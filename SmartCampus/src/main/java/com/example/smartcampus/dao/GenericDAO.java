/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampus.dao;

import java.util.List;
import com.example.smartcampus.model.BaseModel;

/**
 *
 * @author Isith Bhanuka
 */
public class GenericDAO <T extends BaseModel> {
    
    // List to hold the data
    private final List<T> ITEMS;
    
    // Constructor
    public GenericDAO(List<T> ITEMS) {
        this.ITEMS = ITEMS; // Inject the specifc list from the mock database
    }
    
    // Get all elements 
    public List getAll() {
        
        synchronized(ITEMS) {
            return this.ITEMS;
        }
    }
    
    // Get element by id
    public T getById(String id) {
        
        synchronized(ITEMS) {
            for (T item : ITEMS) {
                if (item.getId().equals(id)) return item;
            }
            return null;
        }
    }
    
    // Add element 
    public void add(T item) {
        
        synchronized(ITEMS) {
//            int maxId = 0;
//
//            // Get the max id
//            for (T i : ITEMS) {
//                if (i.getId() > maxId) {
//                    maxId = i.getId();
//                }
//            }
//
//            // Add the new element 
//            item.setId(maxId + 1); // Auto-increment id
//            ITEMS.add(item);
              ITEMS.add(item);
            
        }
    }
    
    // Update item
    public void updateItem (T updateItem) {
        
        synchronized(ITEMS) {
//            // Start a for loop 
//            for (int i = 0; i < ITEMS.size(); i++) {
//                T item = ITEMS.get(i);
//                if (item.getId() == updateItem.getId()) {
//                    ITEMS.set(i, updateItem);
//                    return;
//                }
//            }
        }
    }
    
    // Delete item 
    public void delete(String id) {
        
        synchronized(ITEMS) {
            ITEMS.removeIf(item -> item.getId().equals(id));
        }
    }
    
}
