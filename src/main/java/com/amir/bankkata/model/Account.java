package com.amir.bankkata.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Model représentant le compte
 */
@Builder
@Data
public class Account {
    private Long id;
    private String iban;
    private Customer customer;
    private Amount balance;
}
