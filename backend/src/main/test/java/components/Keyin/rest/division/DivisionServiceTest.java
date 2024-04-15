package com.keyin.rest.division;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DivisionServiceTest {

    @Mock
    private DivisionRepository divisionRepository;

    @InjectMocks
    private DivisionService divisionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDivisions() {
        List<Division> divisions = Arrays.asList(
            new Division("Division 1"),
            new Division("Division 2")
        );
        when(divisionRepository.findAll()).thenReturn(divisions);

        List<Division> result = divisionService.getAllDivisions();
        assertEquals(divisions.size(), result.size());
    }

    @Test
    void testGetDivisionById() {
        Division division = new Division("Test Division");
        when(divisionRepository.findById(eq(1L))).thenReturn(Optional.of(division));

        Division result = divisionService.getDivisionById(1L);
        assertNotNull(result);
        assertEquals(division.getName(), result.getName());
    }

    @Test
    void testCreateDivision() {
        Division division = new Division("New Division");
        when(divisionRepository.save(any(Division.class))).thenReturn(division);

        Division result = divisionService.createDivision(division);
        assertNotNull(result);
        assertEquals(division.getName(), result.getName());
    }

    @Test
    void testUpdateDivision() {
        Division existingDivision = new Division(1L);
        existingDivision.setName("Old Division");
        Division updatedDivision = new Division("Updated Division");
        when(divisionRepository.findById(eq(1L))).thenReturn(Optional.of(existingDivision));
        when(divisionRepository.save(any(Division.class))).thenReturn(updatedDivision);

        Division result = divisionService.updateDivision(1L, updatedDivision);
        assertNotNull(result);
        assertEquals(updatedDivision.getName(), result.getName());
    }

    @Test
    void testDeleteDivisionById() {
        doNothing().when(divisionRepository).deleteById(eq(1L));

        divisionService.deleteDivisionById(1L);
        verify(divisionRepository, times(1)).deleteById(eq(1L));
    }
}
