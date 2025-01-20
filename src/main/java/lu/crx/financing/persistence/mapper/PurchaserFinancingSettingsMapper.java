package lu.crx.financing.persistence.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import lu.crx.financing.domain.entity.PurchaserFinancingSettings;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface PurchaserFinancingSettingsMapper {

    PurchaserFinancingSettings toDomain(lu.crx.financing.persistence.entity.PurchaserFinancingSettings settings);
}
