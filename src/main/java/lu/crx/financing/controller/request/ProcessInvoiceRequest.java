package lu.crx.financing.controller.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record ProcessInvoiceRequest(@FutureOrPresent @NotNull LocalDate processingDate, @Positive int invoicesToProcessAmount) {

}
