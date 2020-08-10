package com.compassouol.service;

import com.compassouol.model.Customer;
import com.compassouol.repository.CustomerRepository;
import com.compassouol.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerServiceJPA implements CustomerService {

    public static final String DELETE_MESSAGE_OK = "{\"status\": \"OK\", \"message\": \"Customer '%d' deleted\"}";
    public static final String DELETE_MESSAGE_ERROR = "{\"status\": \"ERROR\", \"message\": \"Customer with id '%d' not found\"}";

    @Autowired
    CustomerRepository repository;


    public List<Customer> search(Map filter) {
        Long id = ConvertUtil.getLongValue(filter.get("id"));
        if (id != null) {
            Optional<Customer> customerById = repository.findById(id);
            return customerById.isPresent() ? Arrays.asList(customerById.get()) : Collections.EMPTY_LIST;
        }

        String name = (String) filter.get("name");
        if (name != null && !name.isEmpty()) {
            return repository.findByName(name);
        }

        return repository.findAll();
    }


    public Long create(Customer data) {
        Customer customer = repository.save(data);
        return customer.getId();
    }


    public Long update(Customer data) throws Exception {
        Optional<Customer> customerById = repository.findById(data.getId());
        if (customerById.isPresent()) {
            Customer customer = repository.save(data);
            return customer.getId();
        } else {
            throw new Exception(String.format("Customer %d not found.", data.getId()));
        }
    }


    public String delete(Long id) {
        Optional<Customer> customerById = repository.findById(id);
        if (customerById.isPresent()) {
            repository.delete(customerById.get());
            return String.format(DELETE_MESSAGE_OK, id);
        } else {
            return String.format(DELETE_MESSAGE_ERROR, id);
        }
    }
}
