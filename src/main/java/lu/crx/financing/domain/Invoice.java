package lu.crx.financing.domain;

import java.time.LocalDate;

/**
 * An invoice issued by the {@link Creditor} to the {@link Debtor} for shipped goods.
 */
public record Invoice(long id, long creditorId, Long debtorId, LocalDate maturityDate, long valueInCents,
                      boolean processed) {

}
