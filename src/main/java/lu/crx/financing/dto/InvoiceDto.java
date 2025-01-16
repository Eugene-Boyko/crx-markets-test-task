package lu.crx.financing.dto;

import java.time.LocalDate;

public record InvoiceDto(long id, CreditorDto creditor, DebtorDto debtor, LocalDate maturityDate, long valueInCents,
                         boolean processed) {

}
