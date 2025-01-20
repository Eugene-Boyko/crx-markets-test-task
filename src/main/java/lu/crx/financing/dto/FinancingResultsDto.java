package lu.crx.financing.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A FinancingResults is an entity that that represents the results of the financing. It contains applied financing
 * term, financing rate, financing date, early payment amount, purchaser financing settings;
 */
public record FinancingResultsDto(long id, int financingTerm, int financingRate, LocalDate financingDate,
                                  long earlyPaymentAmount, InvoiceDto invoice,
                                  PurchaserFinancingSettingsDto purchaserFinancingSettings) implements Serializable {

}
