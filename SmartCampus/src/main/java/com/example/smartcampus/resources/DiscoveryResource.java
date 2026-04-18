/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampus.resources;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Isith Bhanuka
 */

@Path("/") // This maps to the root: /api/v1
public class DiscoveryResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApiInformation() {
        Map<String, Object> apiInfo = new HashMap<>();
        
        // 1. Versioning Info
        apiInfo.put("api_name", "Smart Campus API");
        apiInfo.put("version", "v1.0");
        
        // 2. Administrative Contact Details (Required by spec!)
        apiInfo.put("admin_contact", "admin@smartcampus.westminster.ac.uk");
        
        // 3. Map of primary resource collections
        Map<String, String> links = new HashMap<>();
        links.put("rooms", "/api/v1/rooms");
        links.put("sensors", "/api/v1/sensors");
        
        apiInfo.put("endpoints", links);

        return Response.ok(apiInfo).build();
    }
    
}
