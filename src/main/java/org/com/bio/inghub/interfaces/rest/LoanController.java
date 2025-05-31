package org.com.bio.inghub.interfaces.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.com.bio.inghub.application.service.DeskService;
import org.com.bio.inghub.application.service.LoanService;
import org.com.bio.inghub.interfaces.rest.request.LoanRequest;
import org.com.bio.inghub.interfaces.rest.request.LoanUpdateRequest;
import org.com.bio.inghub.interfaces.rest.response.LoanResponse;
import org.com.bio.inghub.interfaces.rest.mapper.LoanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
@AllArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class LoanController {

    private final DeskService deskService;

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanResponse> processLoanRequest(@RequestBody @Valid LoanRequest loanRequest) {
        return ResponseEntity.ok(LoanMapper.INSTANCE.toLoanResponse(deskService.processLoanRequest(loanRequest.getCustomerId(),
                loanRequest.getAmount(),
                loanRequest.getInterestRate(),
                loanRequest.getInstallment())));
    }

    @GetMapping("/by-customer-id")
    public ResponseEntity<List<LoanResponse>> getAllLoansByCustomerId(@RequestParam Long customerId) {
        return ResponseEntity.ok(LoanMapper.INSTANCE.toLoanResponseList(loanService.findLoansByCustomerId(customerId)));
    }

    @GetMapping
    public ResponseEntity<LoanResponse> getById(@RequestParam Long id) {
        return ResponseEntity.ok(LoanMapper.INSTANCE.toLoanResponse(loanService.findLoanById(id)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<LoanResponse>> getAll() {
        return ResponseEntity.ok(LoanMapper.INSTANCE.toLoanResponseList(loanService.findAllLoans()));
    }

    @DeleteMapping
    public void deleteById(@RequestParam Long id) {
        loanService.deleteLoan(id);
    }

    @PutMapping
    public ResponseEntity<LoanResponse> updateLoan(@RequestBody @Valid LoanUpdateRequest loanUpdateRequest) {
        return ResponseEntity.ok(LoanMapper.INSTANCE.toLoanResponse(loanService.save(LoanMapper.INSTANCE.toLoan(loanUpdateRequest))));
    }
}
