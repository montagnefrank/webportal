package com.web.portal.services;

import com.web.portal.entities.Customer;
import com.web.portal.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public List<Customer> findCustomersByName(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        return customerRepository.findById(id).map(existingCustomer -> {
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setGender(updatedCustomer.getGender());
            existingCustomer.setAge(updatedCustomer.getAge());
            existingCustomer.setIdentification(updatedCustomer.getIdentification());
            existingCustomer.setAddress(updatedCustomer.getAddress());
            existingCustomer.setTelephone(updatedCustomer.getTelephone());
            existingCustomer.setPassword(updatedCustomer.getPassword());
            existingCustomer.setState(updatedCustomer.getState());
            return customerRepository.save(existingCustomer);
        }).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
