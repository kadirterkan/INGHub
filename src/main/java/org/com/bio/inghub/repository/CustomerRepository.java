package org.com.bio.inghub.repository;

import org.com.bio.inghub.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
