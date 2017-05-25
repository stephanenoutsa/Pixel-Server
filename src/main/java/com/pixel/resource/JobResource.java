/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pixel.resource;

import com.pixel.database.MyDBHandler;
import com.pixel.model.Job;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JobResource {
    
    MyDBHandler dbHandler = new MyDBHandler();
    List<Job> jobList;
    
    // Method to get jobs from database
    @GET
    public List<Job> getJobs(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        
        String category = queryParams.getFirst("category");
        String offsetString = queryParams.getFirst("offset");
        
        int offset;
        if (offsetString.equals("")) {
            offset = 0;
        } else {
            offset = Integer.parseInt(offsetString);
        }
        System.out.println("Offset: " + offset);
        
        return dbHandler.getJobs(category, offset);
    }
    
    // Method to get a single job from database
    @GET
    @Path("{id}")
    public Job getJob(@PathParam("id") String id) {
        int i = Integer.parseInt(id);
        return dbHandler.getJob(i);
    }
    
    // Method to perform regular web scraping for jobs
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("scrape")
    public String scrapeJobs(@Context UriInfo uriInfo) {
        List<String> catList = new ArrayList<>();
        catList.add("Informatique");
        catList.add("Engineering");
        catList.add("Marketing");
        catList.add("Accounting");
        catList.add("Finance");
        catList.add("Human Resources");
        catList.add("Management");
        catList.add("");
        
        String response = "";
        
        for (String category : catList) {
            System.out.println("Doing category \'" + category + "\'");        
            jobList = new ArrayList<>();
            
            response = "";
            
            List<String> urlList = new ArrayList<>();
            String akwajobs = "http://www.akwajobs.com/search?search=&category=" +
                        category + "&location=";
            String wikajobs = "http://www.wikajobs.com/search?search=&category=" +
                        category + "&location=";
            String bueajobs = "http://www.bueajobs.com/search?search=&category=" +
                        category + "&location=";
            String jumiajobs = "https://jobs.jumia.cm/en/jobs/?by=" + category + "&in=";
            urlList.add(akwajobs); // akwajobs at position 0
            urlList.add(wikajobs); // wikajobs at position 1
            urlList.add(bueajobs); // bueajobs at position 2
            urlList.add(jumiajobs); // jumiajobs at position 3
            
            try {
                for (int i = 0; i < urlList.size(); i++) {
                    String url = urlList.get(i);
                    Document doc = Jsoup.connect(url).timeout(0).get();

                    switch (i) {
                        case 0:
                            response += scrapeAkwaJobs(doc, category);

                            // Move to next pages            
                            Elements akwaPages = doc.select("ul.pagination > li > a[href]:not(a[rel])");
                            System.out.println(akwaPages.size());

                            for (Element page : akwaPages) {
                                // Load the next page
                                Document next = Jsoup.connect(page.attr("abs:href")).get();
                                System.out.println(page.attr("abs:href"));
                                response += scrapeAkwaJobs(next, category);
                            }

                            break;

                        case 1:
                            response += scrapeWikaJobs(doc, category);

                            // Move to next pages            
                            Elements wikaPages = doc.select("ul.pagination > li > a[href]:not(a[rel])");
                            System.out.println(wikaPages.size());

                            for (Element page : wikaPages) {
                                // Load the next page
                                Document next = Jsoup.connect(page.attr("abs:href")).get();
                                System.out.println(page.attr("abs:href"));
                                response += scrapeWikaJobs(next, category);
                            }

                            break;

                        case 2:
                            response += scrapeWikaJobs(doc, category);

                            // Move to next pages            
                            Elements bueaPages = doc.select("ul.pagination > li > a[href]:not(a[rel])");
                            System.out.println(bueaPages.size());

                            for (Element page : bueaPages) {
                                // Load the next page
                                Document next = Jsoup.connect(page.attr("abs:href")).get();
                                System.out.println(page.attr("abs:href"));
                                response += scrapeWikaJobs(next, category);
                            }

                            break;

                        case 3:
                            response += scrapeJumiaJobs(doc, category);

                            // Move to next pages
                            String numString = doc.select("div.text-right ul.pagination form.pagination-selector "
                                    + "span.pagination-page-number strong").first().text();

                            int number = Integer.parseInt(numString);
                            System.out.println(number);

                            for (int x = 2; x <= number; x++) {
                                // Load the next page
                                String pageLink = url + "&page=" + x;
                                Document next = Jsoup.connect(pageLink).get();
                                System.out.println(pageLink);
                                response += scrapeJumiaJobs(next, category);
                            }

                            break;
                    }

                }

                // Add list of jobs to the JOBS table
                dbHandler.addJobs(jobList);

                System.out.println("Size of jobList: " + jobList.size());                

                // Retrieve a single job from this category to add to USSDJOBS table
                boolean ok = false;
                
                for (Job ussdJob : jobList) {
                    System.out.println("In for loop");
                    String cat = category;
                    
                    if (!ok) {
                        System.out.println("In if condition");
                        if (cat.equals("")) {
                            cat = "null";
                        }
                        ok = true;

                        String title = ussdJob.getTitle();
                        String url = ussdJob.getUrl();

                        String content = title + "\n" + url;

                        if (content.length() > 160) {
                            if (category.equals("")) {
                                content = url;
                            } else {
                                content = category + "\n" + url;
                            }

                            if (content.length() > 160) {
                                content = url;

                                if (content.length() > 160) {
                                    ok = false;
                                    System.out.println("Could not add job");
                                } else {
                                    // Update the USSD Job for this category
                                    dbHandler.updateUssdJob(content, cat);

                                    /* Code to send the USSD Job to operator
                                    * parameters are assumed to be phone number and content
                                    */
                                }
                            } else {                                
                                // Update the USSD Job for this category
                                dbHandler.updateUssdJob(content, cat);

                                /* Code to send the USSD Job to operator
                                * parameters are assumed to be phone number and content
                                */
                            }
                        } else {
                            // Update the USSD Job for this category
                            dbHandler.updateUssdJob(content, cat);

                            /* Code to send the USSD Job to operator
                            * parameters are assumed to be phone number and content
                            */
                        }
                    } else {
                        System.out.println("USSD job already added for \'" + category + "\'");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return response;
    }
    
    // Method to scrape web pages from Akwa Jobs website
    public String scrapeAkwaJobs(Document doc, String category) {
        String response = "";
        
        Elements container = doc.select("div.col-md-12:gt(2)");
        
        for (Element e : container.select("div.content-left > div.media > div.media-body")) {
            String posted = e.select("p").first().text();
            String title = e.select("h3.media-heading > a[href]").text();
            String link = e.select("h3.media-heading > a[href]").attr("href");
            String location = e.select("h5 > a[href]").first().nextElementSibling().text();
            String description = e.select("p:gt(0)").text();
            description = description.substring(0, description.length() - 10) + "...";
            
            if (category.equals("")) {
                String c = e.select("h5 > a[href]").first().text();
                System.out.println("This category is: " + c);
                
                if (c.toLowerCase().contains("informatique") || c.toLowerCase().contains("engineering")
                        || c.toLowerCase().contains("marketing") || c.toLowerCase().contains("accounting")
                        || c.toLowerCase().contains("finance") || c.toLowerCase().contains("human resources")
                        || c.toLowerCase().contains("management")) {
                } else {
                    title = title.replace("'", "\"\"").trim();
                    location = location.replace("'", "\"\"").trim();
                    description = description.replace("'", "\"\"").trim();
                    
                    response += "Date posted: " + posted + "\n"
                            + "Title: " + title + "\n"
                            + "URL: " + link + "\n"
                            + "Category: " + category + "\n"
                            + "Location: " + location + "\n"
                            + "Description: " + description + "\n\n";

                    Job job = new Job(title, link, category, location, description, posted);
                    jobList.add(job);
                }
            } else {
                title = title.replace("'", "\"\"").trim();
                location = location.replace("'", "\"\"").trim();
                description = description.replace("'", "\"\"").trim();

                response += "Date posted: " + posted + "\n"
                        + "Title: " + title + "\n"
                        + "URL: " + link + "\n"
                        + "Category: " + category + "\n"
                        + "Location: " + location + "\n"
                        + "Description: " + description + "\n\n";

                Job job = new Job(title, link, category, location, description, posted);
                jobList.add(job);
            }
        }
        
        return response;
    }
    
    // Method to scrape web pages from wikajobs website
    public String scrapeWikaJobs(Document doc, String category) {
        String response = "";
        
        Elements container = doc.select("div.box-list-area");
        
        for (Element e : container.select("div.box-list > div.item div.col-md-11")) {
            String posted = e.select("div span.color-white-mute:eq(2)").text();
            posted = posted.replace("Posted on: ", "");
            String title = e.select("h3.no-margin-top > a[href]").text();
            String link = e.select("h3.no-margin-top > a[href]").attr("href");
            String location = e.select("div span.color-white-mute:eq(1) a[href]").first().text();
            String description = e.select("p").text();
            description = description.substring(0, description.length() - 10) + "...";
            
            if (category.equals("")) {
                String c = e.select("div span.color-white-mute:eq(0) a[href]").first().text();
                System.out.println("This category is: " + c);
                
                if (c.toLowerCase().contains("informatique") || c.toLowerCase().contains("engineering")
                        || c.toLowerCase().contains("marketing") || c.toLowerCase().contains("accounting")
                        || c.toLowerCase().contains("finance") || c.toLowerCase().contains("human resources")
                        || c.toLowerCase().contains("management")) {
                } else {
                    title = title.replace("'", "\"\"").trim();
                    location = location.replace("'", "\"\"").trim();
                    description = description.replace("'", "\"\"").trim();
                    
                    response += "Date posted: " + posted + "\n"
                            + "Title: " + title + "\n"
                            + "URL: " + link + "\n"
                            + "Category: " + category + "\n"
                            + "Location: " + location + "\n"
                            + "Description: " + description + "\n\n";

                    Job job = new Job(title, link, category, location, description, posted);
                    jobList.add(job);
                }
            } else {
                title = title.replace("'", "\"\"").trim();
                location = location.replace("'", "\"\"").trim();
                description = description.replace("'", "\"\"").trim();

                response += "Date posted: " + posted + "\n"
                        + "Title: " + title + "\n"
                        + "URL: " + link + "\n"
                        + "Category: " + category + "\n"
                        + "Location: " + location + "\n"
                        + "Description: " + description + "\n\n";

                Job job = new Job(title, link, category, location, description, posted);
                jobList.add(job);
            }     
            
        }
        
        return response;
    }
    
    // Method to scrape web pages from bueajobs website
    public String scrapeBueaJobs(Document doc, String category) {
        String response = "";
        
        Elements container = doc.select("div.col-md-12:gt(0)");
        
        for (Element e : container.select("div.content-left > div.media > div.media-body")) {
            String posted = e.select("p").first().text();
            String title = e.select("h3.media-heading > a[href]").text();
            String link = e.select("h3.media-heading > a[href]").attr("href");
            String location = e.select("h5 > a[href]").first().nextElementSibling().text();
            String description = e.select("p:gt(0)").text();
            description = description.substring(0, description.length() - 10) + "...";
            
            if (category.equals("")) {
                String c = e.select("h5 > a[href]").first().text();
                System.out.println("This category is: " + c);
                
                if (c.toLowerCase().contains("informatique") || c.toLowerCase().contains("engineering")
                        || c.toLowerCase().contains("marketing") || c.toLowerCase().contains("accounting")
                        || c.toLowerCase().contains("finance") || c.toLowerCase().contains("human resources")
                        || c.toLowerCase().contains("management")) {
                    System.out.println("Category already handled");
                } else {
                    title = title.replace("'", "\"\"").trim();
                    location = location.replace("'", "\"\"").trim();
                    description = description.replace("'", "\"\"").trim();
                    
                    response += "Date posted: " + posted + "\n"
                            + "Title: " + title + "\n"
                            + "URL: " + link + "\n"
                            + "Category: " + category + "\n"
                            + "Location: " + location + "\n"
                            + "Description: " + description + "\n\n";

                    Job job = new Job(title, link, category, location, description, posted);
                    jobList.add(job);
                }
            } else {
                title = title.replace("'", "\"\"").trim();
                location = location.replace("'", "\"\"").trim();
                description = description.replace("'", "\"\"").trim();

                response += "Date posted: " + posted + "\n"
                        + "Title: " + title + "\n"
                        + "URL: " + link + "\n"
                        + "Category: " + category + "\n"
                        + "Location: " + location + "\n"
                        + "Description: " + description + "\n\n";

                Job job = new Job(title, link, category, location, description, posted);
                jobList.add(job);
            }            
        }
        
        return response;
    }
    
    // Method to scrape from jumia jobs website
    public String scrapeJumiaJobs(Document doc, String category) {
        String response = "";
        
        Elements container = doc.select("div.col-xs-12:eq(0)");
        
        for (Element e : container.select("div.hidden-sm[data-automation]")) {
            String posted = e.select("div.panel-heading small.text-muted").text();
            String title = e.select("div.panel-heading p.headline3 strong a").text();
            String link = e.select("div.panel-heading p.headline3 strong a").attr("abs:href");
            String location = e.select("div.panel-footer ul.list-inline li.panel-footer-icon-wrapper:gt(0)").first().text();
            String description = e.select("div.panel-footer ul.list-inline li.panel-footer-icon-wrapper "
                    + "a.js-company-name").text();
            
            if (category.equals("")) {
                String c = e.select("div.panel-footer ul.list-inline li.panel-footer-icon-wrapper:eq(2)").text();
                System.out.println("This category is: " + c);
                
                if (c.toLowerCase().contains("informatique") || c.toLowerCase().contains("engineering")
                        || c.toLowerCase().contains("marketing") || c.toLowerCase().contains("accounting")
                        || c.toLowerCase().contains("finance") || c.toLowerCase().contains("human resources")
                        || c.toLowerCase().contains("management")) {
                    System.out.println("Category already handled");
                } else {
                    title = title.replace("'", "\"\"").trim();
                    location = location.replace("'", "\"\"").trim();
                    description = description.replace("'", "\"\"").trim();
                    
                    response += "Date posted: " + posted + "\n"
                            + "Title: " + title + "\n"
                            + "URL: " + link + "\n"
                            + "Category: " + category + "\n"
                            + "Location: " + location + "\n"
                            + "Description: " + description + "\n\n";

                    Job job = new Job(title, link, category, location, description, posted);
                    jobList.add(job);
                }
            } else {
                title = title.replace("'", "\"\"").trim();
                location = location.replace("'", "\"\"").trim();
                description = description.replace("'", "\"\"").trim();

                response += "Date posted: " + posted + "\n"
                        + "Title: " + title + "\n"
                        + "URL: " + link + "\n"
                        + "Category: " + category + "\n"
                        + "Location: " + location + "\n"
                        + "Description: " + description + "\n\n";

                Job job = new Job(title, link, category, location, description, posted);
                jobList.add(job);
            }                        
        }
        
        return response;
    }
    
}
