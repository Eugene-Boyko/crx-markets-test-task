package lu.crx.financing.domain.entity;

import java.time.LocalDate;
import lombok.Builder;

/**
 * A FinancingResults is an entity that that represents the results of the financing. It contains applied financing
 * term, financing rate, financing date, early payment amount, purchaser financing settings;
 */
@Builder(toBuilder = true)
public record FinancingResults(long id, FinancingTerm financingTerm, FinancingRate financingRate,
                               LocalDate financingDate, EarlyPaymentAmount earlyPaymentAmount, Invoice invoice,
                               PurchaserFinancingSettings purchaserFinancingSettings) {

}
