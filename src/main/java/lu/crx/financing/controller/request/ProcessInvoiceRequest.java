package lu.crx.financing.controller.request;

import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

public record ProcessInvoiceRequest(@FutureOrPresent LocalDate date) {

}
