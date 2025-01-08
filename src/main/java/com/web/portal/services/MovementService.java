package com.web.portal.services;

import com.web.portal.entities.Movement;
import com.web.portal.entities.Account;
import com.web.portal.entities.Customer;
import com.web.portal.repositories.MovementRepository;
import com.web.portal.repositories.AccountRepository;
import com.web.portal.dto.MovementReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MovementService {

    private static final Logger logger = LoggerFactory.getLogger(MovementService.class);

    @Autowired
    private MovementRepository movementRepository;
 
    @Autowired
    private AccountRepository accountRepository;

    public List<Movement> getAllMovements() {
        return movementRepository.findAll();
    }

    public Optional<Movement> getMovementById(Long id) {
        return movementRepository.findById(id);
    }
    public List<Movement> getMovementsByAccountNumber(String accountNumber) {
        return movementRepository.findByAccount_AccountNumberContainingIgnoreCase(accountNumber); 
    }

    public Movement createMovement(Movement movement) {
        Account account = accountRepository.findById(movement.getAccount().getId())
                .orElseThrow(() -> new RuntimeException("Cuenta No Existe"));
 
        double newBalance;
        if ("D".equalsIgnoreCase(movement.getType())) {
            if (account.getInitialBalance() < movement.getAmount()) {
                throw new RuntimeException("Saldo no disponible.");
            }
            newBalance = account.getInitialBalance() - movement.getAmount();
        } else if ("C".equalsIgnoreCase(movement.getType())) {
            newBalance = account.getInitialBalance() + movement.getAmount();
        } else {
            throw new RuntimeException("Tipo invalido.");
        }

        movement.setBalance(account.getInitialBalance());
        movement.setDate(LocalDateTime.now());
 
        Movement savedMovement = movementRepository.save(movement);
 
        account.setInitialBalance(newBalance);
        accountRepository.save(account);

        return savedMovement;
    }

    public Movement updateMovement(Long id, Movement updatedMovement) {
        return movementRepository.findById(id).map(movement -> {
            movement.setAccount(updatedMovement.getAccount());
            movement.setType(updatedMovement.getType());
            movement.setAmount(updatedMovement.getAmount());
            movement.setBalance(updatedMovement.getBalance());
            movement.setDate(updatedMovement.getDate());
            return movementRepository.save(movement);
        }).orElse(null);
    }

    public void deleteMovement(Long id) {
        movementRepository.deleteById(id);
    }
 
    
    public List<MovementReportDTO> getMovementReportsByDateRange(LocalDateTime startDate, LocalDateTime endDate) { 
        logger.info("Fetching movement reports for date range: Start = {}, End = {}", startDate, endDate);
        List<Movement> movements = movementRepository.findByDateBetween(startDate, endDate); 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (movements.isEmpty()) {
            logger.warn("No movements found between {} and {}", startDate, endDate);
        } else {
            logger.info("Found {} movements between {} and {}", movements.size(), startDate, endDate);
        }
            
        return movements.stream().map(movement -> {
            Account account = movement.getAccount();
            Customer customer = account.getCustomer();

            logger.debug("Processing movement: Date = {}, Customer = {}, Account = {}",
                    movement.getDate(), customer.getName(), account.getAccountNumber());

            return new MovementReportDTO(
                movement.getDate().format(formatter),  
                customer.getName(),                    
                account.getAccountNumber(),            
                account.getAccountType(),             
                account.getInitialBalance(),           
                "TRUE".equalsIgnoreCase(account.getStatus()),  
                movement.getAmount(),                  
                movement.getBalance()                  
            );
        }).collect(Collectors.toList());
    }
}
