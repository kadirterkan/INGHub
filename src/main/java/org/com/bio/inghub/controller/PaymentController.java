package org.com.bio.inghub.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.com.bio.inghub.service.DeskService;
import org.com.bio.inghub.controller.request.PaymentRequest;
import org.com.bio.inghub.controller.response.ReceiptResponse;
import org.com.bio.inghub.controller.mapper.PaymentMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class PaymentController {

    private final DeskService deskService;

    @PostMapping
    public ResponseEntity<ReceiptResponse> processPayment(@RequestBody @Valid PaymentRequest paymentRequest) {
        return ResponseEntity.ok(PaymentMapper.INSTANCE.toReceiptResponse(deskService.payLoan(paymentRequest.getLoanId(), paymentRequest.getPaymentAmount())));
    }
}
