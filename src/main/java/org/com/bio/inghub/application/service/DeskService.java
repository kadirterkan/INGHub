package org.com.bio.inghub.application.service;

import org.com.bio.inghub.domain.model.Loan;
import org.com.bio.inghub.domain.model.Receipt;
import org.com.bio.inghub.domain.model.enums.Installment;

import java.math.BigDecimal;

public interface DeskService {

    Loan processLoanRequest(Long customerId, BigDecimal amount, Double interestRate, Installment installment);

    Receipt payLoan(Long loanId, BigDecimal paymentAmount);
}
