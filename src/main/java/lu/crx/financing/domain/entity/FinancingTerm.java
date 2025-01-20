package lu.crx.financing.domain.entity;

/**
 * Financing Term - the duration in days between the financing date and the Invoice's maturity date.
 * The financing is essentially a loan given by the Purchaser to the Creditor for the duration of the term,
 * with a certain financing rate and responsibility of the Debtor to pay back the loan.
 */
public record FinancingTerm(int termInDays) {

}
