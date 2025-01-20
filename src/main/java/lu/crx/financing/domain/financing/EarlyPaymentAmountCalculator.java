package lu.crx.financing.domain.financing;

import lombok.experimental.UtilityClass;
import lu.crx.financing.domain.entity.EarlyPaymentAmount;
import lu.crx.financing.domain.entity.FinancingRate;

/**
 * Early payment amount - the amount of money paid by the Purchaser to the Creditor for the particular financed invoice
 * on financing date. This amount is less than the value of the invoice.
 */
@UtilityClass
public class EarlyPaymentAmountCalculator {

    private static final int BPS_COEFFICIENT = 10000;

    public EarlyPaymentAmount calculate(long invoiceValueInCents, FinancingRate financingRate) {
        return new EarlyPaymentAmount(
                invoiceValueInCents - (invoiceValueInCents * financingRate.rateInBps() / BPS_COEFFICIENT));
    }
}
