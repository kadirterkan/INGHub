package org.com.bio.inghub.application.service;

import org.com.bio.inghub.domain.model.LoanInstallment;

import java.util.List;

public interface LoanInstallmentService {
    LoanInstallment save(LoanInstallment loanInstallment);

    List<LoanInstallment> saveAll(List<LoanInstallment> loanInstallments);

    void deleteLoanInstallment(Long id);

    LoanInstallment findLoanInstallmentById(Long id);

    List<LoanInstallment> getAllByLoanId(Long loanId);
}
