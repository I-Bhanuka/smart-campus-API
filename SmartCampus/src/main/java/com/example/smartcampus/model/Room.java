/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampus.model;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Isith Bhanuka
 */
public class Room {
    
    // Priavte attributes
    private String id;
    private String name;
    private int capacity;
    private List<String> sensorIds = new ArrayList<>();
    
    // No args constuctor
    public Room() {}
    
    // All args constructor without the List
    public Room(String id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        // We don't put sensorIds in the constructor because it starts empty!
    }
    
    
    // Getter and Setter for Id
    public String getId() {
        return id; 
    }
    
    public void setId(String id) { 
        this.id = id; 
    }
    
    
    // Getter and Setter for the Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    // Getter and Setter for capacity
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    
    // Getter and Setter for Sensor ID list
    public List<String> getSensorIds() {
        return sensorIds;
    }

    public void setSensorIds(List<String> sensorIds) {
        this.sensorIds = sensorIds;
    }
    
    
    
    
}
