package com.compassouol.service;

import com.compassouol.model.City;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CityServiceJPATest {

    @Autowired
    private CityService service;


    @Test
    void search() {
        Map filter = new HashMap<>();
        List<City> result = service.search(filter);
        assertTrue(result.size() > 1);
    }

    @Test
    void searchById() {
        Map filter = new HashMap<>();
        filter.put("id", 1001);
        List<City> result = service.search(filter);
        assertEquals(1, result.size());
        assertEquals("Florianópolis", result.get(0).getName());
    }

    @Test
    void searchByName() {
        Map filter = new HashMap<>();
        filter.put("name", "Passo");
        List<City> result = service.search(filter);
        assertEquals(1, result.size());
        assertEquals("Passo Fundo", result.get(0).getName());
    }

    @Test
    void searchByIdNotFound() {
        Map filter = new HashMap<>();
        filter.put("id", 999);
        List<City> result = service.search(filter);
        assertEquals(0, result.size());
    }

    @Test
    void searchByNameNotFound() {
        Map filter = new HashMap<>();
        filter.put("name", "Não Encontrado");
        List<City> result = service.search(filter);
        assertEquals(0, result.size());
    }

    @Test
    void create() {
        City city = new City(null, "Santos", "SP");
        Long result = service.create(city);
        assertEquals(1, result);
    }

    @Test
    void update() throws Exception {
        City city = new City(null, "Pato Branco", "PR");
        Long original = service.create(city);

        city = new City(original, "Santa Rosa", "SE");
        Long result = service.update(city);
        assertEquals(original, result);
    }
}