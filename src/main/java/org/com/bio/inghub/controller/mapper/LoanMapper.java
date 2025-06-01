package org.com.bio.inghub.controller.mapper;

import org.com.bio.inghub.model.Loan;
import org.com.bio.inghub.controller.request.LoanUpdateRequest;
import org.com.bio.inghub.controller.response.LoanResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LoanMapper {

    LoanMapper INSTANCE = Mappers.getMapper(LoanMapper.class);

    LoanResponse toLoanResponse(Loan loan);

    List<LoanResponse> toLoanResponseList(List<Loan> loans);

    Loan toLoan(LoanUpdateRequest loanUpdateRequest);
}
