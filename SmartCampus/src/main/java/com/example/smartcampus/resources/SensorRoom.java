/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampus.resources;

import javax.ws.rs.*;

import com.example.smartcampus.model.Room;
import com.example.smartcampus.dao.*;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.smartcampus.exception.DataNotFoundException;
import com.example.smartcampus.model.ErrorMessage;

/**
 *
 * @author Isith Bhanuka
 */

@Path("/rooms")
public class SensorRoom {
    
    // Instantiate the generic DTO
    private final GenericDAO<Room> ROOMDAO = new GenericDAO<> (MockDatabase.ROOM);
    
    // Get all rooms
    @GET // Call the room base
    @Produces(MediaType.APPLICATION_JSON) // Will produce a JSON response
    public Response getAllRooms() {
        
        List<Room> rooms =  ROOMDAO.getAll();
        
        return Response.ok(rooms).build();
    }
    
    // Add a room
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRoom(Room room) { 
        
        // Check whether name and id is provided in the request body
        if (room.getId() == null || room.getName() == null) {
            return Response.status(Response.Status.EXPECTATION_FAILED)
                    .entity(new ErrorMessage("Room already exists!", 409, "Room IDs should be unique. Cannot have rooms with the same id!"))
                    .build();
        }
        
        // Checking if there is a room with the same id
        if (ROOMDAO.getById(room.getId()) != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage("Room ID and Name is required!", 400, "Room ID and Name must be provided in the request body to create a room!"))
                    .build();
        }
        
        ROOMDAO.add(room);
        
        // success message
        return Response.status(Response.Status.CREATED).build();
    }
    
    // Get a room by id
    @GET
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room getRoomById(@PathParam("roomId") String id) {
        
        // Create an instance of the room class
        Room room = ROOMDAO.getById(id);
        
        // Checking if the response is null
        if (room == null) {
            throw new DataNotFoundException("Room not found with the id: " + id);
        }
        
        return room;
    }
    
    
}
