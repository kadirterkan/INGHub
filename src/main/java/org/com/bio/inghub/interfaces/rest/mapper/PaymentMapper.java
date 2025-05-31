package org.com.bio.inghub.interfaces.rest.mapper;

import org.com.bio.inghub.domain.model.Receipt;
import org.com.bio.inghub.interfaces.rest.response.ReceiptResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    ReceiptResponse toReceiptResponse(Receipt receipt);
}
