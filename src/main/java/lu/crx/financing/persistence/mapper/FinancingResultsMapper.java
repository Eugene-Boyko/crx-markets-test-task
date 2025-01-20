package lu.crx.financing.persistence.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import lu.crx.financing.domain.entity.EarlyPaymentAmount;
import lu.crx.financing.domain.entity.FinancingRate;
import lu.crx.financing.domain.entity.FinancingTerm;
import lu.crx.financing.domain.entity.FinancingResults;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface FinancingResultsMapper {

    lu.crx.financing.persistence.entity.FinancingResults fromDomain(FinancingResults financingResults);

    FinancingResults toDomain(lu.crx.financing.persistence.entity.FinancingResults financingResults);

    default int fromFinancingTerm(FinancingTerm financingTerm) {
        return financingTerm.termInDays();
    }

    default int fromFinancingRate(FinancingRate financingRate) {
        return financingRate.rateInBps();
    }

    default long fromEarlyPaymentAmount(EarlyPaymentAmount earlyPaymentAmount) {
        return earlyPaymentAmount.amountInCents();
    }

    default FinancingTerm toFinancingTerm(int financingTerm) {
        return new FinancingTerm(financingTerm);
    }

    default FinancingRate toFinancingRate(int financingRate) {
        return new FinancingRate(financingRate);
    }

    default EarlyPaymentAmount toEarlyPaymentAmount(long earlyPaymentAmount) {
        return new EarlyPaymentAmount(earlyPaymentAmount);
    }
}
