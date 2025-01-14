INSERT INTO Creditor (id, name, maxFinancingRateInBps) VALUES (1, 'Coffee Beans LLC', 5);
INSERT INTO Creditor (id, name, maxFinancingRateInBps) VALUES (2, 'Home Brew', 3);
INSERT INTO Creditor (id, name, maxFinancingRateInBps) VALUES (3, 'Beanstalk', 2);

INSERT INTO Debtor (id, name) VALUES (1, 'Chocolate Factory');
INSERT INTO Debtor (id, name) VALUES (2, 'Sweets Inc');
INSERT INTO Debtor (id, name) VALUES (3, 'ChocoLoco');

INSERT INTO Purchaser (id, name, minimumFinancingTermInDays) VALUES (1, 'RichBank', 10);
INSERT INTO Purchaser (id, name, minimumFinancingTermInDays) VALUES (2, 'FatBank', 12);
INSERT INTO Purchaser (id, name, minimumFinancingTermInDays) VALUES (3, 'MegaBank', 8);

INSERT INTO PurchaserFinancingSettings (id, creditorId, annualRateInBps, purchaserId) VALUES (1, 1, 50, 1);
INSERT INTO PurchaserFinancingSettings (id, creditorId, annualRateInBps, purchaserId) VALUES (2, 2, 60, 1);
INSERT INTO PurchaserFinancingSettings (id, creditorId, annualRateInBps, purchaserId) VALUES (3, 3, 30, 1);

INSERT INTO PurchaserFinancingSettings (id, creditorId, annualRateInBps, purchaserId) VALUES (4, 1, 40, 2);
INSERT INTO PurchaserFinancingSettings (id, creditorId, annualRateInBps, purchaserId) VALUES (5, 2, 80, 2);
INSERT INTO PurchaserFinancingSettings (id, creditorId, annualRateInBps, purchaserId) VALUES (6, 3, 25, 2);

INSERT INTO PurchaserFinancingSettings (id, creditorId, annualRateInBps, purchaserId) VALUES (7, 1, 30, 3);
INSERT INTO PurchaserFinancingSettings (id, creditorId, annualRateInBps, purchaserId) VALUES (8, 2, 50, 3);
INSERT INTO PurchaserFinancingSettings (id, creditorId, annualRateInBps, purchaserId) VALUES (9, 3, 45, 3);