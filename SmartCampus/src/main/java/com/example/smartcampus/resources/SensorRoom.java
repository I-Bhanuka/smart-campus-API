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

import com.example.smartcampus.exception.LinkedResourceNotFoundException;
import com.example.smartcampus.model.ErrorMessage;
import com.example.smartcampus.exception.RoomNotEmptyException;

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
        
        return findRoomById(id);
    }
    
    // Delete a room 
    @DELETE
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRoom(@PathParam("roomId") String id) {
        
        //Retrieve the room
        Room room = findRoomById(id);
        
        // Check whether the room is empty
        if (room.getSensorIds() != null) {
            throw new RoomNotEmptyException("Room cannot be deleted there are active sensors assigned!");
        }
         
        ROOMDAO.delete(id);
        
        return Response.status(Response.Status.OK).build();
        
    }
    
    
    // Helper method to search room by id
    private Room findRoomById(String id) {
        // Create an instance of the room class
        Room room = ROOMDAO.getById(id);
        
        // Checking if the response is null
        if (room == null) {
            throw new LinkedResourceNotFoundException("Room not found with the id: " + id);
        }
        
        return room;
    }
    
    
}
