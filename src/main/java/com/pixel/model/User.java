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
public class User {
    
    // Private variables
    int id;
    String number, network, language, ageGroup, experience, gender, location, category;
    
    // Empty constructor
    public User() {
        
    }
    
    // Constructor
    public User(int id, String number, String network, String language, String ageGroup, String experience, String gender, String location, String category) {
        this.id = id;
        this.number = number;
        this.network = network;
        this.language = language;
        this.ageGroup = ageGroup;
        this.experience = experience;
        this.gender = gender;
        this.location = location;
        this.category = category;
    }
    
    // Constructor
    public User(String number, String network, String language, String ageGroup, String experience, String gender, String location, String category) {
        this.number = number;
        this.network = network;
        this.language = language;
        this.ageGroup = ageGroup;
        this.experience = experience;
        this.gender = gender;
        this.location = location;
        this.category = category;
    }
    
    // Getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }    
        
}
