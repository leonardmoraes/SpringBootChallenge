package com.compassouol.service;

import com.compassouol.model.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    Long create(Customer data);

    Long update(Customer data) throws Exception;

    List<Customer> search(Map filter);

    String delete(Long id);

}
