package lu.crx.financing;

import lu.crx.financing.domain.entity.FinancingResults;
import lu.crx.financing.domain.entity.Invoice;

/**
 * Interface that represents the mutation part of the CQRS pattern for entities update
 */
public interface SaveResults {

    void saveResults(Invoice invoice, FinancingResults financingResults);
}
