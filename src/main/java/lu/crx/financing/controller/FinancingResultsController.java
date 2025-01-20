package lu.crx.financing.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lu.crx.financing.FinancingResultsQuery;
import lu.crx.financing.dto.FinancingResultsDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/yb-task")
public class FinancingResultsController {

    private final FinancingResultsQuery financingResultsQuery;

    @Operation(summary = "Get Financing Results",
            description = "Retrieves processing results generated after invoices processing")
    @GetMapping(value = "/v1/financing-results", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinancingResultsDto> getFinancingResults(
            @RequestParam(value = "invoiceId") long invoiceId) {

        FinancingResultsDto financingResultsDto = financingResultsQuery.getFinancingResults(invoiceId);
        log.info("FinancingResultsController: financingResults have been fetched for the invoice with id: {}",
                invoiceId);
        return ResponseEntity.ok().body(financingResultsDto);
    }
}
