package com.billingcounter.service;

import com.billingcounter.model.HotelTable;
import com.billingcounter.repository.HotelTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * HotelTableService
 *
 * This service class handles business logic for managing hotel dining tables.
 * Responsibilities:
 * - Add, update, delete tables
 * - Mark tables as occupied or free
 * - Help controller stay thin by abstracting logic here
 *
 * Interview Note:
 * - Having this intermediate service layer is good practice (N-tier architecture).
 * - It improves maintainability, reusability, and testability.
 */
@Service
public class HotelTableService {

    // Inject HotelTableRepository to interact with the database
    @Autowired
    public HotelTableRepository hotelTableRepository;

    public HotelTableService(HotelTableRepository hotelTableRepository) {
        this.hotelTableRepository = hotelTableRepository;
    }
    
    // Get all hotel tables
    public List<HotelTable> getAllTables() {
        return hotelTableRepository.findAll();
    }

    // Get a single table by ID
    public Optional<HotelTable> getTableById(Long id) {
        return hotelTableRepository.findById(id);
    }

    // Add a new table
    public HotelTable addHotelTable(HotelTable addTable) {
        return hotelTableRepository.save(addTable);
    }

    // Update existing table info by ID
    public Optional<HotelTable> updateTable(Long id, HotelTable updatedTable) {
        return hotelTableRepository.findById(id).map(existingTable -> {
            existingTable.setTableStatus(updatedTable.getTableStatus()); // Update status (FREE/OCCUPIED)
            existingTable.setTableNumber(updatedTable.getTableNumber()); // Update table number
            return hotelTableRepository.save(existingTable); // Save changes
        });
    }

    // Delete table by ID
    public void deleteTable(Long id) {
        hotelTableRepository.deleteById(id);
    }

    // Mark a table as OCCUPIED (usually when order is placed)
    public Optional<HotelTable> markTableAsOccupied(Long id) {
        return hotelTableRepository.findById(id).map(tableExists -> {
            tableExists.setTableStatus("OCCUPIED");
            return hotelTableRepository.save(tableExists);
        });
    }

    // Mark a table as FREE (usually after bill is paid)
    public Optional<HotelTable> markTableAsFree(Long id) {
        return hotelTableRepository.findById(id).map(tableExists -> {
            tableExists.setTableStatus("FREE"); // 🔁 Fix: previously set to OCCUPIED
            return hotelTableRepository.save(tableExists);
        });
    }
}