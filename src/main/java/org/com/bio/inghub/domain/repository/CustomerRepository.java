package org.com.bio.inghub.domain.repository;

import org.com.bio.inghub.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
