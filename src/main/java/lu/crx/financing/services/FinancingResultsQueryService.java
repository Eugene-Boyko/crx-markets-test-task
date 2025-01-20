package lu.crx.financing.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lu.crx.financing.FinancingResultsQuery;
import lu.crx.financing.dto.FinancingResultsDto;
import lu.crx.financing.persistence.repo.FinancingResultsReadRepo;
import lu.crx.financing.services.mapper.FinancingResultsDtoMapper;

@Service
@RequiredArgsConstructor
public class FinancingResultsQueryService implements FinancingResultsQuery {

    private final FinancingResultsReadRepo repo;
    private final FinancingResultsDtoMapper dtoMapper;

    @Override
    public FinancingResultsDto getFinancingResults(long invoiceId) {
        return dtoMapper.toDto(repo.getFinancingResultsByInvoiceId(invoiceId));
    }
}
