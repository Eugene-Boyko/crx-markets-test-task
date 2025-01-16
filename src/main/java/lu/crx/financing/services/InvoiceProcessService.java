package lu.crx.financing.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import lu.crx.financing.InvoiceProcess;

@Slf4j
@Service
public class InvoiceProcessService implements InvoiceProcess {

    @Override
    public void processInvoices(LocalDate date) {
        log.info("InvoiceProcessService: processInvoices started at {}", LocalDateTime.now());

        log.info("InvoiceProcessService: processInvoices finished at {}", LocalDateTime.now());
    }
}
