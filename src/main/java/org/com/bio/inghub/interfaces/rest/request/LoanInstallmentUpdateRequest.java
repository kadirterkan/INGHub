package org.com.bio.inghub.interfaces.rest.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanInstallmentUpdateRequest extends LoanInstallmentRequest {
    @NotNull(message = "ID cannot be null")
    private Long id;
}
