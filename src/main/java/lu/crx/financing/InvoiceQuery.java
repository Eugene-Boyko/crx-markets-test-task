package lu.crx.financing;

import java.util.List;
import lu.crx.financing.dto.InvoiceDto;

public interface InvoiceQuery {

    List<InvoiceDto> getProcessedInvoices();

    List<InvoiceDto> getAllInvoices();

}
