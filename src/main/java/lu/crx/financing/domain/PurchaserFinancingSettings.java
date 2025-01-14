package lu.crx.financing.domain;

/**
 * Financing settings set by the purchaser for a specific creditor.
 */
public record PurchaserFinancingSettings(long id, long creditorId, int annualRateInBps, long purchaserId) {

}
