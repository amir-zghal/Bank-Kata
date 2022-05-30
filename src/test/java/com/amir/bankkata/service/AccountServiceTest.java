package com.amir.bankkata.service;

import com.amir.bankkata.exception.AccountException;
import com.amir.bankkata.model.Amount;
import com.amir.bankkata.model.Operation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    void test_deposit_should_be_return_operation() throws AccountException {
        Operation operation = accountService.deposit(1L, 5000);

        assertNotNull(operation);
        assertEquals(operation.getAmount(), new Amount(5000));
        assertEquals(operation.getAccount().getBalance(), new Amount(5000));
    }

    @Test
    void test_deposit_when_accountId_null_should_be_return_exception() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> {
            accountService.deposit(null, 5000);
        });
        assertEquals("AccountId must be not null", ex.getMessage());
    }
}