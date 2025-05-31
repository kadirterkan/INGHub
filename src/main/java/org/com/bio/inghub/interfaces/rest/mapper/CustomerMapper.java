package org.com.bio.inghub.interfaces.rest.mapper;

import org.com.bio.inghub.domain.model.Customer;
import org.com.bio.inghub.interfaces.rest.request.CustomerRequest;
import org.com.bio.inghub.interfaces.rest.request.CustomerUpdateRequest;
import org.com.bio.inghub.interfaces.rest.response.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerResponse toCustomerResponse(Customer customer);

    Customer toCustomer(CustomerRequest customerRequest);

    Customer toCustomer(CustomerUpdateRequest customerRequest);
}
