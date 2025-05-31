package org.com.bio.inghub.interfaces.rest.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanUpdateRequest {

    @NotNull(message = "Loan amount cannot be null")
    @Min(value = 0, message = "Loan amount must be greater than or equal to 0")
    @Max(value = Long.MAX_VALUE, message = "Loan amount exceeds maximum limit")
    private BigDecimal loanAmount;

    @NotNull(message = "Number of installment cannot be null")
    private Integer numberOfInstallment;

    @NotNull(message = "Create date cannot be null")
    private LocalDate createDate;

    @NotNull(message = "Is paid cannot be null")
    private Boolean isPaid;
}
