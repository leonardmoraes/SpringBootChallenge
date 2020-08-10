package com.compassouol.service;

import com.compassouol.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CustomerServiceJPATest {

    @Autowired
    private CustomerService service;

    @Test
    void search() {
        Map filter = new HashMap<>();
        List<Customer> result = service.search(filter);
        assertEquals("Leonard Chucre de Moraes", result.get(0).getName());
        assertEquals("Adriana Ritta", result.get(1).getName());
        assertEquals("Sofia Ritta Chucre de Moraes", result.get(2).getName());
    }

    @Test
    void searchById() {
        Map filter = new HashMap<>();
        filter.put("id", 1);
        List<Customer> result = service.search(filter);
        assertEquals(1, result.size());
        assertEquals("Leonard Chucre de Moraes", result.get(0).getName());
    }

    @Test
    void searchByName() {
        Map filter = new HashMap<>();
        filter.put("name", "Ritta");
        List<Customer> result = service.search(filter);
        assertEquals(2, result.size());
        assertEquals("Adriana Ritta", result.get(0).getName());
        assertEquals("Sofia Ritta Chucre de Moraes", result.get(1).getName());
    }

    @Test
    void searchByIdNotFound() {
        Map filter = new HashMap<>();
        filter.put("id", 999);
        List<Customer> result = service.search(filter);
        assertEquals(0, result.size());
    }

    @Test
    void searchByNameNotFound() {
        Map filter = new HashMap<>();
        filter.put("name", "Não Encontrado");
        List<Customer> result = service.search(filter);
        assertEquals(0, result.size());
    }

    @Test
    void create() {
        Customer customer = new Customer(null, "João da Silva", "M", LocalDate.of(1980, 3, 18), null);
        Long result = service.create(customer);
        assertEquals(4, result);
    }

    @Test
    void update() throws Exception {
        Customer customer = new Customer(null, "João da Silva", "M", LocalDate.of(1980, 3, 18), null);
        Long original = service.create(customer);

        customer = new Customer(original, "João da Conceição", "M", LocalDate.of(1980, 3, 18), null);
        Long result = service.update(customer);
        assertEquals(original, result);
    }

    @Test
    void delete() throws Exception {
        Customer customer = new Customer(null, "João da Silva", "M", LocalDate.of(1980, 3, 18), null);
        Long original = service.create(customer);
        String result = service.delete(original);
        assertEquals("{\"status\": \"OK\", \"message\": \"Customer '" + original + "' deleted\"}", result);
    }

    @Test
    void deleteNotFound() throws Exception {
        String result = service.delete(999L);
        assertEquals("{\"status\": \"ERROR\", \"message\": \"Customer with id '999' not found\"}", result);
    }
}