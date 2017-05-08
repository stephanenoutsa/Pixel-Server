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
    
    // Method to add/update subscriber
    public boolean addSubscriber(String phone, String choice) {
        boolean ok = true;
        
        if (subscriberExists(phone)) {
            /* Code to update subscriber's choice */
        } else {
            /* Code to add subscriber to database */
        }
        
        return ok;
    }
    
    // Method to check if subscriber exists
    public boolean subscriberExists(String phone) {
        boolean exists = false;
        
        /* Code to check if subscriber exists and is currently subscribed */
        
        return exists;
    }
    
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
    
}
