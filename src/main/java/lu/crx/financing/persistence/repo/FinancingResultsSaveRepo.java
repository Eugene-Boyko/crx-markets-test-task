package lu.crx.financing.persistence.repo;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lu.crx.financing.domain.entity.FinancingResults;
import lu.crx.financing.persistence.mapper.FinancingResultsMapper;

@Repository
@RequiredArgsConstructor
public class FinancingResultsSaveRepo {

    private final FinancingResultsMapper mapper;

    public void save(FinancingResults financingResults) {
        mapper.fromDomain(financingResults).save();
    }
}
