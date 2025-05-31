package org.com.bio.inghub.application.service;

import org.com.bio.inghub.domain.model.Loan;

import java.util.List;

public interface LoanService {
     Loan save(Loan loan);

     void deleteLoan(Long id);

     Loan findLoanById(Long id);

     List<Loan> findAllLoans();

     List<Loan> findLoansByCustomerId(Long customerId);
}
