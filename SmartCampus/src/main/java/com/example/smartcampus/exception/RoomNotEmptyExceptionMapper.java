/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampus.exception;

import com.example.smartcampus.model.ErrorMessage;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import javax.ws.rs.core.Response;

/**
 *
 * @author Isith Bhanuka
 */

@Provider
public class RoomNotEmptyExceptionMapper implements
        ExceptionMapper<RoomNotEmptyException>{
    
    @Override
    public Response toResponse(RoomNotEmptyException exception) {
        
        // Create an instance of ErrorMessage class
        ErrorMessage errorMessage = new ErrorMessage(
            exception.getMessage(),
            404,
            "Room is not empty. Cannot delete the room!");
        
        // Build the response entity
        return Response.status(Response.Status.CONFLICT)
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
        
    }
    
}
