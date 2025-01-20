package lu.crx.financing.persistence.repo;

import org.springframework.stereotype.Repository;

import io.ebean.Database;
import lombok.RequiredArgsConstructor;
import lu.crx.financing.persistence.entity.FinancingResults;
import lu.crx.financing.persistence.mapper.FinancingResultsMapper;

@Repository
@RequiredArgsConstructor
public class FinancingResultsReadRepo {

    public static final String INVOICE_ID_COLUMN_NAME = "invoice_id";
    private final Database database;
    private final FinancingResultsMapper mapper;

    public lu.crx.financing.domain.entity.FinancingResults getFinancingResultsByInvoiceId(long invoiceId) {
        FinancingResults financingResults = database.find(FinancingResults.class)
                .where().eq(INVOICE_ID_COLUMN_NAME, invoiceId)
                .findOne();
        return mapper.toDomain(financingResults);
    }
}
