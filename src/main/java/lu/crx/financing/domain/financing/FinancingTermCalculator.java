package lu.crx.financing.domain.financing;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.experimental.UtilityClass;
import lu.crx.financing.domain.entity.FinancingTerm;

/**
 * Financing Term - the duration in days between the financing date and the Invoice's maturity date.
 * The financing is essentially a loan given by the Purchaser to the Creditor for the duration of the term,
 * with a certain financing rate and responsibility of the Debtor to pay back the loan.
 */
@UtilityClass
public class FinancingTermCalculator {

    public static FinancingTerm calculate(LocalDate currentFinancingDate, LocalDate invoiceMaturityDate) {
        return new FinancingTerm(Math.toIntExact(ChronoUnit.DAYS.between(currentFinancingDate , invoiceMaturityDate)));
    }
}
