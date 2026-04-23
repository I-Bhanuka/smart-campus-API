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
public class GenericExceptionMapper implements 
        ExceptionMapper<Throwable>{
    
    @Override
    public Response toResponse(Throwable exception) {
        
        ErrorMessage errorMessage = new ErrorMessage(
            "An unexpected error occurred.",
            500,
            exception.getMessage()  // shows the actual error detail
        );
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(errorMessage)
                .build();
    }
    
}
