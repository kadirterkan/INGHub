package org.com.bio.inghub.interfaces.rest.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.com.bio.inghub.domain.model.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanInstallmentResponse {
    private BigDecimal amount;

    private BigDecimal paidAmount;

    private LocalDate dueDate;

    private LocalDate paymentDate;

    private Boolean isPaid;
}
