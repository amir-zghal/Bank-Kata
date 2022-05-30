package com.amir.bankkata.service.impl;

import com.amir.bankkata.exception.AccountException;
import com.amir.bankkata.model.*;
import com.amir.bankkata.service.AccountService;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.random.RandomGenerator;

/**
 * Impl de la couche métier de gestion de compte
 */
@Service
public class AccountServiceImpl implements AccountService {
    private static Map<Long, Customer> customers = new HashMap<>();
    private static Map<Long, Account> accounts = new HashMap<>();
    private static Map<Long, Operation> operations = new HashMap<>();

    // TODO replace Map object with repository
    @PostConstruct
    private void initialize() throws Exception {
        customers.put(1L, Customer.builder().id(1L).name("Customer_1").phoneNumber("0033122334411").build());

        accounts.put(1L, Account.builder().id(1L).customer(customers.get(1)).iban("FR8 0033 XXXX XXXX 11").balance(new Amount(0)).build());
    }

    @Override
    public Operation deposit(Long accountId, double amount) throws AccountException {
        Preconditions.checkNotNull(accountId, "AccountId must be not null");
        if(!accounts.containsKey(accountId)) {
            throw new AccountException("Technical error : this acccount not exist : "+accountId);
        }
        Account account = accounts.get(accountId);
        Amount depositAmount = new Amount(amount);
        account.setBalance(account.getBalance().plus(depositAmount));
        Operation operation = Operation.builder()
                .account(account)
                .operationDate(LocalDateTime.now())
                .operationType(OperationTypeEnum.DEPOSIT)
                .amount(depositAmount)
                .id(RandomGenerator.getDefault().nextLong())
                .build();
        operations.put(operation.getId(), operation);
        return operation;
    }
}
