package lu.crx.financing.dto;

/**
 * A creditor is a company that shipped some goods to the {@link DebtorDto}, issued an {@link InvoiceDto} for the
 * shipment and is waiting for this invoice to be paid by the debtor.
 */
public record CreditorDto(long id, String name, int maxFinancingRateInBps) {

}
