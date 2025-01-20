package lu.crx.financing.dto;

import java.time.LocalDate;

/**
 * An invoice issued by the {@link CreditorDto} to the {@link DebtorDto} for shipped goods.
 */
public record InvoiceDto(long id, CreditorDto creditor, DebtorDto debtor, LocalDate maturityDate, long valueInCents,
                         boolean processed) {

}
