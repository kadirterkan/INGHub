package org.com.bio.inghub.service.impl;

import org.com.bio.inghub.service.CustomerService;
import org.com.bio.inghub.service.LoanInstallmentService;
import org.com.bio.inghub.service.LoanService;
import org.com.bio.inghub.exception.BusinessException;
import org.com.bio.inghub.model.Customer;
import org.com.bio.inghub.model.Loan;
import org.com.bio.inghub.model.LoanInstallment;
import org.com.bio.inghub.model.Receipt;
import org.com.bio.inghub.model.enums.Installment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeskServiceImplTest {
    @Mock
    private CustomerService customerService;

    @Mock
    private LoanService loanService;

    @Mock
    private LoanInstallmentService loanInstallmentService;

    @InjectMocks
    private DeskServiceImpl deskService;

    private Customer customer;
    private static final Long CUSTOMER_ID = 1L;
    private static final BigDecimal LOAN_AMOUNT = BigDecimal.valueOf(1000);
    private static final Double INTEREST_RATE = 0.10;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .creditLimit(BigDecimal.valueOf(10000))
                .usedCreditLimit(BigDecimal.ZERO)
                .id(CUSTOMER_ID)
                .build();
    }

    @Test
    void processLoanRequest_ShouldCreateLoanWithCorrectInstallments() {
        when(customerService.findCustomerById(CUSTOMER_ID)).thenReturn(customer);
        when(loanService.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Loan loan = deskService.processLoanRequest(CUSTOMER_ID, LOAN_AMOUNT, INTEREST_RATE, Installment.TWELVE);

        assertNotNull(loan);
        assertEquals(CUSTOMER_ID, loan.getCustomer().getId());
        assertEquals(Installment.TWELVE.getValue(), loan.getNumberOfInstallment());
        assertFalse(loan.getIsPaid());

        BigDecimal totalAmount = LOAN_AMOUNT.add(LOAN_AMOUNT.multiply(BigDecimal.valueOf(INTEREST_RATE)));
        BigDecimal installmentAmount = totalAmount.divide(BigDecimal.valueOf(Installment.TWELVE.getValue()), BigDecimal.ROUND_HALF_UP);

        assertEquals(totalAmount, loan.getLoanAmount());
        assertEquals(Installment.TWELVE.getValue(), loan.getInstallments().size());

        assertEquals(installmentAmount, loan.getInstallments().get(0).getAmount());
        assertFalse(loan.getInstallments().get(0).getIsPaid());
        assertNull(loan.getInstallments().get(0).getPaymentDate());
        assertNotNull(loan.getInstallments().get(0).getDueDate());
        verify(customerService).findCustomerById(CUSTOMER_ID);
        verify(loanService).save(any());
    }

    @Test
    void processLoanRequest_WithInsufficientCreditLimit_ShouldThrowException() {
        customer.setCreditLimit(BigDecimal.valueOf(500));
        when(customerService.findCustomerById(CUSTOMER_ID)).thenReturn(customer);

        Exception exception = assertThrows(BusinessException.class, () ->
                deskService.processLoanRequest(CUSTOMER_ID, LOAN_AMOUNT, INTEREST_RATE, Installment.TWELVE)
        );

        verify(customerService).findCustomerById(CUSTOMER_ID);
        verifyNoInteractions(loanService);
        verifyNoInteractions(loanInstallmentService);
        assertThat(exception.getMessage()).isEqualTo("Insufficient credit limit");
    }

    @Test
    void processLoanRequest_ShouldCalculateLastInstallmentCorrectly() {
        BigDecimal totalAmount = LOAN_AMOUNT.add(LOAN_AMOUNT.multiply(BigDecimal.valueOf(INTEREST_RATE)));
        Loan loan = Loan.builder()
                .loanAmount(totalAmount)
                .customer(customer)
                .isPaid(false)
                .numberOfInstallment(12)
                .createDate(LocalDate.now())
                .build();
        when(customerService.findCustomerById(CUSTOMER_ID)).thenReturn(customer);
        when(loanService.save(any())).thenReturn(loan);

        Loan result = deskService.processLoanRequest(CUSTOMER_ID, LOAN_AMOUNT, INTEREST_RATE, Installment.TWELVE);

        BigDecimal sumOfInstallments = result.getInstallments().stream()
                .map(LoanInstallment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertThat(sumOfInstallments).isEqualTo(totalAmount);
        verify(customerService).findCustomerById(CUSTOMER_ID);
        verify(loanService).save(any());
        assertNotNull(result.getInstallments());
    }

    @Test
    void processLoanRequest_ShouldSetCorrectDueDates() {
        when(customerService.findCustomerById(CUSTOMER_ID)).thenReturn(customer);
        when(loanService.save(any())).thenReturn(new Loan());

        Loan result = deskService.processLoanRequest(CUSTOMER_ID, LOAN_AMOUNT, INTEREST_RATE, Installment.SIX);

        LocalDate firstDueDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);

        assertThat(result.getInstallments().get(0).getDueDate()).isEqualTo(firstDueDate);
        assertThat(result.getInstallments().get(1).getDueDate()).isEqualTo(firstDueDate.plusMonths(1));
        assertThat(result.getInstallments().get(2).getDueDate()).isEqualTo(firstDueDate.plusMonths(2));

        verify(customerService).findCustomerById(CUSTOMER_ID);
        verify(loanService).save(any());
    }

    @Test
    void payLoan_SuccessfulPayment_SingleInstallment() {
        Long loanId = 1L;
        BigDecimal paymentAmount = BigDecimal.valueOf(100);

        List<LoanInstallment> installments = new ArrayList<>();

        Loan loan = Loan.builder()
                .id(loanId)
                .isPaid(false)
                .installments(installments)
                .numberOfInstallment(1)
                .build();

        for (int i = 0; i < 6; i++) {
            LoanInstallment installment = LoanInstallment.builder()
                    .amount(BigDecimal.valueOf(100))
                    .paidAmount(BigDecimal.ZERO)
                    .isPaid(false)
                    .dueDate(LocalDate.now().plusMonths(i + 1))
                    .build();
            loan.getInstallments().add(installment);
            installments.add(installment);
        }

        when(loanService.findLoanById(loanId)).thenReturn(loan);
        when(loanInstallmentService.save(any())).thenReturn(any());

        Receipt receipt = deskService.payLoan(loanId, paymentAmount);

        assertThat(receipt.getTotalPaidInstallments()).isEqualTo(1);
        assertThat(receipt.getTotalPaid()).isEqualTo(paymentAmount);
        assertThat(receipt.getIsLoanPaid()).isTrue();
        verify(loanInstallmentService).save(any());
    }

    @Test
    void payLoan_PartialPayment_MultipleInstallments() {
        Long loanId = 1L;
        BigDecimal paymentAmount = BigDecimal.valueOf(150);

        Loan loan = Loan.builder()
                .id(loanId)
                .isPaid(false)
                .numberOfInstallment(2)
                .build();

        List<LoanInstallment> installments = List.of(
                LoanInstallment.builder()
                        .amount(BigDecimal.valueOf(100))
                        .paidAmount(BigDecimal.ZERO)
                        .isPaid(false)
                        .dueDate(LocalDate.now().plusMonths(1))
                        .build(),
                LoanInstallment.builder()
                        .amount(BigDecimal.valueOf(100))
                        .paidAmount(BigDecimal.ZERO)
                        .isPaid(false)
                        .dueDate(LocalDate.now().plusMonths(2))
                        .build()
        );

        loan.setInstallments(installments);

        when(loanService.findLoanById(loanId)).thenReturn(loan);
        when(loanInstallmentService.save(any())).thenAnswer(i -> i.getArguments()[0]);

        // When
        Receipt receipt = deskService.payLoan(loanId, paymentAmount);

        // Then
        assertThat(receipt.getTotalPaidInstallments()).isEqualTo(1);
        assertThat(receipt.getTotalPaid()).isEqualTo(BigDecimal.valueOf(100));
        assertThat(receipt.getIsLoanPaid()).isFalse();
        verify(loanInstallmentService, times(2)).save(any());
    }

    @Test
    void payLoan_AlreadyPaidLoan_ShouldThrowException() {
        Long loanId = 1L;
        BigDecimal paymentAmount = BigDecimal.valueOf(100);

        Loan loan = Loan.builder()
                .id(loanId)
                .isPaid(true)
                .build();

        when(loanService.findLoanById(loanId)).thenReturn(loan);

        assertThrows(BusinessException.class, () ->
                        deskService.payLoan(loanId, paymentAmount),
                "Loan is already paid."
        );

        verifyNoInteractions(loanInstallmentService);
    }

    @Test
    void payLoan_PaymentForAllRemainingInstallments() {
        Long loanId = 1L;
        BigDecimal paymentAmount = BigDecimal.valueOf(300);

        Loan loan = Loan.builder()
                .id(loanId)
                .numberOfInstallment(3)
                .isPaid(false)
                .build();

        List<LoanInstallment> installments = List.of(
                LoanInstallment.builder()
                        .amount(BigDecimal.valueOf(100))
                        .paidAmount(BigDecimal.ZERO)
                        .isPaid(false)
                        .dueDate(LocalDate.now())
                        .build(),
                LoanInstallment.builder()
                        .amount(BigDecimal.valueOf(100))
                        .paidAmount(BigDecimal.ZERO)
                        .isPaid(false)
                        .dueDate(LocalDate.now().plusMonths(1))
                        .build(),
                LoanInstallment.builder()
                        .amount(BigDecimal.valueOf(100))
                        .paidAmount(BigDecimal.ZERO)
                        .isPaid(false)
                        .dueDate(LocalDate.now().plusMonths(2))
                        .build()
        );

        loan.setInstallments(installments);

        when(loanService.findLoanById(loanId)).thenReturn(loan);
        when(loanInstallmentService.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Receipt receipt = deskService.payLoan(loanId, paymentAmount);

        assertThat(receipt.getTotalPaidInstallments()).isEqualTo(3);
        assertThat(receipt.getTotalPaid()).isEqualTo(BigDecimal.valueOf(300));
        assertThat(receipt.getIsLoanPaid()).isTrue();
        verify(loanInstallmentService, times(3)).save(any());
    }
}
