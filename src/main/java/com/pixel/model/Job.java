/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pixel.model;

/**
 *
 * @author stephnoutsa
 */
public class Job {
    
    // Private variables
    int id;
    String title, url, category, location, description, datePosted;
    
    // Empty constructor
    public Job() {
        
    }
    
    // Constructor
    public Job(int id, String title, String url, String category, String location, String description, String datePosted) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.category = category;
        this.location = location;
        this.description = description;
        this.datePosted = datePosted;
    }
    
    // Constructor
    public Job(String title, String url, String category, String location, String description, String datePosted) {
        this.title = title;
        this.url = url;
        this.category = category;
        this.location = location;
        this.description = description;
        this.datePosted = datePosted;
    }
    
    // Getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }
        
}
