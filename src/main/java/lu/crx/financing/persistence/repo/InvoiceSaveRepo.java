package lu.crx.financing.persistence.repo;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lu.crx.financing.persistence.entity.Invoice;
import lu.crx.financing.persistence.mapper.InvoiceMapper;

@Repository
@RequiredArgsConstructor
public class InvoiceSaveRepo {

    private final InvoiceMapper mapper;

    public void update(lu.crx.financing.domain.entity.Invoice invoice) {
        Invoice dbInvoice = mapper.fromDomain(invoice);
        dbInvoice.markPropertyUnset("debtor"); // it wasn't fetched, it shouldn't be updated
        dbInvoice.update();
    }
}
