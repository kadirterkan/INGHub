package org.com.bio.inghub.controller.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.com.bio.inghub.model.enums.Installment;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {

    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;

    @NotNull
    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    @Max(value = Long.MAX_VALUE, message = "Amount exceeds maximum limit")
    private BigDecimal amount;

    @NotNull
    @DecimalMin(value = "0.1", message = "Interest rate must be greater than or equal to 0.1")
    @DecimalMax(value = "0.5", message = "Interest rate must be less than or equal to 0.5")
    @Max(value = Long.MAX_VALUE, message = "Interest rate exceeds maximum limit")
    private Double interestRate;

    @NotNull(message = "Installment cannot be null")
    private Installment installment;
}
