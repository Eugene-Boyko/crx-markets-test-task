package lu.crx.financing.services;

import org.springframework.stereotype.Service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lu.crx.financing.InvoiceQuery;
import lu.crx.financing.dto.InvoiceDto;
import lu.crx.financing.persistence.repo.ReadInvoiceRepo;
import lu.crx.financing.services.mapper.InvoiceDtoMapper;

@Service
@RequiredArgsConstructor
public class InvoiceQueryService implements InvoiceQuery {

    private final ReadInvoiceRepo repo;
    private final InvoiceDtoMapper dtoMapper;

    @Override
    public List<InvoiceDto> getProcessedInvoices(int offset, int pageSize, String orderBy) {
        return dtoMapper.toDto(repo.getProcessedInvoices(offset, pageSize, orderBy));
    }

    @Override
    public List<InvoiceDto> getAllInvoices(int offset, int pageSize, String orderBy) {
        return dtoMapper.toDto(repo.getAllInvoices(offset, pageSize, orderBy));
    }
}
