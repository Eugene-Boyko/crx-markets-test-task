package lu.crx.financing.domain;

/**
 * A creditor is a company that shipped some goods to the {@link Debtor}, issued an {@link Invoice} for the shipment and
 * is waiting for this invoice to be paid by the debtor.
 */
public record Creditor(long id, String name, int maxFinancingRateInBps) {

}
