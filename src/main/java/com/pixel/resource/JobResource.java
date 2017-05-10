/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pixel.resource;

import com.pixel.database.MyDBHandler;
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
        
        String response = "";
        
        try {
            String url = "http://www.akwajobs.com/search?search=" + search + "&category=" +
                    category + "&location=" + location;
            Document doc = Jsoup.connect(url).timeout(0).get();
            
             response += scrapeAkwaJobs(doc);    
             
             // Move to next pages            
            Elements pages = doc.select("ul.pagination > li > a[href]:not(a[rel])");
            System.out.println(pages.size());
            
            for (Element page : pages) {
                // Load the next page
                Document next = Jsoup.connect(page.attr("abs:href")).get();
                System.out.println(page.attr("abs:href"));
                response += scrapeAkwaJobs(next);
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
    
}
