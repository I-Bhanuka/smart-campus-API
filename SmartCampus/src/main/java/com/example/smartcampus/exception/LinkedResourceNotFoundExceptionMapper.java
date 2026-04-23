/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampus.exception;

import com.example.smartcampus.model.ErrorMessage;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;


/**
 *
 * @author Isith Bhanuka
 */

public class LinkedResourceNotFoundExceptionMapper implements
        ExceptionMapper<LinkedResourceNotFoundException>{
    
    @Override
    public Response toResponse(LinkedResourceNotFoundException exception) {
        
        // Create an instance of ErrorMessage class
        ErrorMessage errorMessage = new ErrorMessage(
            exception.getMessage(),
            400,
            "The resource passed does not exist!");
        
        // Build the response entity
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
        
    }
    
}
