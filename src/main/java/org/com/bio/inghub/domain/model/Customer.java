package org.com.bio.inghub.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.com.bio.inghub.domain.exception.BusinessException;
import org.com.bio.inghub.domain.model.common.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    private String name;

    private String surname;

    private BigDecimal creditLimit;

    private BigDecimal usedCreditLimit;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Loan> loans;

    public void useCreditLimit(BigDecimal amount) {
        if (creditLimit.compareTo(amount) > 0) {
            usedCreditLimit = usedCreditLimit.add(amount);
            creditLimit = creditLimit.subtract(amount);
        } else {
            throw new BusinessException("Insufficient credit limit");
        }
    }
}
