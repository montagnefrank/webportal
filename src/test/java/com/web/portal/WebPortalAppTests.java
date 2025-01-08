package com.web.portal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.portal.controllers.*;
import com.web.portal.entities.*;
import com.web.portal.services.*;
import com.web.portal.dto.MovementReportDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WebPortalAppTests {

    @Autowired
    private ObjectMapper objectMapper;
 

    @MockBean
    private AccountService accountService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private MovementService movementService;


    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AccountsController accountsController;

    @InjectMocks
    private CustomersController customersController;

    @InjectMocks
    private MovementsController movementsController;

    @InjectMocks
    private ReportsController reportsController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(
            accountsController, 
            customersController, 
            movementsController, 
            reportsController
        ).build();
    }
 
    @Test
    void getAllAccountsTest() throws Exception {
        Account account1 = new Account(1L, "12345", "John Doe");
        Account account2 = new Account(2L, "67890", "Jane Doe");
        when(accountService.getAllAccounts()).thenReturn(Arrays.asList(account1, account2));

        mockMvc.perform(get("/cuentas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountNumber").value("12345"))
                .andExpect(jsonPath("$[1].accountNumber").value("67890"));
    }

    @Test
    void createAccountTest() throws Exception {
        Account account = new Account(1L, "12345", "John Doe");
        when(accountService.createAccount(any(Account.class))).thenReturn(account);

        mockMvc.perform(post("/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("12345"));
    }

    @Test
    void deleteAccountTest() throws Exception {
        doNothing().when(accountService).deleteAccount(1L);

        mockMvc.perform(delete("/cuentas/1"))
                .andExpect(status().isNoContent());
    }
 
    @Test
    void getAllCustomersTest() throws Exception {
        Customer customer1 = new Customer(1L, "John Doe");
        Customer customer2 = new Customer(2L, "Jane Doe");
        when(customerService.findAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));
    }

    @Test
    void createCustomerTest() throws Exception {
        Customer customer = new Customer(1L, "John Doe");
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void getAllMovementsTest() throws Exception {
        Account account1 = new Account(1L, "12345", "Savings");
        Account account2 = new Account(2L, "67890", "Checking");

        Movement movement1 = new Movement(1L, account1, 100.0);
        Movement movement2 = new Movement(2L, account2, 200.0);

        when(movementService.getAllMovements()).thenReturn(Arrays.asList(movement1, movement2));

        mockMvc.perform(get("/movimientos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].account.accountNumber").value("12345"))
                .andExpect(jsonPath("$[1].account.accountNumber").value("67890"));
    }

    @Test
    void createMovementTest() throws Exception {
        Account account = new Account(1L, "12345", "John Doe");

        Movement movement = new Movement(1L, account, 100.0);

        when(movementService.createMovement(any(Movement.class))).thenReturn(movement);

        mockMvc.perform(post("/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movement)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.account.accountNumber").value("12345"));
    }

 
    @Test
    void getMovementReportsByDateRangeTest() throws Exception {
        MovementReportDTO report1 = new MovementReportDTO(
                "2023-01-01", "John Doe", "12345", "Credit", 1000.0, true, 100.0, 1100.0);
        MovementReportDTO report2 = new MovementReportDTO(
                "2023-01-02", "Jane Doe", "67890", "Debit", 2000.0, true, 200.0, 1800.0);

        when(movementService.getMovementReportsByDateRange(any(), any()))
                .thenReturn(Arrays.asList(report1, report2));

        mockMvc.perform(get("/reportes/fecha")
                        .param("startDate", "2023-01-01")
                        .param("endDate", "2023-01-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroCuenta").value("12345"))
                .andExpect(jsonPath("$[1].numeroCuenta").value("67890"));
    }
}
