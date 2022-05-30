package com.amir.bankkata.service;

import com.amir.bankkata.exception.AccountException;
import com.amir.bankkata.model.Amount;
import com.amir.bankkata.model.Operation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

    @Test
    void test_withdrawal_should_be_return_operation() throws AccountException {
        Operation operation = accountService.withdrawal(2L, 2000);

        assertNotNull(operation);
        assertEquals(operation.getAmount(), new Amount(2000));
        assertEquals(operation.getAccount().getBalance(), new Amount(8000));
    }

    @Test
    void test_withdrawal_when_accountId_not_exist_should_be_return_exception() {
        AccountException ex = assertThrows(AccountException.class, () -> {
            accountService.withdrawal(4L, 5000);
        });
        assertEquals("Technical error : this acccount not exist : 4", ex.getMessage());
    }

    @Test
    void test_withdrawal_when_whithdrawal_amount_bigger_then_balance_should_be_return_exception() {
        AccountException ex = assertThrows(AccountException.class, () -> {
            accountService.withdrawal(2L, 15000);
        });
        assertEquals("Functional error : you cannot withdraw this amount : 15000.0", ex.getMessage());
    }

    @Test
    void test_operationsHistory_should_be_return_list_operations() throws AccountException {
        List<Operation> operations = accountService.operationsHistory(3L);

        assertNotNull(operations);
        assertEquals(operations.size(), 3);
    }
}