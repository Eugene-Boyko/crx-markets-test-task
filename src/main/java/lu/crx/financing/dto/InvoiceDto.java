package lu.crx.financing.dto;

import java.time.LocalDate;

public record InvoiceDto(long id, long creditorId, Long debtorId, LocalDate maturityDate, long valueInCents,
                         boolean processed) {

}
