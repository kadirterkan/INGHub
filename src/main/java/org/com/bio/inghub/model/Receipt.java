package org.com.bio.inghub.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Receipt {

    private Integer totalPaidInstallments;

    private BigDecimal totalPaid;

    private Boolean isLoanPaid;
}
