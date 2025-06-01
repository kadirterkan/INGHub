package org.com.bio.inghub.controller.mapper;

import org.com.bio.inghub.model.LoanInstallment;
import org.com.bio.inghub.controller.request.LoanInstallmentRequest;
import org.com.bio.inghub.controller.request.LoanInstallmentUpdateRequest;
import org.com.bio.inghub.controller.response.LoanInstallmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LoanInstallmentMapper {

    LoanInstallmentMapper INSTANCE = Mappers.getMapper(LoanInstallmentMapper.class);

    LoanInstallmentResponse toLoanInstallmentResponse(LoanInstallment loanInstallment);

    List<LoanInstallmentResponse> toLoanInstallmentResponseList(List<LoanInstallment> loanInstallments);

    LoanInstallment toLoanInstallment(LoanInstallmentUpdateRequest loanInstallmentUpdateRequest);

    LoanInstallment toLoanInstallment(LoanInstallmentRequest loanInstallmentUpdateRequest);
}
