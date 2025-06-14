package org.com.bio.inghub.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CustomerResponse {
    private String name;

    private String surname;

    private BigDecimal creditLimit;

    private BigDecimal usedCreditLimit;
}
