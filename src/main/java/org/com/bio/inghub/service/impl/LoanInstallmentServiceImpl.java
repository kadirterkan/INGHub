package org.com.bio.inghub.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.com.bio.inghub.service.LoanInstallmentService;
import org.com.bio.inghub.exception.BusinessException;
import org.com.bio.inghub.model.LoanInstallment;
import org.com.bio.inghub.repository.LoanInstallmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class LoanInstallmentServiceImpl implements LoanInstallmentService {

    private final LoanInstallmentRepository loanInstallmentRepository;

    @Override
    public LoanInstallment save(LoanInstallment loanInstallment) {
        return loanInstallmentRepository.save(loanInstallment);
    }

    @Override
    public List<LoanInstallment> saveAll(List<LoanInstallment> loanInstallments) {
        return loanInstallmentRepository.saveAll(loanInstallments);
    }

    @Override
    public void deleteLoanInstallment(Long id) {
        loanInstallmentRepository.deleteById(id);
    }

    @Override
    public LoanInstallment findLoanInstallmentById(Long id) {
        return loanInstallmentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Loan Installment not found with id: " + id));
    }

    @Override
    public List<LoanInstallment> getAllByLoanId(Long loanId) {
        return loanInstallmentRepository.findAllByLoan_Id(loanId);
    }
}
