package lu.crx.financing.domain.entity;

import java.time.LocalDate;
import lombok.Builder;

/**
 * An invoice issued by the {@link Creditor} to the {@link Debtor} for shipped goods.
 */
@Builder(toBuilder = true)
public record Invoice(long id, Creditor creditor, Debtor debtor, LocalDate maturityDate, long valueInCents,
                      boolean processed) {

}
