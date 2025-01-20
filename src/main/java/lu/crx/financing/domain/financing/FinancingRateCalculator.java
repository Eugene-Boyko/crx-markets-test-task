package lu.crx.financing.domain.financing;

import lombok.experimental.UtilityClass;
import lu.crx.financing.domain.entity.FinancingRate;
import lu.crx.financing.domain.entity.FinancingTerm;

/**
 * Financing Rate - the actual financing rate for a particular Invoice, proportional to its financing term. Calculated
 * as financingRate = annualRate * financingTerm / 360. In Bps
 */
@UtilityClass
public class FinancingRateCalculator {

    private static final int DAYS_IN_YEAR = 360;

    public static FinancingRate calculate(int purchaserAnnualRateInBps, FinancingTerm financingTerm) {
        float purchaserRatePerDay = (float) purchaserAnnualRateInBps / DAYS_IN_YEAR;
        return new FinancingRate(Math.round(financingTerm.termInDays() * purchaserRatePerDay));
    }
}
