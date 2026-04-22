/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampus.exception;

import com.example.smartcampus.model.ErrorMessage;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Isith Bhanuka
 */

@Provider // This annotation registers this mapper with JAX-RS
public class DataNotFoundExceptionMapper implements
        ExceptionMapper<DataNotFoundException>{
    
    
    // Override the toResponse abstract method in the ExceptionMapper
    @Override
    public Response toResponse(DataNotFoundException exception) {
        
        // Create an instance of ErrorMessage class
        ErrorMessage errorMessage = new ErrorMessage(
            exception.getMessage(),
            404,
            "Data has not being found for this");
        
        // Build the response entity
        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorMessage)
                .build();
        
    }
    
}
