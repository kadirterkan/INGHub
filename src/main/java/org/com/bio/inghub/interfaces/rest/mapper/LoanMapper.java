package org.com.bio.inghub.interfaces.rest.mapper;

import jakarta.validation.Valid;
import org.com.bio.inghub.domain.model.Loan;
import org.com.bio.inghub.interfaces.rest.request.LoanUpdateRequest;
import org.com.bio.inghub.interfaces.rest.response.LoanResponse;
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
