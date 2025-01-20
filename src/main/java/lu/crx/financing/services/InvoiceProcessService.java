package lu.crx.financing.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import lu.crx.financing.InvoiceProcess;
import lu.crx.financing.SaveResults;
import lu.crx.financing.domain.entity.EarlyPaymentAmount;
import lu.crx.financing.domain.entity.FinancingRate;
import lu.crx.financing.domain.entity.FinancingTerm;
import lu.crx.financing.domain.entity.FinancingResults;
import lu.crx.financing.domain.entity.Invoice;
import lu.crx.financing.domain.entity.MaxAnnualFinancingRateForFinancingTerm;
import lu.crx.financing.domain.entity.PurchaserFinancingSettings;
import lu.crx.financing.domain.financing.EarlyPaymentAmountCalculator;
import lu.crx.financing.domain.financing.FinancingRateCalculator;
import lu.crx.financing.domain.financing.FinancingTermCalculator;
import lu.crx.financing.domain.financing.MaxAnnualFinancingRateForFinancingTermCalculator;
import lu.crx.financing.persistence.repo.InvoiceReadRepo;
import lu.crx.financing.persistence.repo.PurchaserFinancingSettingsReadRepo;

@Slf4j
@Service
public class InvoiceProcessService implements InvoiceProcess {

    private final InvoiceReadRepo readRepo;
    private final SaveResults saveResults;
    private final PurchaserFinancingSettingsReadRepo purchaserFinancingSettingsReadRepo;
    private final int processingBatchSize;
    private final ExecutorService executorService;

    public InvoiceProcessService(InvoiceReadRepo readRepo, SaveResults saveResults,
            PurchaserFinancingSettingsReadRepo purchaserFinancingSettingsReadRepo,
            @Value("${financing.processing.batchSize}") int processingBatchSize,
            @Value("${financing.processing.threads}") int threads) {
        this.readRepo = readRepo;
        this.saveResults = saveResults;
        this.purchaserFinancingSettingsReadRepo = purchaserFinancingSettingsReadRepo;
        this.processingBatchSize = processingBatchSize;
        this.executorService = Executors.newFixedThreadPool(threads);
    }

    @Override
    public void processInvoices(LocalDate processingDate, int invoicesToProcessAmount) {
        log.info("InvoiceProcessService: processInvoices started at {}", LocalDateTime.now(ZoneOffset.UTC));

        int retrieveInvoicesBatchSize = Math.min(invoicesToProcessAmount, processingBatchSize);
        Map<Integer, Integer> batches = prepareBatches(retrieveInvoicesBatchSize, invoicesToProcessAmount);

        List<CompletableFuture<Void>> features = batches.entrySet().stream()
                .map(firstRowToBatchSize -> CompletableFuture.runAsync(
                        createTask(processingDate, firstRowToBatchSize.getKey(),
                                firstRowToBatchSize.getValue()), executorService))
                .toList();
        CompletableFuture.allOf(features.toArray(new CompletableFuture[0])).join();

        log.info("InvoiceProcessService: processInvoices finished at {}", LocalDateTime.now(ZoneOffset.UTC));
    }

    private Runnable createTask(LocalDate processingDate, Integer batcheStart, int batchSize) {
        return () -> {
            List<Invoice> unprocessedInvoices = readRepo.getUnprocessedInvoices(batcheStart, batchSize, processingDate);
            log.info("InvoiceProcessService: processing batch {} and {} by thread {} at {}, processing invoices: {}",
                    batcheStart, batcheStart + batchSize, Thread.currentThread().getName(), LocalDateTime.now(ZoneOffset.UTC),
                    unprocessedInvoices.size());
            unprocessedInvoices.forEach(invoice -> processInvoice(invoice, processingDate));
        };
    }

    private Map<Integer, Integer> prepareBatches(int batchSize, int invoicesToProcessAmount) {
        Map<Integer, Integer> batches = new HashMap<>();
        for (int i = 0; i < invoicesToProcessAmount; i += batchSize) {
            batches.put(i, i + batchSize <= invoicesToProcessAmount ? batchSize
                    : batchSize - (i + batchSize - invoicesToProcessAmount));
        }
        return batches;
    }

    private void processInvoice(Invoice invoice, LocalDate processingDate) {
        FinancingTerm financingTerm = FinancingTermCalculator.calculate(processingDate, invoice.maturityDate());

        MaxAnnualFinancingRateForFinancingTerm maxAnnualFinancingRateForFinancingTerm =
                MaxAnnualFinancingRateForFinancingTermCalculator.calculateMaxAnnualFinancingRateForFinancingTermInBps(
                        invoice.creditor().maxFinancingRateInBps(), financingTerm);

        Optional<PurchaserFinancingSettings> purchaserSettings = purchaserFinancingSettingsReadRepo.findSettings(
                invoice.creditor().id(), maxAnnualFinancingRateForFinancingTerm, financingTerm);

        Optional<FinancingRate> financingRate = purchaserSettings
                .map((setting) -> FinancingRateCalculator.calculate(setting.annualRateInBps(), financingTerm));

        EarlyPaymentAmount earlyPaymentAmount = financingRate.map((fr) ->
                EarlyPaymentAmountCalculator.calculate(invoice.valueInCents(), fr)).orElse(new EarlyPaymentAmount(0));

        Invoice updatedInvoice = invoice.toBuilder().processed(true).build();
        FinancingResults financingResults = FinancingResults.builder()
                .invoice(updatedInvoice)
                .financingDate(processingDate)
                .financingRate(financingRate.orElse(new FinancingRate(0)))
                .financingTerm(financingTerm)
                .earlyPaymentAmount(earlyPaymentAmount)
                .purchaserFinancingSettings(purchaserSettings.orElse(null))
                .build();
        saveResults.saveResults(updatedInvoice, financingResults);
    }
}
