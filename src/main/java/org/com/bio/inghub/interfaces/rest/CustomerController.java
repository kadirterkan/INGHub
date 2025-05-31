package org.com.bio.inghub.interfaces.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.com.bio.inghub.application.service.CustomerService;
import org.com.bio.inghub.interfaces.rest.mapper.CustomerMapper;
import org.com.bio.inghub.interfaces.rest.request.CustomerRequest;
import org.com.bio.inghub.interfaces.rest.request.CustomerUpdateRequest;
import org.com.bio.inghub.interfaces.rest.response.CustomerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<CustomerResponse> getCustomer(@RequestParam Long id) {
        return ResponseEntity.ok(CustomerMapper.INSTANCE.toCustomerResponse(customerService.findCustomerById(id)));
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.ok(CustomerMapper.INSTANCE.toCustomerResponse(customerService.save(CustomerMapper.INSTANCE.toCustomer(customerRequest))));
    }

    @PutMapping
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody @Valid CustomerUpdateRequest customerRequest) {
        return ResponseEntity.ok(CustomerMapper.INSTANCE.toCustomerResponse(customerService.save(CustomerMapper.INSTANCE.toCustomer(customerRequest))));
    }

    @DeleteMapping
    public void deleteCustomer(@RequestParam Long id) {
        customerService.deleteCustomer(id);
    }
}
