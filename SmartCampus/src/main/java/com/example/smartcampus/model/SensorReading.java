/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampus.model;

/**
 *
 * @author Isith Bhanuka
 */
public class SensorReading {
    
    // Private attributes 
    private String id;
    private long timestamp;
    private double value;
    
    // No args constructor
    public SensorReading() {}

    // All args constructor
    public SensorReading(String id, long timestamp, double value) {
        this.id = id;
        this.timestamp = timestamp;
        this.value = value;
    }

    
    // Getter and Setter for id 
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    // Getter and Setter timestamp
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    
    // Getter and Setter for value
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    
    
    
}
