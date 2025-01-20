package lu.crx.financing.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.ZoneOffset;
import lu.crx.financing.FinancingResultsQuery;
import lu.crx.financing.dto.CreditorDto;
import lu.crx.financing.dto.DebtorDto;
import lu.crx.financing.dto.FinancingResultsDto;
import lu.crx.financing.dto.InvoiceDto;
import lu.crx.financing.dto.PurchaserDto;
import lu.crx.financing.dto.PurchaserFinancingSettingsDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FinancingResultsControllerTest {

    private final static long GENERAL_ID = 1;

    @Mock
    private FinancingResultsQuery financingResultsQuery;
    @InjectMocks
    private FinancingResultsController controller;

    @Test
    public void getFinancingResults_shouldCallFinancingResultsQuery_withExpectedValue() {
        // given
        long invoiceId = 1L;

        // when
        controller.getFinancingResults(invoiceId);

        // then
        verify(financingResultsQuery).getFinancingResults(invoiceId);
    }

    @Test
    public void getFinancingResults_shouldReturnExpectedValue() {
        // given
        long invoiceId = 1L;
        int financingTerm = 5;
        int financingRate = 4;
        LocalDate financingDate = LocalDate.now(ZoneOffset.UTC);
        long earlyPaymentAmount = 100;
        String creditorName = "testCreditorName";
        int creditorMaxFinancingRateInBps = 3;
        CreditorDto creditor = getCreditorDto(creditorName, creditorMaxFinancingRateInBps);
        String debtorName = "testDebtorName";
        DebtorDto debtor = getDebtorDto(debtorName);
        LocalDate maturityDate = LocalDate.now(ZoneOffset.UTC).plusDays(5);
        int invoiceValueInCents = 10000;
        InvoiceDto invoice = getInvoiceDto(creditor, debtor, maturityDate, invoiceValueInCents, true);
        int purchaserAnnualRateInBps = 50;
        String purchaserName = "testPurchaserName";
        PurchaserDto purchaserDto = getPurchaserDto(purchaserName);
        int purchaserMinimumFinancingTermInDays = 10;
        PurchaserFinancingSettingsDto purchaserFinancingSettings = getPurchaserFinancingSettingsDto(creditor,
                purchaserAnnualRateInBps, purchaserDto, purchaserMinimumFinancingTermInDays);
        FinancingResultsDto expectedFinancingResults = getFinancingResultsDto(financingTerm, financingRate,
                financingDate,
                earlyPaymentAmount, invoice,
                purchaserFinancingSettings);

        when(financingResultsQuery.getFinancingResults(anyLong())).thenReturn(expectedFinancingResults);

        // when
        ResponseEntity<FinancingResultsDto> financingResultsResponse = controller.getFinancingResults(invoiceId);

        // then
        verify(financingResultsQuery).getFinancingResults(invoiceId);
        assertThat(financingResultsResponse.getBody()).isEqualTo(expectedFinancingResults);
    }

    private FinancingResultsDto getFinancingResultsDto(int financingTerm, int financingRate, LocalDate financingDate,
            long earlyPaymentAmount, InvoiceDto invoice,
            PurchaserFinancingSettingsDto purchaserFinancingSettings) {
        return new FinancingResultsDto(GENERAL_ID, financingTerm, financingRate, financingDate, earlyPaymentAmount,
                invoice, purchaserFinancingSettings);
    }

    private InvoiceDto getInvoiceDto(CreditorDto creditor, DebtorDto debtor, LocalDate maturityDate, long valueInCents,
            boolean processed) {
        return new InvoiceDto(GENERAL_ID, creditor, debtor, maturityDate, valueInCents, processed);
    }

    private CreditorDto getCreditorDto(String name, int maxFinancingRateInBps) {
        return new CreditorDto(GENERAL_ID, name, maxFinancingRateInBps);
    }

    private DebtorDto getDebtorDto(String name) {
        return new DebtorDto(GENERAL_ID, name);
    }

    private PurchaserDto getPurchaserDto(String name) {
        return new PurchaserDto(GENERAL_ID, name);
    }

    private PurchaserFinancingSettingsDto getPurchaserFinancingSettingsDto(CreditorDto creditor,
            int annualRateInBps, PurchaserDto purchaser,
            int minimumFinancingTermInDays) {
        return new PurchaserFinancingSettingsDto(GENERAL_ID, creditor, annualRateInBps, purchaser,
                minimumFinancingTermInDays);
    }
}