package lu.crx.financing.domain.entity;

/**
 * This is a mathematical transformation of the expression
 * <p> financingRate <= Creditor.maxFinancingRateInBps
 * <p> The Financing Rate is equal annualRate * financingTerm / 360 so the full linear inequality:
 * <p> annualRate * financingTerm / 360 <= Creditor.maxFinancingRateInBps
 * <p> but it requires retrieving of the purchaser from the database, calculation of the financing rate and then
 * comparison with the Creditor.maxFinancingRateInBps
 * <p> It can be avoided based on the rules for transferring terms of a linear inequality.
 * <p> And the following expression helps to compare values as query parameters:
 * <p> annualRate <= Creditor.maxFinancingRateInBps * 360 / financingTerm
 * <p> In general, instead of calculating the rate for the financingTerm period from the annual rate, here is an
 * intermediate rate that is calculated based on maxFinancingRateInBps for a certain period of financingTerm time
 */
public record MaxAnnualFinancingRateForFinancingTerm(int rateInBps) {

}
