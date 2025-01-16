package lu.crx.financing;

import java.util.List;
import lu.crx.financing.dto.InvoiceDto;

public interface InvoiceQuery {

    List<InvoiceDto> getProcessedInvoices(int offset, int pageSize, String orderBy);

    List<InvoiceDto> getAllInvoices(int offset, int pageSize, String orderBy);
}
