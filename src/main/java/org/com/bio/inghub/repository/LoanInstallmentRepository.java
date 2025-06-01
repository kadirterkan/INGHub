package org.com.bio.inghub.repository;

import org.com.bio.inghub.model.LoanInstallment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanInstallmentRepository extends JpaRepository<LoanInstallment, Long> {

    List<LoanInstallment> findAllByLoan_Id(Long loanId);
}
