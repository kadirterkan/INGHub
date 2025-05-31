package org.com.bio.inghub.application.service.impl;

import lombok.AllArgsConstructor;
import org.com.bio.inghub.application.service.CustomerService;
import org.com.bio.inghub.application.service.DeskService;
import org.com.bio.inghub.application.service.LoanInstallmentService;
import org.com.bio.inghub.application.service.LoanService;
import org.com.bio.inghub.domain.exception.BusinessException;
import org.com.bio.inghub.domain.model.Customer;
import org.com.bio.inghub.domain.model.Loan;
import org.com.bio.inghub.domain.model.LoanInstallment;
import org.com.bio.inghub.domain.model.Receipt;
import org.com.bio.inghub.domain.model.enums.Installment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DeskServiceImpl implements DeskService {

    private final CustomerService customerService;

    private final LoanService loanService;

    private final LoanInstallmentService loanInstallmentService;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Loan processLoanRequest(Long customerId, BigDecimal amount, Double interestRate, Installment installment) {
        Customer customer = customerService.findCustomerById(customerId);

        // Check if the customer is eligible for the loan
        customer.useCreditLimit(amount);

        // Total amount for loan should be amount * (1 + interest rate)
        BigDecimal totalAmount = amount.add(amount.multiply(BigDecimal.valueOf(interestRate)));

        // Divide the total amount by the number of installments
        BigDecimal installmentAmount = totalAmount.divide(BigDecimal.valueOf(installment.getValue()), BigDecimal.ROUND_HALF_UP);

        Loan loan = Loan.builder()
                .customer(customer)
                .loanAmount(totalAmount)
                .numberOfInstallment(installment.getValue())
                .createDate(java.time.LocalDate.now())
                .installments(new ArrayList<>())
                .isPaid(false)
                .build();

        // Create installments for the loan
        BigDecimal totalInstallments = BigDecimal.ZERO;
        for (int i = 0; i < installment.getValue() - 1; i++) {
            LoanInstallment installmentEntity = LoanInstallment.builder()
                    .loan(loan)
                    .amount(installmentAmount)
                    .paidAmount(BigDecimal.ZERO)
                    .dueDate(java.time.LocalDate.now().plusMonths(i + 1).withDayOfMonth(1))
                    .isPaid(false)
                    .build();
            loan.getInstallments().add(installmentEntity);
            totalInstallments = totalInstallments.add(installmentAmount);
        }

        BigDecimal lastInstallmentAmount = totalAmount.subtract(totalInstallments);
        LoanInstallment lastInstallment = LoanInstallment.builder()
                .loan(loan)
                .amount(lastInstallmentAmount)
                .paidAmount(BigDecimal.ZERO)
                .dueDate(java.time.LocalDate.now().plusMonths(installment.getValue()).withDayOfMonth(1))
                .isPaid(false)
                .build();
        loan.getInstallments().add(lastInstallment);

        loanService.save(loan);

        loanInstallmentService.saveAll(loan.getInstallments());

        return loan;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Receipt payLoan(Long loanId, BigDecimal paymentAmount) {
        Loan loan = loanService.findLoanById(loanId);

        if (loan.getIsPaid()) {
            throw new BusinessException("Loan is already paid.");
        }

        List<LoanInstallment> installments = loan.getInstallments();
        installments = installments.stream().filter(
                (LoanInstallment installment) -> !installment.getIsPaid() && installment.getDueDate().isBefore(java.time.LocalDate.now().plusMonths(3))
        ).sorted(Comparator.comparing(LoanInstallment::getDueDate))
                .toList();

        int totalPaidInstallments = 0;
        BigDecimal totalPaidAmount = BigDecimal.ZERO;

        for (LoanInstallment installment : installments) {
            if (!installment.getIsPaid()) {
                if (paymentAmount.compareTo(installment.getAmount()) >= 0) {
                    installment.setPaidAmount(installment.getAmount());
                    installment.setPaymentDate(LocalDate.now());
                    installment.setIsPaid(true);
                    totalPaidAmount = totalPaidAmount.add(installment.getAmount());
                    paymentAmount = paymentAmount.subtract(installment.getAmount());
                    totalPaidInstallments++;
                } else {
                    paymentAmount = BigDecimal.ZERO;
                }

                loanInstallmentService.save(installment);

                if (paymentAmount.compareTo(BigDecimal.ZERO) == 0) {
                    break;
                }
            }
        }

        Boolean isLoanPaid = totalPaidInstallments == loan.getNumberOfInstallment();
        if (isLoanPaid) {
            loan.setIsPaid(true);
        }

        return Receipt.builder()
                .totalPaidInstallments(totalPaidInstallments)
                .totalPaid(totalPaidAmount)
                .isLoanPaid(isLoanPaid)
                .build();
    }
}
