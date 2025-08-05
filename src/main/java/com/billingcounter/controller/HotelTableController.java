package com.billingcounter.controller;

import java.util.List;

import com.billingcounter.model.HotelTable;
import com.billingcounter.service.HotelTableService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * CONTROLLER LAYER: HotelTableController
 * 
 * Responsibilities:
 * - Acts as the entry point for HTTP requests related to hotel tables.
 * - Delegates business logic to the Service layer.
 * 
 * Annotations Used:
 * - @RestController: Combines @Controller + @ResponseBody (returns JSON directly).
 * - @RequestMapping: Sets base URI for all methods in this controller.
 * - @Autowired: Injects the HotelTableService bean from the application context.
 * - @GetMapping, @PostMapping, etc.: Maps HTTP methods to controller methods.
 */

@RestController
@RequestMapping("/api/hoteltables") // 👈 Base URL path for this controller
public class HotelTableController {

    // Injecting service layer using Spring's dependency injection
    @Autowired
    private HotelTableService hotelTableService;

    /**
     * GET /api/hoteltables
     * - Returns a list of all hotel tables
     * - HTTP 200 OK
     */
    @GetMapping
    public List<HotelTable> getAllTables() {
        return hotelTableService.getAllTables(); // Delegates to service
    }

    /**
     * GET /api/hoteltables/{id}
     * - Returns a specific table by ID
     * - If not found, returns 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<HotelTable> getTableById(@PathVariable Long id) {
        return hotelTableService.getTableById(id)
                .map(ResponseEntity::ok) // 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }

    /**
     * POST /api/hoteltables
     * - Creates a new hotel table
     * - HTTP 201 Created with body
     */
    @PostMapping
    public ResponseEntity<HotelTable> addTable(@RequestBody HotelTable hotelTable) {
        HotelTable savedTable = hotelTableService.addHotelTable(hotelTable);
        return ResponseEntity.status(201).body(savedTable); // 201 Created
    }

    /**
     * PUT /api/hoteltables/{id}
     * - Updates an existing hotel table by ID
     * - Returns 200 OK if updated, or 404 Not Found if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<HotelTable> updateHotelTable(@PathVariable Long id,
                                                       @RequestBody HotelTable updateTable) {
        return hotelTableService.updateTable(id, updateTable)
                .map(updated -> ResponseEntity.ok(updated)) // 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    /**
     * DELETE /api/hoteltables/{id}
     * - Deletes a hotel table by ID
     * - Returns 204 No Content (no body) if success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTableById(@PathVariable Long id) {
        hotelTableService.deleteTable(id); // Assuming service handles non-existence silently
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    /**
     * PUT /api/hoteltables/{id}/occupy
     * - Marks a table as OCCUPIED
     * - Returns 200 OK if successful or 404 if not found
     */
    @PutMapping("/{id}/occupy")
    public ResponseEntity<HotelTable> setTableOccupy(@PathVariable Long id) {
        return hotelTableService.markTableAsOccupied(id)
                .map(occupiedTable -> ResponseEntity.ok(occupiedTable))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * PUT /api/hoteltables/{id}/free
     * - Marks a table as FREE
     * - Returns 200 OK if successful or 404 if not found
     */
    @PutMapping("/{id}/free")
    public ResponseEntity<HotelTable> setTableFree(@PathVariable Long id) {
        return hotelTableService.markTableAsFree(id)
                .map(freedTable -> ResponseEntity.ok(freedTable))
                .orElse(ResponseEntity.notFound().build());
    }
}
