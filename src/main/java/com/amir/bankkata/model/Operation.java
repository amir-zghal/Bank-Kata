package com.amir.bankkata.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Model représentant une opération
 */
@Builder
@Data
public class Operation {
    private Long id;
    private LocalDateTime operationDate;
    private Account account;
    private OperationTypeEnum operationType;
    private Amount amount;
}
