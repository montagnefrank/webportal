package com.web.portal.services;

import com.web.portal.entities.Account;
import com.web.portal.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public List<Account> getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumberContainingIgnoreCase(accountNumber);
    }

    public List<Account> getAccountsByCustomerName(String customerName) {
        return accountRepository.findByCustomer_NameContainingIgnoreCase(customerName);
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account updateAccount(Long id, Account updatedAccount) {
        return accountRepository.findById(id).map(account -> {
            account.setAccountNumber(updatedAccount.getAccountNumber());
            account.setAccountType(updatedAccount.getAccountType());
            account.setInitialBalance(updatedAccount.getInitialBalance());
            account.setStatus(updatedAccount.getStatus());
            account.setCustomer(updatedAccount.getCustomer());
            return accountRepository.save(account);
        }).orElse(null);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
