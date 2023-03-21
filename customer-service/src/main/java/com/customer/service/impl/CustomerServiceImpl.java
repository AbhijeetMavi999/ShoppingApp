package com.customer.service.impl;

import com.customer.entity.Customer;
import com.customer.exception.CustomerAlreadyExistException;
import com.customer.exception.CustomerNotFoundException;
import com.customer.repository.CustomerRepository;
import com.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer registerCustomer(Customer customer) {
        log.info("registerCustomer() method of CustomerServiceImpl is called");

        Customer findedCustomer = customerRepository.findByEmail(customer.getEmail());
        if(findedCustomer != null) {
            log.warn("This email is already present in our database");
            throw new CustomerAlreadyExistException("This email is already present in our database");
        }

        Customer registeredCustomer = customerRepository.save(customer);
        return registeredCustomer;
    }

    @Override
    public Customer getCustomerById(Long id) {
        log.info("getCustomerById() method of CustomerServiceImpl is called");

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found by id: "+id));
        return customer;
    }

    @Override
    public Customer getCustomerByName(String name) {
        log.info("getCustomerByName() method of CustomerServiceImpl is called");

        Customer customer = customerRepository.findByName(name);
        if(customer == null) {
            log.warn("Customer not found by name: "+name);
            throw new CustomerNotFoundException("Customer not found by name: "+name);
        }
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        log.info("getAllCustomers() method of CustomerServiceImpl is called");

        List<Customer> customers = customerRepository.findAll();
        if(customers.size() == 0) {
            log.warn("Customers not found");
            throw new CustomerNotFoundException("Customers not found");
        }
        return customers;
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        log.info("updateCustomer() method of CustomerServiceImpl is called");

        Customer update = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found by id: "+id));

        update.setName(customer.getName());
        update.setEmail(customer.getEmail());
        update.setPhNumber(customer.getPhNumber());

        Customer updatedCustomer = customerRepository.save(update);
        return updatedCustomer;
    }

    @Override
    public void deleteCustomerById(Long id) {
        log.info("deleteCustomerById() method of CustomerServiceImpl is called");

        customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found by id: "+id));

        customerRepository.deleteById(id);
    }
}
