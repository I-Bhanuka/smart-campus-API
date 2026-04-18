/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampus.model;

/**
 *
 * @author Isith Bhanuka
 */
public class Sensor {
    
    // Private attributes
    private String id;
    private String type;
    private String status;
    private double currentValue;
    private String roomId;
    
    // No args constructor
    public Sensor() {}
    
    // All args constructor
    public Sensor(String id, String type, String status, double currentValue, String roomId) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.currentValue = currentValue;
        this.roomId = roomId;
    }

    
    // Getter and Setter for Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    // Getter and Setter for type
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
    // Getter and Setter for status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    // Getter and Setter for currentValue
    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    
    // Getter and Setter for Room Id
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    
    
    
}
