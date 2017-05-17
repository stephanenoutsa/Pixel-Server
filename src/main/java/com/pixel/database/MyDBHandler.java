/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pixel.database;

import com.pixel.model.Subscription;
import com.pixel.model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author stephnoutsa
 */
public class MyDBHandler {
    
    public static final String DATABASE_NAME = "pixel";
    
    public static final String TABLE_USERS = "USERS";
    public static final String USER_COLUMN_ID = "ID";
    public static final String USER_COLUMN_NUMBER = "NUMBER";
    public static final String USER_COLUMN_NETWORK = "NETWORK";
    public static final String USER_COLUMN_LANGUAGE = "LANGUAGE";
    public static final String USER_COLUMN_AGEGROUP = "AGEGROUP";
    public static final String USER_COLUMN_EXPERIENCE = "EXPERIENCE";
    public static final String USER_COLUMN_GENDER = "GENDER";
    public static final String USER_COLUMN_LOCATION = "LOCATION";
    public static final String USER_COLUMN_CATEGORY = "CATEGORY";
    
    public static final String TABLE_SUBSCRIPTIONS = "SUBSCRIPTION";
    public static final String SUBS_COLUMN_ID = "ID";
    public static final String SUBS_COLUMN_NUMBER = "NUMBER";
    public static final String SUBS_COLUMN_FIRST = "FIRSTSTATUS";
    public static final String SUBS_COLUMN_SUBSCRIBED = "SUBSCRIBESTATUS";
    public static final String SUBS_COLUMN_SERVICE = "SERVICE";
    public static final String SUBS_COLUMN_DATE = "SUBSCRIBEDATE";
    
    public static final String TABLE_JOBS = "JOBS";
    public static final String JOBS_COLUMN_ID = "ID";
    public static final String JOBS_COLUMN_TITLE = "TITLE";
    public static final String JOBS_COLUMN_URL = "URL";
    public static final String JOBS_COLUMN_CATEGORY = "CATEGORY";
    public static final String JOBS_COLUMN_LOCATION = "LOCATION";
    public static final String JOBS_COLUMN_DESC = "DESCRIPTION";
    public static final String JOBS_COLUMN_DATE = "DATEPOSTED";
    
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
     * @param number
     * @param service
     * @param network
     * @return 
     */    
    // Method to add/update subscriber
    public User addSubscriber(String number, String service, String network) {
        User user = new User();
        
        Connection conn = null;
        Statement db = null;
        
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            db = conn.createStatement();
            
            // Get the date when the user subscribes
            SimpleDateFormat sdf = new SimpleDateFormat("dd MM, yyyy");
            Date today = Calendar.getInstance().getTime();
            String date = sdf.format(today);

            if (userExists(number)) {
                // Update the subscriber's service and subscribeStatus
                updateSubscriberService(number, service);
                updateSubscribeStatus(number, "SUBSCRIBED");
            } else {
                // Add user to Users table
                String addUser = "INSERT INTO " + TABLE_USERS + "(" + USER_COLUMN_NUMBER + ", "
                        + USER_COLUMN_NETWORK + ", " + USER_COLUMN_LANGUAGE + ", "
                        +USER_COLUMN_AGEGROUP + ", " + USER_COLUMN_EXPERIENCE + ", "
                        + USER_COLUMN_GENDER + ", " + USER_COLUMN_LOCATION + ", "
                        + USER_COLUMN_CATEGORY + ") VALUES(\'" + number + "\', \'"
                        + network + "\', \'null\', \'null\', \'null\', \'null\', \'null\', \'null\'";
                System.out.println("User ID: " + user.getId());
                db.executeUpdate(addUser);
                
                user.setNumber(number);
                user.setNetwork(network);
                
                // Add subscription to Subscriptions table
                String addSubscription = "INSERT INTO " + TABLE_SUBSCRIPTIONS + "("
                        + SUBS_COLUMN_NUMBER + ", " + SUBS_COLUMN_FIRST + ", "
                        + SUBS_COLUMN_SUBSCRIBED + ", " + SUBS_COLUMN_SERVICE + ", "
                        + SUBS_COLUMN_DATE + ") VALUES(\'" + number + "\', \'null\', \'"
                        + "SUBSCRIBED\', \'" + service + "\', \'" + date + "\'";
                db.executeUpdate(addSubscription);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (db != null)
                    db.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
               if (conn != null)
                  conn.close();
            } catch (SQLException se) {
               se.printStackTrace();
            }
        }
        
