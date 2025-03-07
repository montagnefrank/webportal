package com.web.portal.repositories;

import com.web.portal.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> { 
    Account findByAccountNumber(String accountNumber);
    List<Account> findByAccountNumberContainingIgnoreCase(String accountNumber);
    List<Account> findByCustomer_NameContainingIgnoreCase(String customerName);
}
