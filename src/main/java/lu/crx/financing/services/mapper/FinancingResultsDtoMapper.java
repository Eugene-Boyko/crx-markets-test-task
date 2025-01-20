package lu.crx.financing.services.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import lu.crx.financing.domain.entity.EarlyPaymentAmount;
import lu.crx.financing.domain.entity.FinancingRate;
import lu.crx.financing.domain.entity.FinancingTerm;
import lu.crx.financing.domain.entity.FinancingResults;
import lu.crx.financing.dto.FinancingResultsDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface FinancingResultsDtoMapper {

    FinancingResultsDto toDto(FinancingResults invoices);

    default int fromFinancingTerm(FinancingTerm financingTerm) {
        return financingTerm.termInDays();
    }

    default int fromFinancingRate(FinancingRate financingRate) {
        return financingRate.rateInBps();
    }

    default long fromEarlyPaymentAmount(EarlyPaymentAmount earlyPaymentAmount) {
        return earlyPaymentAmount.amountInCents();
    }
}
