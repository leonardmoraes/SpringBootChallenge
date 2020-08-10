package com.compassouol.controller;

import com.compassouol.model.City;
import com.compassouol.service.CityService;
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
class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CityService service;


    @Test
    void searchNoFilter() throws Exception {
        mockMvc.perform(
            get("/city"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[*].id").isNotEmpty());
        ;
    }

    @Test
    void searchByIdNotFound() throws Exception {
        mockMvc.perform(
            get("/city/{filter}", "{\"id\": 9999}")
                .contentType("application/json"))
                .andExpect(status().isNoContent());
    }

    @Test
    void searchById() throws Exception {
        mockMvc.perform(
            get("/city/{filter}", "{\"id\": 1001}")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[*].id").value(1001))
        ;
    }

    @Test
    void searchByNameNotFound() throws Exception {
        mockMvc.perform(
            get("/city/{filter}", "{\"name\": \"Não Encontrado\"}")
                .contentType("application/json"))
                .andExpect(status().isNoContent());
        ;
    }


    @Test
    void searchByName() throws Exception {
        String filter = "{\"name\":\"Passo Fundo\"}";
        mockMvc.perform(
            get("/city/{filter}", filter)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[*].id").value(1002))
        ;

    }


    @Test
    void searchByState() throws Exception {
        mockMvc.perform(
                get("/city/{filter}", "{\"state\": \"RS\"}")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[*].id").value(1002))
        ;
    }



    @Test
    void searchByNameAndState() throws Exception {
        mockMvc.perform(
                get("/city/{filter}", "{\"name\": \"Passo\", \"state\": \"RS\"}")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[*].id").value(1002))
        ;
    }


    @Test
    void searchByStateNotFound() throws Exception {
        mockMvc.perform(
            get("/city/{filter}", "{\"state\": \"Não Encontrado\"}")
                .contentType("application/json"))
                .andExpect(status().isNoContent());
        ;
    }

    @Test
    void create() throws Exception {
        City city = new City(null, "Santos", "SP");

        mockMvc.perform(
            post("/city").content(asJsonString(city))
                .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").value(1))
        ;
    }

    @Test
    void update() throws Exception {
        City original = new City(null, "Taubaté", "SP");
        mockMvc.perform(
            post("/city").content(asJsonString(original))
                .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(1))
        ;

        City city = new City(2L, "São José", "SC");

        mockMvc.perform(
            put("/city")
                .content(asJsonString(city))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").value(2))
        ;

        mockMvc.perform(
            get("/city/{filter}", "{\"id\": 2}")
                .contentType("application/json"))
                .andExpect(jsonPath("$[*].name").value("São José"))
                .andExpect(jsonPath("$[*].state").value("SC"))
        ;
    }

    @Test
    void updateNotFound() throws Exception {
        City city = new City(999L, "Pato Branco", "PR");

        mockMvc.perform(
            put("/city")
                .content(asJsonString(city))
                .contentType("application/json"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").value("City 999 not found."))
        ;

        mockMvc.perform(
        get("/city/{filter}", "{\"id\": 999}")
                .contentType("application/json"))
                .andExpect(status().isNoContent())
        ;
    }


    @Test
    void delete() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/city/{id}", 1001))
                .andExpect(status().isMethodNotAllowed())
        ;
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/city/{id}", 1))
                .andExpect(status().isMethodNotAllowed())
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