package com.amir.bankkata.resource;

import com.amir.bankkata.dto.OperationActionDto;
import com.amir.bankkata.exception.AccountException;
import com.amir.bankkata.model.Operation;
import com.amir.bankkata.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountResource {

    @Autowired
    private AccountService accountService;

    @PostMapping("/deposit")
    public ResponseEntity<Operation> deposit(@RequestBody OperationActionDto operationAction) throws AccountException {
        // TODO ajouter PreAuthorize pour controler l'accèes à ce service: controle de l'user connecté avec le customerId du compte
        return new ResponseEntity<Operation>(accountService.deposit(operationAction.getAccountId(), operationAction.getAmount()), HttpStatus.OK);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<Operation> withdrawal(@RequestBody OperationActionDto operationAction) throws AccountException {
        // TODO ajouter PreAuthorize pour controler l'accèes à ce service: controle de l'user connecté avec le customerId du compte
        return new ResponseEntity<Operation>(accountService.withdrawal(operationAction.getAccountId(), operationAction.getAmount()), HttpStatus.OK);
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<List<Operation>> history(@PathVariable Long id) throws AccountException {
        // TODO ajouter PreAuthorize pour controler l'accèes à ce service: controle de l'user connecté avec le customerId du compte
        return new ResponseEntity<List<Operation>>(accountService.operationsHistory(id), HttpStatus.OK);
    }
}
