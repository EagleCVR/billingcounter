package com.billingcounter.service;

import com.billingcounter.model.HotelTable;
import com.billingcounter.repository.HotelTableRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HotelTableServiceTest {

    @Mock
    private HotelTableRepository hotelTableRepository;

    @InjectMocks
    private HotelTableService hotelTableService;

    @Test
    public void testMarkTableAsOccupied() {
        HotelTable table = new HotelTable();
        table.setTableNumber(1);
        table.setTableStatus("free"); // Initial status

        when(hotelTableRepository.findById(1L)).thenReturn(Optional.of(table));
        when(hotelTableRepository.save(any(HotelTable.class))).thenAnswer(i -> i.getArgument(0));

        Optional<HotelTable> result = hotelTableService.markTableAsOccupied(1L);

        assertTrue(result.isPresent());
        assertEquals("OCCUPIED", result.get().getTableStatus()); // Match lowercase
        verify(hotelTableRepository).save(table);
    }
}
