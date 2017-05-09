/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pixel.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author stephnoutsa
 */
public class MyDBHandler {
    
    public static final String DATABASE_NAME = "pixel";
    
    /** Declare tables here **/
    
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";  
    static final String DB_URL = "jdbc:derby://localhost:1527/" + DATABASE_NAME;
    
    // Database credentials
    static final String USER = "root";
    static final String PASS = "b2kline";
    
    // Main method
    public static void main(String[] args) {
               
    }
    
    
    
    /**
     * Start of methods for Jobs and Scholarship service
     * 
     * @param phone
     * @param choice
     * @param network
     * @return 
     */    
    // Method to add/update subscriber
    public boolean addSubscriber(String phone, String choice, String network) {
        boolean ok = true;
        // Add the date the user subscribes
        
        if (subscriberExists(phone)) {
            /* Code to update subscriber's choice */
        } else {
            /* Code to add subscriber to database */
        }
        
        return ok;
    }
    
    // Method to check if subscriber exists
    public boolean subscriberExists(String phone) {
        boolean exists = true;
        
        /* Code to check if subscriber exists and is currently subscribed */
        
        return exists;
    }
    
    // Method to update a subscriber's preferred language
    public boolean updateSubscriberLanguage(String phone, String lang) {
        boolean ok = true;
        
        /* Code to update the subscriber's preferred language */
        
        return ok;
    }
    
    // Method to update a subscriber's first time status
    // Status is either "YES", "NO" or "null"
    public boolean updateFirstTimeStatus(String phone, String status) {
        boolean ok = true;
        
        /* Code to update the subscriber's first time status */
        
        return ok;
    }
    
    // Method to update a subscriber's category
    public boolean updateSubscriberCategory(String phone, String category) {
        boolean ok = true;
        
        /* Code to update the subscriber's category */
        
        return ok;
    }
    
    // Method to update a subscriber's career level
    public boolean updateSubscriberLevel(String phone, String level) {
        boolean ok = true;
        
        /* Code to update the subscriber's career level */
        
        return ok;
    }
    
    // Method to update a subscriber's age
    public boolean updateSubscriberAge(String phone, String age) {
        boolean ok = true;
        
        /* Code to update the subscriber's age */
        
        return ok;
    }
    
    // Method to update a subscriber's gender
    public boolean updateSubscriberGender(String phone, String gender) {
        boolean ok = true;
        
        /* Code to update the subscriber's gender */
        
        return ok;
    }
    
    // Method to update a subscriber's town of residence
    public boolean updateSubscriberTown(String phone, String town) {
        boolean ok = true;
        
        /* Code to update the subscriber's town of residence */
        
        return ok;
    }
    
    // Method to add a job
    public String addJob(String job) {
        /* Code to add a job to database */
        return job;
    }
    
    // Method to add a scholarship
    public String addScholarship(String scholarship) {
        /* Code to add a scholarship to database */
        return scholarship;
    }
    /** End of methods for Jobs & Scholarships service */
    
    
    
    /**
     * Start of methods for Parent Eye Service
     * 
     * @param phone
     * @return 
     */    
    // Method to verify if a phone number belongs to a parent or guardian
    public boolean isParent(String phone) {
        boolean ok = true;
        
        
        
        return ok;
    }
    
    // Method to retrieve the ids of students under a guardian's care
    public String[] getStudentIds(String phone) {
        String[] studentIds = new String[0];
        
        
        
        return studentIds;
    }
    
    // Method to retrieve the name of a student with a particular ID
    public String getStudentName(String id) {
        String name = "";
        
        
        
        return name;
    }
    
    // Method to check if parent/guardian is subscribed
    public boolean isSubscribed(String phone) {
        boolean ok = true;
        
        /**
         * Get guardian info
         * Check guardian status
         * If status is subscribed, ok remains true
         * Else ok = false;
         */
        
        return ok;
    }    
    /** End of methods for Parent Eye Service */
    
}
