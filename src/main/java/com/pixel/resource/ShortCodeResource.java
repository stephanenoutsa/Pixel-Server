/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pixel.resource;

import com.pixel.database.MyDBHandler;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author stephnoutsa
 */

@Path("shortcode")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class ShortCodeResource {
    
    MyDBHandler dbHandler = new MyDBHandler();
    
    @GET
    @Path("mtn")
    public String mGetChoice(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        
        String phone = queryParams.getFirst("MSISDN");
        String userChoice = queryParams.getFirst("SC_STRING");
        String network = "MTN";
        
        String response = "";
        
        // Add/update subscriber
        dbHandler.addSubscriber(phone, userChoice, network);
        
        return response;
    }
    
    @GET
    @Path("orange")
    public String oGetChoice(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        
        String phone = queryParams.getFirst("MSISDN");
        String userChoice = queryParams.getFirst("SC_STRING");
        String network = "Orange";
        
        String response = "";
        
        // Add/update subscriber
        dbHandler.addSubscriber(phone, userChoice, network);
        
        return response;
    }
    
    @GET
    @Path("nexttel")
    public String nGetChoice(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        
        String phone = queryParams.getFirst("MSISDN");
        String userChoice = queryParams.getFirst("SC_STRING");
        String network = "Nexttel";
        
        String response = "";
        
        // Add/update subscriber
        dbHandler.addSubscriber(phone, userChoice, network);
        
        return response;
    }
    
}
