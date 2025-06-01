package org.com.bio.inghub.repository;

import org.com.bio.inghub.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findAllByCustomer_Id(Long customerId);
}
