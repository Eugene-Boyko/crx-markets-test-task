package lu.crx.financing.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lu.crx.financing.InvoiceProcess;
import lu.crx.financing.InvoiceQuery;
import lu.crx.financing.controller.request.ProcessInvoiceRequest;
import lu.crx.financing.dto.InvoiceDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/yb-task")
public class InvoiceController {

    private final InvoiceQuery invoiceQuery;
    private final InvoiceProcess invoiceProcess;

    @Operation(summary = "Get all Invoices", description = "Retrieves all invoices.")
    @GetMapping(value = "/v1/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InvoiceDto>> getInvoices(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
            @RequestParam(value = "orderBy", required = false, defaultValue = "id") String orderBy) {

        List<InvoiceDto> allInvoices = invoiceQuery.getAllInvoices(offset, pageSize, orderBy);
        log.info("InvoiceController: getInvoices, {} invoices have been fetched", allInvoices.size());
        return ResponseEntity.ok().body(allInvoices);
    }

    @Operation(summary = "Get only processed Invoices", description = "Retrieves only processed invoices.")
    @GetMapping(value = "/v1/invoices/processed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InvoiceDto>> getProcessedInvoices(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
            @RequestParam(value = "orderBy", required = false, defaultValue = "id") String orderBy) {

        List<InvoiceDto> processedInvoices = invoiceQuery.getProcessedInvoices(offset, pageSize, orderBy);
        log.info("InvoiceController: getProcessedInvoices, {} invoices have been fetched", processedInvoices.size());
        return ResponseEntity.ok().body(processedInvoices);
    }

    @Operation(summary = "Process Invoices", description = "Starts invoices processing.")
    @PostMapping(value = "/v1/invoices",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> processInvoices(@RequestBody @Valid ProcessInvoiceRequest request) {
        log.trace("InvoiceController: processInvoices, requested for processingDate: {}", request.processingDate());

        invoiceProcess.processInvoices(request.processingDate(), request.invoicesToProcessAmount());

        log.trace("InvoiceController: processInvoices finished, for processingDate: {}", request.processingDate());
        return ResponseEntity.accepted().build();
    }
}
