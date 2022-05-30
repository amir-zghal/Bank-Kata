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
import java.util.stream.Collectors;

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
        customers.put(2L, Customer.builder().id(2L).name("Customer_2").phoneNumber("0033122334422").build());
        customers.put(3L, Customer.builder().id(3L).name("Customer_3").phoneNumber("0033122334433").build());

        accounts.put(1L, Account.builder().id(1L).customer(customers.get(1)).iban("FR8 0033 XXXX XXXX 11").balance(new Amount(0)).build());
        accounts.put(2L, Account.builder().id(2L).customer(customers.get(2)).iban("FR7 0034 XXXX XXXX 22").balance(new Amount(10000)).build());
        accounts.put(3L, Account.builder().id(3L).customer(customers.get(3)).iban("FR6 0035 XXXX XXXX 33").balance(new Amount(20000)).build());

        Operation operation1 = this.deposit(3L, 12000);
        Operation operation2 = this.withdrawal(3L, 3500);
        Operation operation3 = this.deposit(3L, 9000);
        operations.put(operation1.getId(), operation1);
        operations.put(operation2.getId(), operation2);
        operations.put(operation3.getId(), operation3);
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

    @Override
    public Operation withdrawal(Long accountId, double amount) throws AccountException {
        Preconditions.checkNotNull(accountId, "AccountId must be not null");
        if(!accounts.containsKey(accountId)) {
            throw new AccountException("Technical error : this acccount not exist : " + accountId);
        }
        Account account = accounts.get(accountId);
        if(account.getBalance().minus(new Amount(amount)).isNegative()) {
            throw new AccountException("Functional error : you cannot withdraw this amount : " + amount);
        }
        Amount withdrawalAmount = new Amount(amount);
        account.setBalance(account.getBalance().minus(withdrawalAmount));
        Operation operation = Operation.builder()
                .account(account)
                .operationDate(LocalDateTime.now())
                .operationType(OperationTypeEnum.WITHDRAWAL)
                .amount(withdrawalAmount)
                .id(RandomGenerator.getDefault().nextLong())
                .build();
        operations.put(operation.getId(), operation);
        return operation;
    }

    @Override
    public List<Operation> operationsHistory(Long accountId) throws AccountException {
        Preconditions.checkNotNull(accountId, "AccountId must be not null");
        if(!accounts.containsKey(accountId)) {
            throw new AccountException("Technical error : this acccount not exist : " + accountId);
        }

        List<Operation> ops = null;
        ops = operations.entrySet().stream().filter(op -> op.getValue().getAccount().getId().equals(accountId))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        return ops;
    }
}
