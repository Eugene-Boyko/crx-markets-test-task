package lu.crx.financing.dto;

/**
 * A debtor is an entity that purchased some goods from the {@link CreditorDto}, received an {@link InvoiceDto} and is
 * obliged to pay for the invoice at the specified date called maturity date (see {@link InvoiceDto#maturityDate()}).
 */
public record DebtorDto(long id, String name) {

}
