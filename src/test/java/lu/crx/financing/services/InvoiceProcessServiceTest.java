package lu.crx.financing.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.stream.LongStream;
import lu.crx.financing.SaveResults;
import lu.crx.financing.domain.entity.Creditor;
import lu.crx.financing.domain.entity.Debtor;
import lu.crx.financing.domain.entity.FinancingResults;
import lu.crx.financing.domain.entity.FinancingTerm;
import lu.crx.financing.domain.entity.Invoice;
import lu.crx.financing.domain.entity.MaxAnnualFinancingRateForFinancingTerm;
import lu.crx.financing.domain.entity.Purchaser;
import lu.crx.financing.domain.entity.PurchaserFinancingSettings;
import lu.crx.financing.persistence.repo.InvoiceReadRepo;
import lu.crx.financing.persistence.repo.PurchaserFinancingSettingsReadRepo;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InvoiceProcessServiceTest {

    private static final int DAYS_IN_YEAR = 360;

    @Mock
    private InvoiceReadRepo readInvoicesRepo;
    @Mock
    private SaveResults saveResults;
    @Mock
    private PurchaserFinancingSettingsReadRepo purchaserFinancingSettingsReadRepo;
    @Mock
    private ExecutorService executorService;

    @Captor
    private ArgumentCaptor<FinancingResults> financingResultsCaptor;
    @Captor
    private ArgumentCaptor<Invoice> invoiceArgumentCaptor;

    private final static int PROCESSING_BATCH_SIZE = 10;
    private final static int THREADS_AMOUNT = 5;

    private InvoiceProcessService service;


    @BeforeEach
    void setUp() {
        service = new InvoiceProcessService(readInvoicesRepo, saveResults, purchaserFinancingSettingsReadRepo,
                PROCESSING_BATCH_SIZE, THREADS_AMOUNT);
    }

    @Test
    public void processInvoices_shouldCallUnderlyingServices_withAppropriateValues() {
        // given
        String purchaserName = "testPurchaserName";
        int purchaserMinimumFinancingTermInDays = 3;
        LocalDate processingDate = LocalDate.now(ZoneOffset.UTC);
        int invoicesToProcessAmount = 25;
        List<Pair<Integer, Integer>> batches = List.of(
                Pair.of(0, 10),
                Pair.of(10, 10),
                Pair.of(20, 5));
        Creditor creditor = getCreditor();
        Debtor debtor = getDebtor();
        LocalDate maturityDate = LocalDate.now(ZoneOffset.UTC).plusDays(5);
        int purchaserAnnualRateInBps = 50;

        when(readInvoicesRepo.getUnprocessedInvoices(batches.get(0).getLeft(), batches.get(0).getRight(),
                processingDate))
                .thenReturn(LongStream.range(0, 10)
                        .mapToObj(longValue -> new Invoice(longValue, creditor, debtor, maturityDate,
                                longValue + 10000, false))
                        .toList());
        when(readInvoicesRepo.getUnprocessedInvoices(batches.get(1).getLeft(), batches.get(1).getRight(),
                processingDate))
                .thenReturn(LongStream.range(10, 20)
                        .mapToObj(longValue -> new Invoice(longValue, creditor, debtor, maturityDate,
                                longValue + 10000, false))
                        .toList());
        when(readInvoicesRepo.getUnprocessedInvoices(batches.get(2).getLeft(), batches.get(2).getRight(),
                processingDate))
                .thenReturn(LongStream.range(20, 25)
                        .mapToObj(longValue -> new Invoice(longValue, creditor, debtor, maturityDate,
                                longValue + 10000, false))
                        .toList());
        MaxAnnualFinancingRateForFinancingTerm maxAnnualFinancingRateForFinancingTerm = getMaxAnnualFinancingRateForFinancingTerm(
                creditor.maxFinancingRateInBps(), maturityDate);
        FinancingTerm financingTerm = getFinancingTerm(maturityDate);
        when(purchaserFinancingSettingsReadRepo
                .findSettings(creditor.id(), maxAnnualFinancingRateForFinancingTerm, financingTerm))
                .thenReturn(Optional.of(
                        getPurchaserFinancingSettings(creditor, purchaserAnnualRateInBps, getPurchaser(purchaserName),
                                purchaserMinimumFinancingTermInDays)));

        // when
        service.processInvoices(processingDate, invoicesToProcessAmount);

        // then
        verify(readInvoicesRepo).getUnprocessedInvoices(0, 10, processingDate);
        verify(readInvoicesRepo).getUnprocessedInvoices(10, 10, processingDate);
        verify(readInvoicesRepo).getUnprocessedInvoices(20, 5, processingDate);

        verify(purchaserFinancingSettingsReadRepo, times(25)).findSettings(creditor.id(),
                maxAnnualFinancingRateForFinancingTerm, financingTerm);
        verify(saveResults, times(25)).saveResults(invoiceArgumentCaptor.capture(), financingResultsCaptor.capture());
        List<Invoice> processedInvoices = invoiceArgumentCaptor.getAllValues();
        List<FinancingResults> processingResults = financingResultsCaptor.getAllValues();
        assertThat(processedInvoices).hasSize(25);
        assertThat(processingResults).hasSize(25);

        processedInvoices.forEach(invoice -> assertThat(invoice.processed()).isTrue());
        processingResults.forEach(results -> {
            assertThat(results.earlyPaymentAmount().amountInCents()).isEqualTo(
                    (results.invoice().id() + 10000) - ((results.invoice().id() + 10000) * results.financingRate().rateInBps() / 10000));
            assertThat(results.financingDate()).isEqualTo(processingDate);
            assertThat(results.financingTerm().termInDays()).isEqualTo(financingTerm.termInDays());
            assertThat(results.financingRate().rateInBps()).isEqualTo(Math.round((float) purchaserAnnualRateInBps / DAYS_IN_YEAR * financingTerm.termInDays()));
        });
    }

    private MaxAnnualFinancingRateForFinancingTerm getMaxAnnualFinancingRateForFinancingTerm(
            int creditorMaxFinancingRateInBps, LocalDate maturityDate) {
        return new MaxAnnualFinancingRateForFinancingTerm(creditorMaxFinancingRateInBps * 360 / (Math.toIntExact(
                ChronoUnit.DAYS.between(LocalDate.now(ZoneOffset.UTC), maturityDate))));
    }

    private FinancingTerm getFinancingTerm(LocalDate invoiceMaturityDate) {
        return new FinancingTerm(
                Math.toIntExact(ChronoUnit.DAYS.between(LocalDate.now(ZoneOffset.UTC), invoiceMaturityDate)));
    }

    private PurchaserFinancingSettings getPurchaserFinancingSettings(Creditor creditor, int annualRateInBps,
            Purchaser purchaser,
            int minimumFinancingTermInDays) {
        return new PurchaserFinancingSettings(1, creditor, annualRateInBps, purchaser, minimumFinancingTermInDays);
    }

    private Debtor getDebtor() {
        return new Debtor(1, "testDebtorName");
    }

    private Creditor getCreditor() {
        return new Creditor(1, "testDebtorName", 5);
    }

    private Purchaser getPurchaser(String name) {
        return new Purchaser(1, name);
    }
}