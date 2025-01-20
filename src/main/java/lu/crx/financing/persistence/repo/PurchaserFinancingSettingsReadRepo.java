package lu.crx.financing.persistence.repo;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lu.crx.financing.domain.entity.FinancingTerm;
import lu.crx.financing.domain.entity.MaxAnnualFinancingRateForFinancingTerm;
import lu.crx.financing.persistence.entity.PurchaserFinancingSettings;
import lu.crx.financing.persistence.entity.query.QPurchaser;
import lu.crx.financing.persistence.entity.query.QPurchaserFinancingSettings;
import lu.crx.financing.persistence.mapper.PurchaserFinancingSettingsMapper;

@Repository
@RequiredArgsConstructor
public class PurchaserFinancingSettingsReadRepo {

    private static final String ORDER_BY_CLAUSE = "abs(%s - %s)";

    private final PurchaserFinancingSettingsMapper mapper;

    public Optional<lu.crx.financing.domain.entity.PurchaserFinancingSettings> findSettings(
            long creditorId,
            MaxAnnualFinancingRateForFinancingTerm maxAnnualFinancingRateForFinancingTerm,
            FinancingTerm financingTerm) {
        QPurchaserFinancingSettings qSettings = QPurchaserFinancingSettings.alias();
        QPurchaser qPurchaser = QPurchaser.alias();

        String orderByClause = String.format(ORDER_BY_CLAUSE, qSettings.annualRateInBps,
                maxAnnualFinancingRateForFinancingTerm.rateInBps());
        PurchaserFinancingSettings settings = new QPurchaserFinancingSettings()
                .select(qSettings.annualRateInBps, qSettings.minimumFinancingTermInDays)
                .purchaser.fetch(qPurchaser.id)
                .where()
                .creditor.id.eq(creditorId)
                .minimumFinancingTermInDays.le(financingTerm.termInDays())
                .annualRateInBps.le(maxAnnualFinancingRateForFinancingTerm.rateInBps())
                .orderBy(orderByClause)
                .setDisableLazyLoading(true)
                .setMaxRows(1)
                .findOne();
        return Optional.ofNullable(settings).map(mapper::toDomain);
    }
}
