package com.customer.controller;

import com.customer.entity.Customer;
import com.customer.payloads.CustomerResponse;
import com.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customer")
@Validated
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@Valid @RequestBody Customer customer) {
        log.info("registerCustomer() method of CustomerController is called");

        Customer savedCustomer = customerService.registerCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) {
        log.info("getCustomerById() method of CustomerController is called");

        Customer customer = customerService.getCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Customer> getCustomerByName(@PathVariable("name") String name) {
        log.info("getCustomerByName() method of CustomerController is called");

        Customer customer = customerService.getCustomerByName(name);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<CustomerResponse> getAllCustomers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSze
    ) {
        log.info("getAllCustomers() method of CustomerController is called");

        CustomerResponse customers = customerService.getAllCustomers(pageNumber, pageSze);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
        log.info("updateCustomer() method of CustomerController is called");

        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable("id") Long id) {
        log.info("deleteCustomerById() method of CustomerController is called");

        customerService.deleteCustomerById(id);
        return new ResponseEntity<>("Customer deleted successfully by Id: "+id, HttpStatus.OK);
    }
}
