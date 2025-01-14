package lu.crx.financing.services;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import lu.crx.financing.dao.Creditor;
import lu.crx.financing.dao.Debtor;
import lu.crx.financing.dao.Invoice;
import lu.crx.financing.dao.Purchaser;
import lu.crx.financing.dao.PurchaserFinancingSettings;

@Slf4j
@Service
public class SeedingService {

    private final EntityManager entityManager;

    private Creditor creditor1;
    private Creditor creditor2;
    private Creditor creditor3;

    private Debtor debtor1;
    private Debtor debtor2;
    private Debtor debtor3;

    public SeedingService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void seedMasterData() {
        log.info("Seeding master data");

        // creditors
        creditor1 = Creditor.builder()
                .name("Coffee Beans LLC")
                .maxFinancingRateInBps(5)
                .build();
        entityManager.persist(creditor1);

        creditor2 = Creditor.builder()
                .name("Home Brew")
                .maxFinancingRateInBps(3)
                .build();
        entityManager.persist(creditor2);

        creditor3 = Creditor.builder()
                .name("Beanstalk")
                .maxFinancingRateInBps(2)
                .build();
        entityManager.persist(creditor3);

        // debtors
        debtor1 = Debtor.builder()
                .name("Chocolate Factory")
                .build();
        entityManager.persist(debtor1);

        debtor2 = Debtor.builder()
                .name("Sweets Inc")
                .build();
        entityManager.persist(debtor2);

        debtor3 = Debtor.builder()
                .name("ChocoLoco")
                .build();
        entityManager.persist(debtor3);

        // purchasers
        Purchaser purchaser1 = Purchaser.builder()
                .name("RichBank")
                .minimumFinancingTermInDays(10)
                .build();
        entityManager.persist(purchaser1);
        // purchaserFinancingSetting 1
        entityManager.persist(
                PurchaserFinancingSettings.builder()
                        .creditor(creditor1)
                        .annualRateInBps(50)
                        .purchaser(purchaser1)
                        .build());
        entityManager.persist(
                PurchaserFinancingSettings.builder()
                        .creditor(creditor2)
                        .annualRateInBps(60)
                        .purchaser(purchaser1)
                        .build());
        entityManager.persist(
                PurchaserFinancingSettings.builder()
                        .creditor(creditor3)
                        .annualRateInBps(30)
                        .purchaser(purchaser1)
                        .build());

        Purchaser purchaser2 = Purchaser.builder()
                .name("FatBank")
                .minimumFinancingTermInDays(12)
                .build();
        entityManager.persist(purchaser2);
        // purchaserFinancingSetting 2
        entityManager.persist(
                PurchaserFinancingSettings.builder()
                        .creditor(creditor1)
                        .annualRateInBps(40)
                        .purchaser(purchaser2)
                        .build());
        entityManager.persist(
                PurchaserFinancingSettings.builder()
                        .creditor(creditor2)
                        .annualRateInBps(80)
                        .purchaser(purchaser2)
                        .build());
        entityManager.persist(
                PurchaserFinancingSettings.builder()
                        .creditor(creditor3)
                        .annualRateInBps(25)
                        .purchaser(purchaser2)
                        .build());

        Purchaser purchaser3 = Purchaser.builder()
                .name("MegaBank")
                .minimumFinancingTermInDays(8)
                .build();
        entityManager.persist(purchaser3);
        // purchaserFinancingSetting 3
        entityManager.persist(
                PurchaserFinancingSettings.builder()
                        .creditor(creditor1)
                        .annualRateInBps(30)
                        .purchaser(purchaser3)
                        .build());
        entityManager.persist(
                PurchaserFinancingSettings.builder()
                        .creditor(creditor2)
                        .annualRateInBps(50)
                        .purchaser(purchaser3)
                        .build());
        entityManager.persist(
                PurchaserFinancingSettings.builder()
                        .creditor(creditor3)
                        .annualRateInBps(45)
                        .purchaser(purchaser3)
                        .build());
    }

    @Transactional
    public void seedInvoices() {
        log.info("Seeding the invoices");

        entityManager.persist(Invoice.builder()
                .creditor(creditor1)
                .debtor(debtor1)
                .valueInCents(200000)
                .maturityDate(LocalDate.now().plusDays(52))
                .build());

        entityManager.persist(Invoice.builder()
                .creditor(creditor1)
                .debtor(debtor2)
                .valueInCents(800000)
                .maturityDate(LocalDate.now().plusDays(33))
                .build());

        entityManager.persist(Invoice.builder()
                .creditor(creditor1)
                .debtor(debtor3)
                .valueInCents(600000)
                .maturityDate(LocalDate.now().plusDays(43))
                .build());

        entityManager.persist(Invoice.builder()
                .creditor(creditor1)
                .debtor(debtor1)
                .valueInCents(500000)
                .maturityDate(LocalDate.now().plusDays(80))
                .build());

        entityManager.persist(Invoice.builder()
                .creditor(creditor1)
                .debtor(debtor2)
                .valueInCents(6000000)
                .maturityDate(LocalDate.now().plusDays(5))
                .build());

        entityManager.persist(Invoice.builder()
                .creditor(creditor2)
                .debtor(debtor3)
                .valueInCents(500000)
                .maturityDate(LocalDate.now().plusDays(10))
                .build());

        entityManager.persist(Invoice.builder()
                .creditor(creditor2)
                .debtor(debtor1)
                .valueInCents(800000)
                .maturityDate(LocalDate.now().plusDays(15))
                .build());

        entityManager.persist(Invoice.builder()
                .creditor(creditor2)
                .debtor(debtor2)
                .valueInCents(9000000)
                .maturityDate(LocalDate.now().plusDays(30))
                .build());

        entityManager.persist(Invoice.builder()
                .creditor(creditor2)
                .debtor(debtor3)
                .valueInCents(450000)
                .maturityDate(LocalDate.now().plusDays(32))
                .build());

        entityManager.persist(Invoice.builder()
                .creditor(creditor2)
                .debtor(debtor1)
                .valueInCents(800000)
                .maturityDate(LocalDate.now().plusDays(11))
                .build());

        entityManager.persist(Invoice.builder()
                .creditor(creditor3)
                .debtor(debtor2)
                .valueInCents(3000000)
                .maturityDate(LocalDate.now().plusDays(10))
                .build());

        entityManager.persist(Invoice.builder()
                .creditor(creditor3)
                .debtor(debtor3)
                .valueInCents(5000000)
                .maturityDate(LocalDate.now().plusDays(14))
                .build());

        entityManager.persist(Invoice.builder()
                .creditor(creditor3)
                .debtor(debtor1)
                .valueInCents(9000000)
                .maturityDate(LocalDate.now().plusDays(23))
                .build());

        entityManager.persist(Invoice.builder()
                .creditor(creditor3)
                .debtor(debtor2)
                .valueInCents(800000)
                .maturityDate(LocalDate.now().plusDays(18))
                .build());

        entityManager.persist(Invoice.builder()
                .creditor(creditor3)
                .debtor(debtor3)
                .valueInCents(9000000)
                .maturityDate(LocalDate.now().plusDays(50))
                .build());
    }

}
