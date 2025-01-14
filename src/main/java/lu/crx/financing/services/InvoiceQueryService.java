package lu.crx.financing.services;

import org.springframework.stereotype.Service;

import java.util.List;
import lu.crx.financing.InvoiceQuery;
import lu.crx.financing.dto.InvoiceDto;

@Service
public class InvoiceQueryService implements InvoiceQuery {

    @Override
    public List<InvoiceDto> getProcessedInvoices() {
        return List.of();
    }

    @Override
    public List<InvoiceDto> getAllInvoices() {
        return List.of();
    }
}
