package lu.crx.financing.dto;

/**
 * Financing settings set by the purchaser for a specific creditor.
 */
public record PurchaserFinancingSettingsDto(long id, CreditorDto creditor, int annualRateInBps, PurchaserDto purchaser,
                                            int minimumFinancingTermInDays) {

}
