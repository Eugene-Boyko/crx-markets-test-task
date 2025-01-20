package lu.crx.financing.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import lu.crx.financing.InvoiceProcess;
import lu.crx.financing.InvoiceQuery;
import lu.crx.financing.controller.request.ProcessInvoiceRequest;
import lu.crx.financing.dto.CreditorDto;
import lu.crx.financing.dto.DebtorDto;
import lu.crx.financing.dto.InvoiceDto;
import lu.crx.financing.dto.PurchaserDto;
import lu.crx.financing.dto.PurchaserFinancingSettingsDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InvoiceControllerTest {

    private final static long GENERAL_ID = 1;

    @Mock
    private InvoiceQuery invoiceQuery;
    @Mock
    private InvoiceProcess invoiceProcess;
    @InjectMocks
    private InvoiceController controller;

    @Test
    public void getInvoices_shouldCallInvoiceQuery_withExpectedValues() {
        // given
        int offset = 10;
        int pageSize = 20;
        String orderBy = "id";

        // when
        controller.getInvoices(offset, pageSize, orderBy);

        // then
        verify(invoiceQuery).getAllInvoices(offset, pageSize, orderBy);
        verifyNoInteractions(invoiceProcess);
    }

    @Test
    public void getInvoices_shouldReturnExpectedValue() {
        // given
        int offset = 10;
        int pageSize = 20;
        String orderBy = "id";
        String creditorName = "testCreditorName";
        int creditorMaxFinancingRateInBps = 3;
        CreditorDto creditor = getCreditorDto(creditorName, creditorMaxFinancingRateInBps);
        String debtorName = "testDebtorName";
        DebtorDto debtor = getDebtorDto(debtorName);
        LocalDate maturityDate = LocalDate.now(ZoneOffset.UTC).plusDays(5);
        int invoiceValueInCents = 10000;
        InvoiceDto invoice = getInvoiceDto(creditor, debtor, maturityDate, invoiceValueInCents, true);
        InvoiceDto invoice2 = getInvoiceDto(creditor, debtor, maturityDate.plusDays(2), invoiceValueInCents + 1000,
                true);

        List<InvoiceDto> expectedInvoices = List.of(invoice, invoice2);
        when(invoiceQuery.getAllInvoices(anyInt(), anyInt(), anyString())).thenReturn(expectedInvoices);

        // when
        ResponseEntity<List<InvoiceDto>> invoicesResponse = controller.getInvoices(offset, pageSize, orderBy);

        // then
        verify(invoiceQuery).getAllInvoices(offset, pageSize, orderBy);
        assertThat(invoicesResponse.getBody()).containsExactlyInAnyOrderElementsOf(expectedInvoices);
    }

    @Test
    public void processInvoices_shouldCallInvoiceProcess_withExpectedValues() {
        // given
        LocalDate processingDate = LocalDate.now(ZoneOffset.UTC);
        int invoicesToProcessAmount = 1000;

        // when
        controller.processInvoices(new ProcessInvoiceRequest(processingDate, invoicesToProcessAmount));

        //then
        verify(invoiceProcess).processInvoices(processingDate, invoicesToProcessAmount);
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
}