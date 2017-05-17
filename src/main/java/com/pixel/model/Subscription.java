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
public class Subscription {
    
    // Private variables
    int id;
    String number, firstStatus, subscribeStatus, service, subscribeDate;
    
    // Empty constructor
    public Subscription() {
        
    }
    
    // Constructor
    public Subscription(int id, String number, String firstStatus, String subscribeStatus, String service, String subscribeDate) {
        this.id = id;
        this.number = number;
        this.firstStatus = firstStatus;
        this.subscribeStatus = subscribeStatus;
        this.service = service;
        this.subscribeDate = subscribeDate;
    }
    
    // Constructor
    public Subscription(String number, String firstStatus, String subscribeStatus, String service, String subscribeDate) {
        this.number = number;
        this.firstStatus = firstStatus;
        this.subscribeStatus = subscribeStatus;
        this.service = service;
        this.subscribeDate = subscribeDate;
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

    public String getFirstStatus() {
        return firstStatus;
    }

    public void setFirstStatus(String firstStatus) {
        this.firstStatus = firstStatus;
    }

    public String getSubscribeStatus() {
        return subscribeStatus;
    }

    public void setSubscribeStatus(String subscribeStatus) {
        this.subscribeStatus = subscribeStatus;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSubscribeDate() {
        return subscribeDate;
    }

    public void setSubscribeDate(String subscribeDate) {
        this.subscribeDate = subscribeDate;
    }    
    
}
