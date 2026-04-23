/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampus.resources;

import com.example.smartcampus.dao.GenericDAO;
import com.example.smartcampus.dao.MockDatabase;
import com.example.smartcampus.model.Room;
import com.example.smartcampus.model.Sensor;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.smartcampus.exception.DataNotFoundException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Isith Bhanuka
 */

@Path("/sensors")
public class SensorResource {
    
    // Instantiate the generic DTO
    private final GenericDAO<Sensor> SENSORDAO = new GenericDAO<> (MockDatabase.SENSOR);
    private final GenericDAO<Room> ROOMDAO = new GenericDAO<> (MockDatabase.ROOM);
    
    // Create a sensor
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor) {
        
        // Check whether the room exists
        String roomId = sensor.getRoomId();
        if (ROOMDAO.getById(roomId) == null) {
            throw new DataNotFoundException("Room not found with the given ID");
        }
        
        // Save the sensor object
        SENSORDAO.add(sensor);
        
        // Add the sensor object into the room
        Room room = ROOMDAO.getById(roomId);
        
        List<String> sensorIds = room.getSensorIds();
        sensorIds.add(sensor.getId());
        room.setSensorIds(sensorIds);
        
        // success message
        return Response.status(Response.Status.CREATED).build();
        
    }
    
    
    // Get all sensors + extra query 
    @GET 
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSensors(@QueryParam("type") String type) {
        
        List<Sensor> sensors =  SENSORDAO.getAll();
        
        if (type != null) {
            List<Sensor> queryResult = new ArrayList<>();
            
            for (Sensor sensor : sensors) {
                if (sensor.getType().equals(type)) queryResult.add(sensor);
            }
            
            return Response.ok(queryResult).build();
        }
        
        return Response.ok(sensors).build();
    }
    
    
    // Sub-resource LOCATOR 
    // Has @Path but NO @GET/@POST/@DELETE
    // Just returns an instance of the child class
    @Path("{sensorId}/read")
    public SensorReadingResource getSensorReadingResource(@PathParam("sensorId") String sensorId) {
        
        // Jersey takes this object and looks inside it for @GET, @POST
        return new SensorReadingResource(sensorId);
    }
    
    
}
