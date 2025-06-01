package org.com.bio.inghub.controller.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Surname cannot be blank")
    private String surname;

    @Min(value = 0, message = "Credit limit must be a positive value")
    @Max(value = Long.MAX_VALUE, message = "Credit limit must not exceed maximum value")
    private BigDecimal creditLimit;

    @Min(value = 0, message = "Used credit limit must be a positive value")
    @Max(value = Long.MAX_VALUE, message = "Used credit limit must not exceed maximum value")
    private BigDecimal usedCreditLimit;
}
