package org.com.bio.inghub.interfaces.rest.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanInstallmentRequest {
    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;

    @NotNull(message = "Paid amount cannot be null")
    @Min(value = 0, message = "Paid amount must be a positive value")
    @Max(value = Long.MAX_VALUE, message = "Paid amount must not exceed maximum value")
    private BigDecimal paidAmount;

    @NotNull(message = "Due date cannot be null")
    private LocalDate dueDate;

    @NotNull(message = "Payment date cannot be null")
    private LocalDate paymentDate;

    @NotNull(message = "Paid status cannot be null")
    private Boolean isPaid;
}
