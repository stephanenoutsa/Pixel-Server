/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pixel.resource;

import com.pixel.database.MyDBHandler;
import java.io.IOException;
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

/**
 *
 * @author stephnoutsa
 */

@Path("scholarships")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class ScholarshipResource {
    
    MyDBHandler dbHandler = new MyDBHandler();
    
    @GET
    public String getScholarships(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        
        String query = queryParams.getFirst("query");
        
        String response = "";
        
        try {
            String url = "http://www.njorku.com/search/jobsearch?query=" + query + "&country=";
            Document doc = Jsoup.connect(url).get();
            //Elements newsHeadlines = doc.select("#mp-itn b a");
            response = doc.text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return response;
    }
    
    @POST
    public String addScholarship(String scholarship) {
        return dbHandler.addScholarship(scholarship);
    }
    
}
