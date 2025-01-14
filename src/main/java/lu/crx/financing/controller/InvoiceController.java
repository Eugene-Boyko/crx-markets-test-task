package lu.crx.financing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lu.crx.financing.InvoiceProcess;
import lu.crx.financing.InvoiceQuery;
import lu.crx.financing.dto.InvoiceDto;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceQuery invoiceQuery;
    private final InvoiceProcess invoiceProcess;

    @GetMapping(value = "yb-test-task/invoices")
    public ResponseEntity<List<InvoiceDto>> getInvoices(@RequestParam("all") boolean all) {
        List<InvoiceDto> invoiceDtos = all ? invoiceQuery.getAllInvoices() : invoiceQuery.getProcessedInvoices();
        return ResponseEntity.ok().body(invoiceDtos);
    }

    @PostMapping(value = "yb-test-task/invoices")
    public ResponseEntity<Void> processInvoices(@RequestBody @Valid @FutureOrPresent LocalDate dateForProcessing) {
        log.trace("InvoiceController: processInvoices, requested dateForProcessing: {}", dateForProcessing);

        invoiceProcess.processInvoices(dateForProcessing);

        log.trace("InvoiceController: processInvoices finished, dateForProcessing: {}", dateForProcessing);
        return ResponseEntity.accepted().build();
    }

}
