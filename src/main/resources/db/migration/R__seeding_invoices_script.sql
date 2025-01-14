-- ${flyway:timestamp}
MERGE INTO Invoice (id, creditorId, debtorId, maturityDate, valueInCents) VALUES (1, 1, 1, current_date + 52, 200000);
MERGE INTO Invoice (id, creditorId, debtorId, maturityDate, valueInCents) VALUES (2, 1, 2, current_date + 33, 800000);
MERGE INTO Invoice (id, creditorId, debtorId, maturityDate, valueInCents) VALUES (3, 1, 3, current_date + 43, 600000);
MERGE INTO Invoice (id, creditorId, debtorId, maturityDate, valueInCents) VALUES (4, 1, 1, current_date + 80, 500000);
MERGE INTO Invoice (id, creditorId, debtorId, maturityDate, valueInCents) VALUES (5, 1, 2, current_date + 5, 6000000);
MERGE INTO Invoice (id, creditorId, debtorId, maturityDate, valueInCents) VALUES (6, 2, 3, current_date + 10, 500000);
MERGE INTO Invoice (id, creditorId, debtorId, maturityDate, valueInCents) VALUES (7, 2, 1, current_date + 15, 800000);
MERGE INTO Invoice (id, creditorId, debtorId, maturityDate, valueInCents) VALUES (8, 2, 2, current_date + 30, 9000000);
MERGE INTO Invoice (id, creditorId, debtorId, maturityDate, valueInCents) VALUES (9, 2, 3, current_date + 32, 450000);
MERGE INTO Invoice (id, creditorId, debtorId, maturityDate, valueInCents) VALUES (10, 2, 1, current_date + 11, 800000);
MERGE INTO Invoice (id, creditorId, debtorId, maturityDate, valueInCents) VALUES (11, 3, 2, current_date + 10, 3000000);
MERGE INTO Invoice (id, creditorId, debtorId, maturityDate, valueInCents) VALUES (12, 3, 3, current_date + 14, 5000000);
MERGE INTO Invoice (id, creditorId, debtorId, maturityDate, valueInCents) VALUES (13, 3, 1, current_date + 23, 9000000);
MERGE INTO Invoice (id, creditorId, debtorId, maturityDate, valueInCents) VALUES (14, 3, 2, current_date + 18, 800000);
MERGE INTO Invoice (id, creditorId, debtorId, maturityDate, valueInCents) VALUES (15, 3, 3, current_date + 50, 9000000);