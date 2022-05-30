package com.amir.bankkata.service;

import com.amir.bankkata.exception.AccountException;
import com.amir.bankkata.model.Operation;

/**
 * Couche métier de gestion de compte
 */
public interface AccountService {
    /**
     *
     * @param accountId l'id du compte
     * @param amount le montant à déposer
     * @return l'opération créer
     * @throws AccountException
     */
    Operation deposit(Long accountId, double amount) throws AccountException;
}
