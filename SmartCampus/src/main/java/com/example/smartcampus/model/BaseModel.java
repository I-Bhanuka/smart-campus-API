/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.smartcampus.model;

/**
 *
 * @author Isith Bhanuka
 */

// The base model will have the structure only for ID
// This is because DAO is only responsible Object related CRUD. 

public interface BaseModel {
    
    // Abstract methods
    String getId();
    void setId(String id);
    
    
}
