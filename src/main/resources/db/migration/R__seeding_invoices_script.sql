-- ${flyway:timestamp}
merge into invoice (id, creditor_id, debtor_id, maturity_date, value_in_cents) values (1, 1, 1, current_date + 52, 200000);
merge into invoice (id, creditor_id, debtor_id, maturity_date, value_in_cents) values (2, 1, 2, current_date + 33, 800000);
merge into invoice (id, creditor_id, debtor_id, maturity_date, value_in_cents) values (3, 1, 3, current_date + 43, 600000);
merge into invoice (id, creditor_id, debtor_id, maturity_date, value_in_cents) values (4, 1, 1, current_date + 80, 500000);
merge into invoice (id, creditor_id, debtor_id, maturity_date, value_in_cents) values (5, 1, 2, current_date + 5, 6000000);
merge into invoice (id, creditor_id, debtor_id, maturity_date, value_in_cents) values (6, 2, 3, current_date + 10, 500000);
merge into invoice (id, creditor_id, debtor_id, maturity_date, value_in_cents) values (7, 2, 1, current_date + 15, 800000);
merge into invoice (id, creditor_id, debtor_id, maturity_date, value_in_cents) values (8, 2, 2, current_date + 30, 9000000);
merge into invoice (id, creditor_id, debtor_id, maturity_date, value_in_cents) values (9, 2, 3, current_date + 32, 450000);
merge into invoice (id, creditor_id, debtor_id, maturity_date, value_in_cents) values (10, 2, 1, current_date + 11, 800000);
merge into invoice (id, creditor_id, debtor_id, maturity_date, value_in_cents) values (11, 3, 2, current_date + 10, 3000000);
merge into invoice (id, creditor_id, debtor_id, maturity_date, value_in_cents) values (12, 3, 3, current_date + 14, 5000000);
merge into invoice (id, creditor_id, debtor_id, maturity_date, value_in_cents) values (13, 3, 1, current_date + 23, 9000000);
merge into invoice (id, creditor_id, debtor_id, maturity_date, value_in_cents) values (14, 3, 2, current_date + 18, 800000);
merge into invoice (id, creditor_id, debtor_id, maturity_date, value_in_cents) values (15, 3, 3, current_date + 50, 9000000);