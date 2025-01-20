package lu.crx.financing.services;

import org.springframework.stereotype.Service;

import io.ebean.Database;
import io.ebean.Transaction;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lu.crx.financing.persistence.entity.Creditor;
import lu.crx.financing.persistence.entity.Debtor;
import lu.crx.financing.persistence.entity.FinancingResults;
import lu.crx.financing.persistence.entity.Invoice;
import lu.crx.financing.persistence.entity.Purchaser;
import lu.crx.financing.persistence.entity.PurchaserFinancingSettings;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.jeasy.random.randomizers.misc.BooleanRandomizer;
import org.jeasy.random.randomizers.number.IntegerRandomizer;
import org.jeasy.random.randomizers.number.LongRandomizer;

@Service
@RequiredArgsConstructor
public class SeedingTestDataService {

    private final Database database;

    public void seeding() {

        clearDb();

        List<Creditor> creditors = generateCreditors();
        List<Debtor> debtors = generateDebtors();
        List<Purchaser> purchasers = generatePurchasers();
        List<Invoice> notProcessedInvoices = generateNotProcessedInvoices();
        List<Invoice> dummyProcessedInvoices = generateDummyProcessedInvoices();
        List<Invoice> invoices = Stream.concat(notProcessedInvoices.stream(), dummyProcessedInvoices.stream()).toList();
        List<PurchaserFinancingSettings> purchaserFinancingSettings = generatePurchaserFinancingSettings();

        database.saveAll(creditors);
        database.saveAll(debtors);
        database.saveAll(purchasers);
        database.saveAll(invoices);
        database.saveAll(purchaserFinancingSettings);
    }

    private static List<PurchaserFinancingSettings> generatePurchaserFinancingSettings() {
        EasyRandomParameters purchaserFinancingSettingsParameters = new EasyRandomParameters();
        purchaserFinancingSettingsParameters.excludeField(
                FieldPredicates.named("id")
                        .and(FieldPredicates.inClass(PurchaserFinancingSettings.class)));
        purchaserFinancingSettingsParameters.excludeField(
                FieldPredicates.named("minimumFinancingTermInDays")
                        .and(FieldPredicates.inClass(PurchaserFinancingSettings.class)));
        purchaserFinancingSettingsParameters.randomize(Long.class, new LongRandomizer() {
            @Override
            public Long getRandomValue() {
                return (long) (1 + Math.random() * 109);
            }
        });
        purchaserFinancingSettingsParameters.randomize(Integer.class, new IntegerRandomizer() {
            @Override
            public Integer getRandomValue() {
                return (int) (10 + Math.random() * 100);
            }
        });
        EasyRandom purchaserFinancingSettingsGenerator = new EasyRandom(purchaserFinancingSettingsParameters);
        List<PurchaserFinancingSettings> purchaserFinancingSettings = purchaserFinancingSettingsGenerator.objects(
                PurchaserFinancingSettings.class, 10000).toList();
        purchaserFinancingSettings.forEach(
                setting -> setting.setMinimumFinancingTermInDays(RandomGenerator.getDefault().nextInt(1, 30)));
        return purchaserFinancingSettings;
    }

    private static List<Invoice> generateNotProcessedInvoices() {
        EasyRandomParameters invoiceParameters = new EasyRandomParameters();
        invoiceParameters.stringLengthRange(3, 10);
        invoiceParameters.excludeField(
                FieldPredicates.named("id")
                        .and(FieldPredicates.inClass(Invoice.class)));
        invoiceParameters.excludeField(
                FieldPredicates.named("processed")
                        .and(FieldPredicates.inClass(Invoice.class)));
        invoiceParameters.excludeField(
                FieldPredicates.named("valueInCents")
                        .and(FieldPredicates.inClass(Invoice.class)));
        invoiceParameters.dateRange(LocalDate.now(ZoneOffset.UTC).plusDays(1),
                LocalDate.now(ZoneOffset.UTC).plusDays(360));
        invoiceParameters.randomize(Long.class, new LongRandomizer() {
            @Override
            public Long getRandomValue() {
                return (long) (1 + Math.random() * 109);
            }
        });
        EasyRandom invoiceGenerator = new EasyRandom(invoiceParameters);
        List<Invoice> invoices = invoiceGenerator.objects(Invoice.class, 10100).toList();
        invoices.forEach(invoice -> invoice.setValueInCents(RandomGenerator.getDefault().nextLong(10000000)));
        return invoices;
    }

    private static List<Invoice> generateDummyProcessedInvoices() {
        EasyRandomParameters invoiceParameters = new EasyRandomParameters();
        invoiceParameters.stringLengthRange(3, 10);
        invoiceParameters.excludeField(
                FieldPredicates.named("id")
                        .and(FieldPredicates.inClass(Invoice.class)));
        invoiceParameters.dateRange(LocalDate.now(ZoneOffset.UTC).plusDays(1),
                LocalDate.now(ZoneOffset.UTC).plusDays(360));
        invoiceParameters.randomize(Long.class, new LongRandomizer() {
            @Override
            public Long getRandomValue() {
                return (long) (1 + Math.random() * 109);
            }
        });
        invoiceParameters.randomize(Boolean.class, new BooleanRandomizer() {
            @Override
            public Boolean getRandomValue() {
                return true;
            }
        });
        EasyRandom invoiceGenerator = new EasyRandom(invoiceParameters);
        return invoiceGenerator.objects(Invoice.class, 500000).toList();
    }

    private static List<Purchaser> generatePurchasers() {
        EasyRandomParameters purchaserParameters = new EasyRandomParameters();
        purchaserParameters.stringLengthRange(3, 10);
        purchaserParameters.excludeField(FieldPredicates.named("id")
                .and(FieldPredicates.inClass(Purchaser.class)));
        EasyRandom purchaserGenerator = new EasyRandom(purchaserParameters);
        return purchaserGenerator.objects(Purchaser.class, 110).toList();
    }

    private static List<Debtor> generateDebtors() {
        EasyRandomParameters debtorParameters = new EasyRandomParameters();
        debtorParameters.stringLengthRange(3, 10);
        debtorParameters.excludeField(FieldPredicates.named("id")
                .and(FieldPredicates.inClass(Debtor.class)));
        EasyRandom debtorGenerator = new EasyRandom(debtorParameters);
        return debtorGenerator.objects(Debtor.class, 110).toList();
    }

    private static List<Creditor> generateCreditors() {
        EasyRandomParameters creditorParameters = new EasyRandomParameters();
        creditorParameters.stringLengthRange(3, 10);
        creditorParameters.excludeField(FieldPredicates.named("id")
                .and(FieldPredicates.inClass(Creditor.class)));
        creditorParameters.randomize(Integer.class, new IntegerRandomizer() {
            @Override
            public Integer getRandomValue() {
                return (int) (Math.random() * 20);
            }
        });
        EasyRandom creditorGenerator = new EasyRandom(creditorParameters);
        return creditorGenerator.objects(Creditor.class, 110).toList();
    }

    private void clearDb() {
        try (Transaction transaction = database.beginTransaction()) {
            database.sqlUpdate("SET REFERENTIAL_INTEGRITY FALSE").execute();
            database.truncate(FinancingResults.class, PurchaserFinancingSettings.class, Invoice.class, Debtor.class,
                    Creditor.class, Purchaser.class);
            database.sqlUpdate("SET REFERENTIAL_INTEGRITY TRUE").execute();
        }
    }
}
