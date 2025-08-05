/**
 * CONTROLLER LAYER: GroceryController
 *
 * PURPOSE:
 * - This class handles HTTP requests for Grocery operations.
 * - Connects frontend/clients (like Postman or React) to the business logic in Service layer.
 *
 * SECURITY:
 * - Add method-level or class-level role-based access if needed later.
 *
 * INTERVIEW INSIGHT:
 * - Controller = entry point of REST API
 * - Should not contain business logic directly
 * - Returns ResponseEntity (standard API practice)
 */

package com.billingcounter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.billingcounter.model.GroceryItem;
import com.billingcounter.service.GroceryService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController  //Marks this class as REST controller
@RequestMapping("/api/groceries") //Base URL for all grocery endpoints
public class GroceryController {
    
    // Inject the GroceryService to delgate the business logic
    @Autowired
    private GroceryService groceryService;
    
    // GET all groceries
    // Endpoint : GET /api/groceries
    @GetMapping
    public List<GroceryItem> getAllGroceries() {
        return groceryService.getAllGroceries();
    }
    
    // GET single grocery by ID
    // Endpoint: GET /api/groceries/{id}
    @GetMapping("/{id}")
    public ResponseEntity<GroceryItem> getGroceryById(@PathVariable Long id){
        return groceryService.getGroceryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //POST: Add new grocery item
    // Endpoint: POST /api/groceries
    @PostMapping
    public GroceryItem addGroceryItem (@RequestBody GroceryItem item) {
        return groceryService.addGrocery(item);
    }
    

    // PUT: Update existing grocery
    // Endpoint: PUT /api/groceries/{id}
    @PutMapping("/{id}")
    public ResponseEntity<GroceryItem> updateGrocery(@PathVariable Long id, @RequestBody GroceryItem item) {
        return groceryService.updateGrocery(id, item)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Delete / Remove ite, bu ID
    //Endpoint : DELETE /api/groceries/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<GroceryItem> deleteGrocery(@PathVariable Long id){
        groceryService.deleteGrocery(id);
        return ResponseEntity.noContent().build();
    }

    //GET: Low stock groceries (refill alerts)
    // Endpoint: /api/groceries/lowstock
    @GetMapping("/low-stock")
    public List<GroceryItem> getLowStockGroceries(){
        return groceryService.getLowStockItems();
    }

    // @PreAuthorize("hasRole('ADMIN')")
    // @PostMapping
    // public ResponseEntity<GroceryItem> createGrocery(@RequestBody GroceryItem item) {
    // return ResponseEntity.ok(groceryService.addGrocery(item));
    // }

    // @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    // @GetMapping
    // public List<GroceryItem> getAllGroceries() {
    //     return groceryService.getAllGroceries();
    // }


}
