package lu.crx.financing;

import java.util.List;
import lu.crx.financing.dto.InvoiceDto;

/**
 * Interface that represents the query part of the CQRS pattern for invoices retrieving
 */
public interface InvoiceQuery {

    List<InvoiceDto> getAllInvoices(int offset, int pageSize, String orderBy);

    List<InvoiceDto> getProcessedInvoices(int offset, int pageSize, String orderBy);
}
