package org.com.bio.inghub.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.com.bio.inghub.domain.common.BaseEntity;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "loan_installment")
public class LoanInstallment extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    private Double amount;

    private Double paidAmount;

    private LocalDate dueDate;

    private LocalDate paymentDate;

    private Boolean isPaid;
}
