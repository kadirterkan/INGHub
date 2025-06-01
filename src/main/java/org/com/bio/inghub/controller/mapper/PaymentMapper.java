package org.com.bio.inghub.controller.mapper;

import org.com.bio.inghub.model.Receipt;
import org.com.bio.inghub.controller.response.ReceiptResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    ReceiptResponse toReceiptResponse(Receipt receipt);
}
