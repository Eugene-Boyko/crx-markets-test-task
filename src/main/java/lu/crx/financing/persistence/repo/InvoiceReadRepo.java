package lu.crx.financing.persistence.repo;

import org.springframework.stereotype.Repository;

import io.ebean.Database;
import io.ebean.PagedList;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lu.crx.financing.persistence.entity.Invoice;
import lu.crx.financing.persistence.entity.query.QCreditor;
import lu.crx.financing.persistence.entity.query.QInvoice;
import lu.crx.financing.persistence.mapper.InvoiceMapper;

@Repository
@RequiredArgsConstructor
public class InvoiceReadRepo {

    public static final String INVOICE_PROCESSED_STATUS = "processed";
    public static final String INVOICE_ID_COLUMN_NAME = "id";
    private final Database database;
    private final InvoiceMapper mapper;

    public List<lu.crx.financing.domain.entity.Invoice> getAllInvoices(int offset, int pageSize, String orderBy) {
        PagedList<Invoice> pagedList = database.find(Invoice.class)
                .orderBy().asc(orderBy)
                .setFirstRow(offset)
                .setMaxRows(pageSize)
                .findPagedList();
        return mapper.toDomain(pagedList.getList());
    }

    public List<lu.crx.financing.domain.entity.Invoice> getProcessedInvoices(int offset, int pageSize, String orderBy) {
        PagedList<Invoice> pagedList = database.find(Invoice.class)
                .where().eq(INVOICE_PROCESSED_STATUS, true)
                .orderBy().asc(orderBy)
                .setFirstRow(offset)
                .setMaxRows(pageSize)
                .findPagedList();
        return mapper.toDomain(pagedList.getList());
    }

    public List<lu.crx.financing.domain.entity.Invoice> getUnprocessedInvoices(int offset, int pageSize, LocalDate processingDate) {
        QInvoice qInvoice = QInvoice.alias();
        QCreditor qCreditor = QCreditor.alias();

        List<Invoice> invoices = new QInvoice()
                .select(qInvoice.maturityDate, qInvoice.valueInCents, qInvoice.processed)
                .creditor.fetch(qCreditor.maxFinancingRateInBps)
                .where()
                .processed.is(false)
                .maturityDate.ge(processingDate)
                .orderBy(INVOICE_ID_COLUMN_NAME)
                .setFirstRow(offset)
                .setMaxRows(pageSize)
                .setDisableLazyLoading(true)
                .findList();
        return mapper.toDomain(invoices);
    }
}
