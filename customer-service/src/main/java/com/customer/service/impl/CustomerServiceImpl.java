package com.customer.service.impl;

import com.customer.entity.Customer;
import com.customer.exception.CustomerAlreadyExistException;
import com.customer.exception.CustomerNotFoundException;
import com.customer.payloads.CustomerResponse;
import com.customer.repository.CustomerRepository;
import com.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional(propagation = Propagation.REQUIRED)
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

    @Cacheable(cacheNames = "customer", key = "#id")
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
    public CustomerResponse getAllCustomers(Integer pageNumber, Integer pageSize) {
        log.info("getAllCustomers() method of CustomerServiceImpl is called");


        // Implementing pagination
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Customer> customerPage = customerRepository.findAll(pageable);
        List<Customer> customers = customerPage.getContent();

        if(customers.size() == 0) {
            log.warn("Customers not found");
            throw new CustomerNotFoundException("Customers not found");
        }

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setContent(customers);
        customerResponse.setPageNumber(customerPage.getNumber());
        customerResponse.setPageSize(customerPage.getSize());
        customerResponse.setTotalRecords(customerPage.getTotalElements());
        customerResponse.setTotalPages(customerPage.getTotalPages());
        customerResponse.setIsLastPage(customerPage.isLast());

        return customerResponse;
    }

    @CachePut(cacheNames = "customer", key = "#id")
    @Transactional(propagation = Propagation.REQUIRED)
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

    @CacheEvict(cacheNames = "customer", key = "#id")
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteCustomerById(Long id) {
        log.info("deleteCustomerById() method of CustomerServiceImpl is called");

        customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found by id: "+id));

        customerRepository.deleteById(id);
    }
}
