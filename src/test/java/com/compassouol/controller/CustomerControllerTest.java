package com.compassouol.controller;

import com.compassouol.model.City;
import com.compassouol.model.Customer;
import com.compassouol.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerService service;


    @Test
    void searchNoFilter() throws Exception {
        mockMvc.perform(get("/customer"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$[*].id").isNotEmpty());
        ;
    }

    @Test
    void searchByIdNotFound() throws Exception {
        mockMvc.perform(
            get("/customer/{filter}", "{\"id\": 9999}")
            .contentType("application/json"))
            .andExpect(status().isNoContent());
    }

    @Test
    void searchById() throws Exception {
        mockMvc.perform(
            get("/customer/{filter}", "{\"id\": 1}")
            .contentType("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$[*].id").value(1))
        ;
    }

    @Test
    void searchByNameNotFound() throws Exception {
        mockMvc.perform(
            get("/customer/{filter}", "{\"name\": \"Não Encontrado\"}")
            .contentType("application/json"))
            .andExpect(status().isNoContent());
        ;
    }

    @Test
    void searchByName() throws Exception {
        mockMvc.perform(
            get("/customer/{filter}", "{\"name\": \"Leonard\"}")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[*].id").value(1))
        ;
    }

    @Test
    void create() throws Exception {
        City city = new City(1001L, "Florianópolis", "SC");
        Customer customer = new Customer(null, "João da Silva", "M", null, city);

        mockMvc.perform(
            post("/customer").content(asJsonString(customer))
                .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").value(4))
        ;
    }

    @Test
    void update() throws Exception {
        City city = new City(1001L, "Florianópolis", "SC");
        Customer customer = new Customer(3L, "João da Silva", "M", null, city);

        mockMvc.perform(
            put("/customer")
                .content(asJsonString(customer))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").value(3))
        ;

        mockMvc.perform(
            get("/customer/{filter}", "{\"id\": 3}")
                .contentType("application/json"))
                .andExpect(jsonPath("$[*].name").value("João da Silva"))
        ;
    }

    @Test
    void updateNotFound() throws Exception {
        Customer customer = new Customer(999L, "João da Silva", "M", null, null);

        mockMvc.perform(
            put("/customer")
                .content(asJsonString(customer))
                .contentType("application/json"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").value("Customer 999 not found."))
        ;

        mockMvc.perform(
            get("/customer/{filter}", "{\"id\": 999}")
                .contentType("application/json"))
                .andExpect(status().isNoContent())
        ;
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/customer/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("Customer '2' deleted"))
        ;
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/customer/{id}", 999))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.message").value("Customer with id '999' not found"))
        ;
    }

    @Test
    void deleteNoId() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/customer"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").value("Id is missing."))
        ;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}