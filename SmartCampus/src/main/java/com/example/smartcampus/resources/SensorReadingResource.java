/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampus.resources;

import com.example.smartcampus.dao.GenericDAO;
import com.example.smartcampus.dao.MockDatabase;
import com.example.smartcampus.model.Sensor;
import com.example.smartcampus.model.SensorReading;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.smartcampus.exception.LinkedResourceNotFoundException;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Isith Bhanuka
 */

public class SensorReadingResource {
    
    private final String sensorId;
    private final GenericDAO<SensorReading> READINGSDAO = new GenericDAO<> (MockDatabase.READINGS);
    private final GenericDAO<Sensor> SENSORDAO = new GenericDAO<> (MockDatabase.SENSOR);

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId; // knows WHICH sensor it belongs to
    }
    
    // Handles: GET /sensors/S1/readings
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadings() {
        List<SensorReading> readings = READINGSDAO.getAll();
        List<SensorReading> results = new ArrayList<>();
        
        for (SensorReading reading : readings) {
            if (reading.getSensorId().equals(sensorId)) results.add(reading);
        }
        
        return Response.ok(results).build();
    }
    
    // Handles: POST /sensors/S1/readings
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading) {
        
        // See if the sensor exits
        Sensor sensor = SENSORDAO.getById(sensorId);
        
        if (sensor == null) {
            throw new LinkedResourceNotFoundException("Sensor not found with id: " + sensorId);
    }
        
        // Link the reading to THIS sensor
        reading.setSensorId(sensorId);
        
        // Save the reading
        READINGSDAO.add(reading);
        
        // Side effect: update parent sensor's currentValue
        sensor.setCurrentValue(reading.getValue());
        
        return Response.status(Response.Status.CREATED).build();
        
        
    }
    
}
