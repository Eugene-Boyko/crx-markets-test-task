package lu.crx.financing;

import lu.crx.financing.dto.FinancingResultsDto;

/**
 * Interface that represents the query part of the CQRS pattern for financing results retrieving
 */
public interface FinancingResultsQuery {

    FinancingResultsDto getFinancingResults(long financingResultsQuery);

}