        return user;
    }
    
    // Method to check if subscriber exists
    public boolean userExists(String number) {
        boolean exists = true;
        
        Connection conn = null;
        Statement db = null;
             
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            db = conn.createStatement();
            
            String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + USER_COLUMN_NUMBER
                    + " = \'" + number + "\'";
            
            ResultSet result = db.executeQuery(query);
            
            if (result.next()) {
                System.out.println("User exists");
            } else {
                System.out.println("User does not exist");
                
                exists = false;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (db != null)
                    db.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
               if (conn != null)
                  conn.close();
            } catch (SQLException se) {
               se.printStackTrace();
            }
        }
        
        return exists;
    }
    
    // Method to update a subscriber's service
    public Subscription updateSubscriberService(String number, String service) {
        Subscription s = new Subscription();
        
        Connection conn = null;
        Statement db = null;
        
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            db = conn.createStatement();
            
            String query = "UPDATE " + TABLE_SUBSCRIPTIONS + " SET " +
                    SUBS_COLUMN_SERVICE + "=\'" + service + "\' " +
                    "WHERE " + SUBS_COLUMN_NUMBER + "=" + number;
            db.executeUpdate(query);
            
            s.setService(service);
            s.setNumber(number);
            
            System.out.println("Service updated");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (db != null)
                    db.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
               if (conn != null)
                  conn.close();
            } catch (SQLException se) {
               se.printStackTrace();
            }
        }
        
        return s;
    }
    
    // Method to update a subscriber's status
    public Subscription updateSubscribeStatus(String number, String status) {
        Subscription s = new Subscription();
        
        Connection conn = null;
        Statement db = null;
        
        try {
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            db = conn.createStatement();
            
            String query = "UPDATE " + TABLE_SUBSCRIPTIONS + " SET " +
                    SUBS_COLUMN_SUBSCRIBED + "=\'" + status + "\' " +
                    "WHERE " + SUBS_COLUMN_NUMBER + "=" + number;
            db.executeUpdate(query);
            
            s.setNumber(number);
            s.setSubscribeStatus(status);
            
            System.out.println("Subscribed status updated");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (db != null)
                    db.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
               if (conn != null)
                  conn.close();
            } catch (SQLException se) {
               se.printStackTrace();
            }
        }
        
        return s;
    }
    
    // Method to get a subscriber's preferred language
    public String getPreferredLang(String number) {
        String lang = "";
        
        Connection conn = null;
        Statement db = null;
             
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            db = conn.createStatement();
            
            String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + USER_COLUMN_NUMBER
                    + " = \'" + number + "\'";
            
            ResultSet result = db.executeQuery(query);
            
            if (result.next()) {
                lang = result.getString(USER_COLUMN_LANGUAGE);
            } else {
                System.out.println("User does not exist");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (db != null)
                    db.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
               if (conn != null)
                  conn.close();
            } catch (SQLException se) {
               se.printStackTrace();
            }
        }
        
        return lang;
    }
    
    // Method to update a subscriber's preferred language
    public User updateSubscriberLanguage(String number, String lang) {
        User user = new User();
        
        Connection conn = null;
        Statement db = null;
        
        try {
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            db = conn.createStatement();
            
            String query = "UPDATE " + TABLE_USERS + " SET " +
                    USER_COLUMN_LANGUAGE + "=\'" + lang + "\' " +
                    "WHERE " + USER_COLUMN_NUMBER + "=" + number;
            db.executeUpdate(query);
            
            user.setNumber(number);
            user.setLanguage(lang);
            
            System.out.println("Language updated");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (db != null)
                    db.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
               if (conn != null)
                  conn.close();
            } catch (SQLException se) {
               se.printStackTrace();
            }
        }
        
        return user;
    }
    
    // Method to determine a subscriber's first time status
    public boolean isFirstTime(String number) {
        boolean first = true;
        
        Connection conn = null;
        Statement db = null;
             
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            db = conn.createStatement();
            
            String query = "SELECT * FROM " + TABLE_SUBSCRIPTIONS + " WHERE " + SUBS_COLUMN_NUMBER
                    + " = \'" + number + "\'";
            
            ResultSet result = db.executeQuery(query);
            
            if (result.next()) {
                String status = result.getString(SUBS_COLUMN_FIRST);
                
                if (!status.equals("YES") && !status.equals("null")) {
                    first = false;
                }
            } else {
                System.out.println("User does not exist");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (db != null)
                    db.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
               if (conn != null)
                  conn.close();
            } catch (SQLException se) {
               se.printStackTrace();
            }
        }
        
        return first;
    }
    
    // Method to update a subscriber's first time status
    // Status is either "YES", "NO" or "null"
    public Subscription updateFirstTimeStatus(String number, String status) {
        Subscription s = new Subscription();
        
        Connection conn = null;
        Statement db = null;
        
        try {
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            db = conn.createStatement();
            
            String query = "UPDATE " + TABLE_SUBSCRIPTIONS + " SET " +
                    SUBS_COLUMN_FIRST + "=\'" + status + "\' " +
                    "WHERE " + SUBS_COLUMN_NUMBER + "=" + number;
            db.executeUpdate(query);
            
            s.setNumber(number);
            s.setSubscribeStatus(status);
            
            System.out.println("First-time status updated");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (db != null)
                    db.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
               if (conn != null)
                  conn.close();
            } catch (SQLException se) {
               se.printStackTrace();
            }
        }
        
        return s;
    }
    
    // Method to update a subscriber's category
    public User updateSubscriberCategory(String number, String category) {
        User user = new User();
        
        Connection conn = null;
        Statement db = null;
        
        try {
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            db = conn.createStatement();
            
            String query = "UPDATE " + TABLE_USERS + " SET " +
                    USER_COLUMN_CATEGORY + "=\'" + category + "\' " +
                    "WHERE " + USER_COLUMN_NUMBER + "=" + number;
            db.executeUpdate(query);
            
            user.setNumber(number);
            user.setLanguage(category);
            
            System.out.println("Category updated");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (db != null)
                    db.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
               if (conn != null)
                  conn.close();
            } catch (SQLException se) {
               se.printStackTrace();
            }
        }
        
        return user;
    }
    
    // Method to update a subscriber's experience
    public User updateSubscriberExperience(String number, String exp) {
        User user = new User();
        
        Connection conn = null;
        Statement db = null;
        
        try {
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            db = conn.createStatement();
            
            String query = "UPDATE " + TABLE_USERS + " SET " +
                    USER_COLUMN_EXPERIENCE + "=\'" + exp + "\' " +
                    "WHERE " + USER_COLUMN_NUMBER + "=" + number;
            db.executeUpdate(query);
            
            user.setNumber(number);
            user.setExperience(exp);
            
            System.out.println("Experience updated");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (db != null)
                    db.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
               if (conn != null)
                  conn.close();
            } catch (SQLException se) {
               se.printStackTrace();
            }
        }
        
        return user;
    }
    
    // Method to update a subscriber's age group
    public User updateSubscriberAgeGroup(String number, String ageGroup) {
        User user = new User();
        
        Connection conn = null;
        Statement db = null;
        
        try {
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            db = conn.createStatement();
            
            String query = "UPDATE " + TABLE_USERS + " SET " +
                    USER_COLUMN_AGEGROUP + "=\'" + ageGroup + "\' " +
                    "WHERE " + USER_COLUMN_NUMBER + "=" + number;
            db.executeUpdate(query);
            
            user.setNumber(number);
            user.setAgeGroup(ageGroup);
            
            System.out.println("Age group updated");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (db != null)
                    db.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
               if (conn != null)
                  conn.close();
            } catch (SQLException se) {
               se.printStackTrace();
            }
        }
        
        return user;
    }
    
    // Method to update a subscriber's gender
    public User updateSubscriberGender(String number, String gender) {
        User user = new User();
        
        Connection conn = null;
        Statement db = null;
        
        try {
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            db = conn.createStatement();
            
            String query = "UPDATE " + TABLE_USERS + " SET " +
                    USER_COLUMN_GENDER + "=\'" + gender + "\' " +
                    "WHERE " + USER_COLUMN_NUMBER + "=" + number;
            db.executeUpdate(query);
            
            user.setNumber(number);
            user.setGender(gender);
            
            System.out.println("Gender updated");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (db != null)
                    db.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
               if (conn != null)
                  conn.close();
            } catch (SQLException se) {
               se.printStackTrace();
            }
        }
        
        return user;
    }
    
    // Method to update a subscriber's town of residence
    public User updateSubscriberLocation(String number, String location) {
        User user = new User();
        
        Connection conn = null;
        Statement db = null;
        
        try {
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            db = conn.createStatement();
            
            String query = "UPDATE " + TABLE_USERS + " SET " +
                    USER_COLUMN_LOCATION + "=\'" + location + "\' " +
                    "WHERE " + USER_COLUMN_NUMBER + "=" + number;
            db.executeUpdate(query);
            
            user.setNumber(number);
            user.setLocation(location);
            
            System.out.println("Town of residence updated");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (db != null)
                    db.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
               if (conn != null)
                  conn.close();
            } catch (SQLException se) {
               se.printStackTrace();
            }
        }
        
        return user;
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
