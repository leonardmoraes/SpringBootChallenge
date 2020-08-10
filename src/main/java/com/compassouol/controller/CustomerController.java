package com.compassouol.controller;

import com.compassouol.model.Customer;
import com.compassouol.service.CustomerService;
import com.compassouol.utils.RequestEntityUtil;
import com.compassouol.utils.ResponseEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService service;


    @GetMapping("")
    public ResponseEntity searchNoFilter() {
        // return ResponseEntity.badRequest().body("Filter is missing.");
        return search("{}");
    }

    @GetMapping("/{filter}")
    public ResponseEntity search(@PathVariable String filter) {
        try {
            Map filterValues = RequestEntityUtil.getValuesFromFilter(filter);
            List result = service.search(filterValues);
            return ResponseEntityUtil.responseHandler(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity create(@RequestBody Customer data) {
        try {
            Long result = service.create(data);
            return ResponseEntityUtil.responseHandler(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PutMapping(value = {"", "/"})
    public ResponseEntity update(@RequestBody Customer data) {
        try {
            Long result = service.update(data);
            return ResponseEntityUtil.responseHandler(result);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            String result = service.delete(id);
            return ResponseEntityUtil.responseHandler(result);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @DeleteMapping(value = {"", "/"})
    public ResponseEntity deleteNoId() {
         return ResponseEntity.badRequest().body("Id is missing.");
    }
}
