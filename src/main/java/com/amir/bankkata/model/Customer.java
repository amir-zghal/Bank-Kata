package com.amir.bankkata.model;

import lombok.Builder;
import lombok.Data;

/**
 * Model représentant le client
 */
@Builder
@Data
public class Customer {
    private Long id;
    private String name;
    private String phoneNumber;
}
