/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pixel.resource;

import com.pixel.database.MyDBHandler;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author stephnoutsa
 */

@Path("jobs")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class JobResource {
    
    MyDBHandler dbHandler = new MyDBHandler();
    
    @GET
    public String getJobs(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        
        String search = queryParams.getFirst("search");
        String category = queryParams.getFirst("category");
        String location = queryParams.getFirst("location");
        
        List<String> urlList = new ArrayList<>();
        String akwajobs = "http://www.akwajobs.com/search?search=" + search + "&category=" +
                    category + "&location=" + location;
        String wikajobs = "http://www.wikajobs.com/search?search=" + search + "&category=" +
                    category + "&location=" + location;
        String bueajobs = "http://www.bueajobs.com/search?search=" + search + "&category=" +
                    category + "&location=" + location;
        String jumiajobs = "https://jobs.jumia.cm/en/jobs/?by=" + category + "&in=";
        urlList.add(akwajobs); // akwajobs at position 0
        urlList.add(wikajobs); // wikajobs at position 1
        urlList.add(bueajobs); // bueajobs at position 2
        urlList.add(jumiajobs); // jumiajobs at position 3
        
        String response = "";
        
        try {
            for (int i = 0; i < urlList.size(); i++) {
                String url = urlList.get(i);
                Document doc = Jsoup.connect(url).timeout(0).get();

                switch (i) {
                    case 0:
                        response += scrapeAkwaJobs(doc);
                        
                        // Move to next pages            
                        Elements akwaPages = doc.select("ul.pagination > li > a[href]:not(a[rel])");
                        System.out.println(akwaPages.size());

                        for (Element page : akwaPages) {
                            // Load the next page
                            Document next = Jsoup.connect(page.attr("abs:href")).get();
                            System.out.println(page.attr("abs:href"));
                            response += scrapeAkwaJobs(next);
                        }
                        
                        break;
                        
                    case 1:
                        response += scrapeWikaJobs(doc);
                        
                        // Move to next pages            
                        Elements wikaPages = doc.select("ul.pagination > li > a[href]:not(a[rel])");
                        System.out.println(wikaPages.size());

                        for (Element page : wikaPages) {
                            // Load the next page
                            Document next = Jsoup.connect(page.attr("abs:href")).get();
                            System.out.println(page.attr("abs:href"));
                            response += scrapeWikaJobs(next);
                        }
                        
                        break;
                        
                    case 2:
                        response += scrapeWikaJobs(doc);
                        
                        // Move to next pages            
                        Elements bueaPages = doc.select("ul.pagination > li > a[href]:not(a[rel])");
                        System.out.println(bueaPages.size());

                        for (Element page : bueaPages) {
                            // Load the next page
                            Document next = Jsoup.connect(page.attr("abs:href")).get();
                            System.out.println(page.attr("abs:href"));
                            response += scrapeWikaJobs(next);
                        }
                        
                        break;
                        
                    case 3:
                        response += scrapeJumiaJobs(doc);
                        
                        // Move to next pages
                        String numString = doc.select("div.search-nav-wrapper ul.pagination form.pagination-selector "
                                + "span.pagination-page-number strong").text();
                        
                        int number = Integer.valueOf(numString);
                        System.out.println(number);
                        
                        for (int x = 2; x <= number; x++) {
                            // Load the next page
                            String pageLink = url + "&page=" + x;
                            Document next = Jsoup.connect(pageLink).get();
                            System.out.println(pageLink);
                            response += scrapeJumiaJobs(next);
                        }
                    
                        break;
                }
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return response;
    }
    
    @POST
    public String addJob(String job) {
        return dbHandler.addJob(job);
    }
    
    // Method to scrape web pages from Akwa Jobs website
    public String scrapeAkwaJobs(Document doc) {
        String response = "";
        
        Elements container = doc.select("div.col-md-12:gt(2)");
        
        for (Element e : container.select("div.content-left > div.media > div.media-body")) {
            String posted = e.select("p").first().text();
            String title = e.select("h3.media-heading > a[href]").text();
            String link = e.select("h3.media-heading > a[href]").attr("href");
            String town = e.select("h5 > a[href]").first().nextElementSibling().text();
            String description = e.select("p").text();
            description = description.substring(0, description.length() - 10) + "...";

            response += "Date posted: " + posted + "\n"
                    + "Title: " + title + "\n"
                    + "URL: " + link + "\n"
                    + "Location: " + town + "\n"
                    + "Description: " + description + "\n\n";
        }
        
        return response;
    }
    
    // Method to scrape web pages from wikajobs and bueajobs websites
    public String scrapeWikaJobs(Document doc) {
        String response = "";
        
        Elements container = doc.select("div.col-md-12:gt(0)");
        
        for (Element e : container.select("div.content-left > div.media > div.media-body")) {
            String posted = e.select("p").first().text();
            String title = e.select("h3.media-heading > a[href]").text();
            String link = e.select("h3.media-heading > a[href]").attr("href");
            String town = e.select("h5 > a[href]").first().nextElementSibling().text();
            String description = e.select("p").text();
            description = description.substring(0, description.length() - 10) + "...";

            response += "Date posted: " + posted + "\n"
                    + "Title: " + title + "\n"
                    + "URL: " + link + "\n"
                    + "Location: " + town + "\n"
                    + "Description: " + description + "\n\n";
        }
        
        return response;
    }
    
    // Method to scrape from jumia jobs website
    public String scrapeJumiaJobs(Document doc) {
        String response = "";
        
        Elements container = doc.select("div.col-xs-12:eq(0)");
        
        for (Element e : container.select("div.hidden-sm[data-automation]")) {
            String posted = e.select("div.panel-heading small.text-muted").text();
            String title = e.select("div.panel-heading p.headline3 strong a").text();
            String link = e.select("div.panel-heading p.headline3 strong a").attr("abs:href");
            String town = e.select("div.panel-footer ul.list-inline li.panel-footer-icon-wrapper:gt(0)").first().text();
            String description = e.select("div.panel-footer ul.list-inline li.panel-footer-icon-wrapper "
                    + "a.js-company-name").text();

            response += "Date posted: " + posted + "\n"
                    + "Title: " + title + "\n"
                    + "URL: " + link + "\n"
                    + "Location: " + town + "\n"
                    + "Description: " + description + "\n\n";
        }
        
        return response;
    }
    
}
