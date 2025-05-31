package org.com.bio.inghub.interfaces.rest.mapper;

import org.com.bio.inghub.domain.model.LoanInstallment;
import org.com.bio.inghub.interfaces.rest.request.LoanInstallmentRequest;
import org.com.bio.inghub.interfaces.rest.request.LoanInstallmentUpdateRequest;
import org.com.bio.inghub.interfaces.rest.response.LoanInstallmentResponse;
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
