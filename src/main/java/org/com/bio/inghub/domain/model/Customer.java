package org.com.bio.inghub.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.com.bio.inghub.domain.common.BaseEntity;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    private String name;
    private String surname;
    private Double creditLimit;
    private Double usedCreditLimit;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Loan> loans;
}
