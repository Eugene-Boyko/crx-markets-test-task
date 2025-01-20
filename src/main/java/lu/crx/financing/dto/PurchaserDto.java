package lu.crx.financing.dto;

/**
 * Purchaser is an entity (usually a bank) that wants to purchase the invoices. I.e. it issues a loan to the creditor
 * for the term and the value of the invoice, according to the rate set up by this purchaser.
 */
public record PurchaserDto(long id, String name) {

}
