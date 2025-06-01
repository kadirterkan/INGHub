package org.com.bio.inghub.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.com.bio.inghub.service.LoanInstallmentService;
import org.com.bio.inghub.controller.mapper.LoanInstallmentMapper;
import org.com.bio.inghub.controller.request.LoanInstallmentRequest;
import org.com.bio.inghub.controller.request.LoanInstallmentUpdateRequest;
import org.com.bio.inghub.controller.response.LoanInstallmentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan-installments")
@AllArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class LoanInstallmentController {

    private final LoanInstallmentService loanInstallmentService;

    @GetMapping("/by-loan-id")
    public ResponseEntity<List<LoanInstallmentResponse>> getLoanInstallmentsByLoanId(@RequestParam Long loanId) {
        return ResponseEntity.ok(LoanInstallmentMapper.INSTANCE.toLoanInstallmentResponseList(loanInstallmentService.getAllByLoanId(loanId)));
    }

    @GetMapping
    public ResponseEntity<LoanInstallmentResponse> getLoanInstallmentById(@RequestParam Long id) {
        return ResponseEntity.ok(LoanInstallmentMapper.INSTANCE.toLoanInstallmentResponse(loanInstallmentService.findLoanInstallmentById(id)));
    }

    @PostMapping
    public ResponseEntity<LoanInstallmentResponse> createLoanInstallment(@RequestBody LoanInstallmentRequest loanInstallmentUpdateRequest) {
        return ResponseEntity.ok(LoanInstallmentMapper.INSTANCE.toLoanInstallmentResponse(loanInstallmentService.save(LoanInstallmentMapper.INSTANCE.toLoanInstallment(loanInstallmentUpdateRequest))));
    }

    @PutMapping
    public ResponseEntity<LoanInstallmentResponse> updateLoanInstallment(@RequestBody LoanInstallmentUpdateRequest loanInstallmentUpdateRequest) {
        return ResponseEntity.ok(LoanInstallmentMapper.INSTANCE.toLoanInstallmentResponse(loanInstallmentService.save(LoanInstallmentMapper.INSTANCE.toLoanInstallment(loanInstallmentUpdateRequest))));
    }

    @DeleteMapping
    public void deleteLoanInstallmentById(@RequestParam Long id) {
        loanInstallmentService.deleteLoanInstallment(id);
    }
}
