package lu.crx.financing.services.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import java.util.List;
import lu.crx.financing.domain.Invoice;
import lu.crx.financing.dto.InvoiceDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface InvoiceDtoMapper {

    List<InvoiceDto> toDto(List<Invoice> invoices);
}
