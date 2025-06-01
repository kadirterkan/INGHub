package org.com.bio.inghub.service;

import org.com.bio.inghub.model.Loan;

import java.util.List;

public interface LoanService {
     Loan save(Loan loan);

     void deleteLoan(Long id);

     Loan findLoanById(Long id);

     List<Loan> findAllLoans();

     List<Loan> findLoansByCustomerId(Long customerId);
}
