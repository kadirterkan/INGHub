package org.com.bio.inghub.service;

import org.com.bio.inghub.model.Loan;
import org.com.bio.inghub.model.Receipt;
import org.com.bio.inghub.model.enums.Installment;

import java.math.BigDecimal;

public interface DeskService {

    Loan processLoanRequest(Long customerId, BigDecimal amount, Double interestRate, Installment installment);

    Receipt payLoan(Long loanId, BigDecimal paymentAmount);
}
