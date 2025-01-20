package lu.crx.financing.domain.entity;

/**
 * Early payment amount - the amount of money paid by the Purchaser to the Creditor for the particular financed invoice
 * on financing date. This amount is less than the value of the invoice.
 */
public record EarlyPaymentAmount(long amountInCents) {

}
