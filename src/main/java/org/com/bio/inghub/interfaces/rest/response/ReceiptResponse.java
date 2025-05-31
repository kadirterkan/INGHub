package org.com.bio.inghub.interfaces.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReceiptResponse {

    private Integer totalPaidInstallments;

    private BigDecimal totalPaid;

    private Boolean isLoanPaid;
}
