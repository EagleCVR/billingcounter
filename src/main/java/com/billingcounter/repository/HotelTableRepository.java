package com.billingcounter.repository;

import com.billingcounter.model.HotelTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * REPOSITORY LAYER: HotelTableRepository
 * Extends JpaRepository to get all built-in CRUD operations for free.
 *
 * Interview Note:
 * - No need to write SQL manually
 * - Use method names like `findByStatus` to auto-generate queries
 */
@Repository
public interface HotelTableRepository extends JpaRepository<HotelTable, Long> {
    List<HotelTable> findByTableStatus(String tableStatus); // CORRECT
} 