package lu.crx.financing.domain;

/**
 * A debtor is an entity that purchased some goods from the {@link Creditor}, received an {@link Invoice} and is obliged
 * to pay for the invoice at the specified date called maturity date (see {@link Invoice#maturityDate()}).
 */
public record Debtor(long id, String name) {

}
