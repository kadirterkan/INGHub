package org.com.bio.inghub.application.service;

import org.com.bio.inghub.domain.model.Customer;

import java.util.List;

public interface CustomerService {
     Customer save(Customer customer);

     void deleteCustomer(Long id);

     Customer findCustomerById(Long id);

     List<Customer> findAllCustomers();
}
