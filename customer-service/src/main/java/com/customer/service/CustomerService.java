package com.customer.service;

import com.customer.entity.Customer;

import java.util.List;

public interface CustomerService {

    public Customer registerCustomer(Customer customer);

    public Customer getCustomerById(Long id);

    public Customer getCustomerByName(String name);

    public List<Customer> getAllCustomers(Integer pageNumber, Integer pageSize);

    public Customer updateCustomer(Long id, Customer customer);

    public void deleteCustomerById(Long id);

}
