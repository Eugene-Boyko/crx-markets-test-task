package lu.crx.financing.domain.entity;

/**
 * Financing Rate - the actual financing rate for a particular Invoice, proportional to its financing term. Calculated
 * as financingRate = annualRate * financingTerm / 360. In Bps
 */
public record FinancingRate(int rateInBps) {

}
