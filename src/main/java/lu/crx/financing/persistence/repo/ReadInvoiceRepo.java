package lu.crx.financing.persistence.repo;

import org.springframework.stereotype.Repository;

import io.ebean.Database;
import io.ebean.PagedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lu.crx.financing.persistence.entity.Invoice;
import lu.crx.financing.persistence.mapper.InvoiceMapper;


@Repository
@RequiredArgsConstructor
public class ReadInvoiceRepo {

    private final Database database;
    private final InvoiceMapper invoiceMapper;

    public List<lu.crx.financing.domain.Invoice> getAllInvoices(int offset, int pageSize, String orderBy) {
        PagedList<Invoice> pagedList = database.find(Invoice.class)
                .orderBy().asc(orderBy)
                .setFirstRow(offset)
                .setMaxRows(pageSize)
                .findPagedList();
        return invoiceMapper.toDomain(pagedList.getList());
    }

    public List<lu.crx.financing.domain.Invoice> getProcessedInvoices(int offset, int pageSize, String orderBy) {
        PagedList<Invoice> pagedList = database.find(Invoice.class)
                .where().eq("processed", true)
                .orderBy().asc(orderBy)
                .setFirstRow(offset)
                .setMaxRows(pageSize)
                .findPagedList();
        return invoiceMapper.toDomain(pagedList.getList());
    }
}
