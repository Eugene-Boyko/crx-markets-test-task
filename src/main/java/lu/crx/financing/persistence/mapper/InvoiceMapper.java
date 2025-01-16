package lu.crx.financing.persistence.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import java.util.List;
import lu.crx.financing.domain.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface InvoiceMapper {

    List<Invoice> toDomain(List<lu.crx.financing.persistence.entity.Invoice> invoices);
}
