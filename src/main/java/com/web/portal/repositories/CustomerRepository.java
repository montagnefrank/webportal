package com.web.portal.repositories;

import com.web.portal.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Additional query methods can be added here if needed
}
