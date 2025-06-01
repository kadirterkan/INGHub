package org.com.bio.inghub.controller.mapper;

import org.com.bio.inghub.model.Customer;
import org.com.bio.inghub.controller.request.CustomerRequest;
import org.com.bio.inghub.controller.request.CustomerUpdateRequest;
import org.com.bio.inghub.controller.response.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerResponse toCustomerResponse(Customer customer);

    Customer toCustomer(CustomerRequest customerRequest);

    Customer toCustomer(CustomerUpdateRequest customerRequest);
}
