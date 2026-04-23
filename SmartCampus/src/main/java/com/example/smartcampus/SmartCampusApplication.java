/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampus;

import javax.ws.rs.ApplicationPath; // ApplicationPath to define the root URL for everything
import javax.ws.rs.core.Application; // Application 
import java.util.HashSet; // Implementation of Set interface
import java.util.Set; // Used to have unique resource classes

import com.example.smartcampus.resources.DiscoveryResource;
import com.example.smartcampus.resources.SensorResource;
import com.example.smartcampus.resources.SensorRoom;
import com.example.smartcampus.exception.LinkedResourceNotFoundExceptionMapper;
import com.example.smartcampus.exception.RoomNotEmptyExceptionMapper;
import com.example.smartcampus.exception.SensorUnavailableExceptionMapper;
import com.example.smartcampus.exception.GenericExceptionMapper;
import com.example.smartcampus.filter.LoginFilter;

/**
 *
 * @author Isith Bhanuka
 */

// SmartCampusApplication is the core configuration class of the REST application
// The Central point is the SmartCampusApplication class

@ApplicationPath("/api/v1")
public class SmartCampusApplication extends Application {
    
    // Overriding the getClasses method 
    @Override 
    public Set<Class<?>> getClasses() {
        
        // Declare anc initlize the HashSet
        Set<Class<?>> classes = new HashSet<>();
        classes.add(DiscoveryResource.class);
        classes.add(SensorRoom.class);
        classes.add(SensorResource.class);
        classes.add(LinkedResourceNotFoundExceptionMapper.class);
        classes.add(RoomNotEmptyExceptionMapper.class);
        classes.add(SensorUnavailableExceptionMapper.class);
        classes.add(SensorUnavailableExceptionMapper.class);
        classes.add(GenericExceptionMapper.class);
        classes.add(LoginFilter.class);
        return classes;
    }
    
}
