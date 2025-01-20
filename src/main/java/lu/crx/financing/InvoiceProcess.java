package lu.crx.financing;

import java.time.LocalDate;

/**
 * Interface that is responsible for the invoice processing.
 * Invoice is processed only then all values of the Financing Results are calculated and saved
 */
public interface InvoiceProcess {

    void processInvoices(LocalDate processingDate, int invoicesToProcessAmount);
}
