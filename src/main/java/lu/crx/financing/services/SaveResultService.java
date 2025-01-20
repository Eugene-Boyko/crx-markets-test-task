package lu.crx.financing.services;

import org.springframework.stereotype.Service;

import io.ebean.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lu.crx.financing.SaveResults;
import lu.crx.financing.domain.entity.FinancingResults;
import lu.crx.financing.domain.entity.Invoice;
import lu.crx.financing.persistence.repo.FinancingResultsSaveRepo;
import lu.crx.financing.persistence.repo.InvoiceSaveRepo;

@Service
@RequiredArgsConstructor
public class SaveResultService implements SaveResults {
    private final InvoiceSaveRepo invoiceSaveRepo;
    private final FinancingResultsSaveRepo financingResultsSaveRepo;

    @Transactional
    public void saveResults(Invoice invoice, FinancingResults financingResults) {
        financingResultsSaveRepo.save(financingResults);
        invoiceSaveRepo.update(invoice);
    }
}
