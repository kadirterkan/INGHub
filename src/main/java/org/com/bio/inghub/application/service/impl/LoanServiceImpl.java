package org.com.bio.inghub.application.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.com.bio.inghub.application.service.LoanService;
import org.com.bio.inghub.domain.model.Loan;
import org.com.bio.inghub.domain.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    @Override
    public Loan save(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }

    @Override
    public Loan findLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found with id: " + id));
    }

    @Override
    public List<Loan> findAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public List<Loan> findLoansByCustomerId(Long customerId) {
        return loanRepository.findAllByCustomer_Id(customerId);
    }
}
