package lu.crx.financing.domain;

/**
 * Financing settings set by the purchaser for a specific creditor.
 */
public record PurchaserFinancingSettings(long id, Creditor creditor, int annualRateInBps, Purchaser purchaser) {

}
