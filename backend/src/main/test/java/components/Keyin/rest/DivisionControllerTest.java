package com.keyin.rest.division;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class DivisionControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private DivisionService divisionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testGetAllDivisions() throws Exception {
        when(divisionService.getAllDivisions()).thenReturn(Arrays.asList(
            new Division("Division 1"),
            new Division("Division 2")
        ));

        mockMvc.perform(get("/division"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].name").value("Division 1"))
            .andExpect(jsonPath("$[1].name").value("Division 2"));
    }

    @Test
    void testGetDivisionById() throws Exception {
        Division division = new Division("Test Division");
        when(divisionService.getDivisionById(eq(1L))).thenReturn(division);

        mockMvc.perform(get("/division/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("Test Division"));
    }

    @Test
    void testCreateDivision() throws Exception {
        Division newDivision = new Division("New Division");
        when(divisionService.createDivision(any(Division.class))).thenReturn(newDivision);

        mockMvc.perform(post("/division")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newDivision)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("New Division"));
    }

    @Test
    void testUpdateDivision() throws Exception {
        Division updatedDivision = new Division("Updated Division");
        when(divisionService.updateDivision(eq(1L), any(Division.class))).thenReturn(updatedDivision);

        mockMvc.perform(put("/division/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDivision)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("Updated Division"));
    }

    @Test
    void testDeleteDivisionById() throws Exception {
        doNothing().when(divisionService).deleteDivisionById(eq(1L));

        mockMvc.perform(delete("/division/1"))
            .andExpect(status().isNoContent());
    }
}
