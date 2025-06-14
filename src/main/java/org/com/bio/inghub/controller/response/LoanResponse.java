package org.com.bio.inghub.controller.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponse {
    private Long id;

    private BigDecimal loanAmount;

    private Integer numberOfInstallment;

    private LocalDate createDate;

    private Boolean isPaid;
}
