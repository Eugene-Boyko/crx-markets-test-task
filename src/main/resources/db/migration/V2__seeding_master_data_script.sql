insert into creditor (id, name, max_financing_rate_in_bps) values (1, 'Coffee Beans LLC', 5);
insert into creditor (id, name, max_financing_rate_in_bps) values (2, 'Home Brew', 3);
insert into creditor (id, name, max_financing_rate_in_bps) values (3, 'Beanstalk', 2);

insert into debtor (id, name) values (1, 'Chocolate Factory');
insert into debtor (id, name) values (2, 'Sweets Inc');
insert into debtor (id, name) values (3, 'ChocoLoco');

insert into purchaser (id, name) values (1, 'RichBank');
insert into purchaser (id, name) values (2, 'FatBank');
insert into purchaser (id, name) values (3, 'MegaBank');

insert into purchaser_financing_settings (id, creditor_id, annual_rate_in_bps, purchaser_id, minimum_financing_term_in_days) values (1, 1, 50, 1, 10);
insert into purchaser_financing_settings (id, creditor_id, annual_rate_in_bps, purchaser_id, minimum_financing_term_in_days) values (2, 2, 60, 1, 10);
insert into purchaser_financing_settings (id, creditor_id, annual_rate_in_bps, purchaser_id, minimum_financing_term_in_days) values (3, 3, 30, 1, 10);

insert into purchaser_financing_settings (id, creditor_id, annual_rate_in_bps, purchaser_id, minimum_financing_term_in_days) values (4, 1, 40, 2, 12);
insert into purchaser_financing_settings (id, creditor_id, annual_rate_in_bps, purchaser_id, minimum_financing_term_in_days) values (5, 2, 80, 2, 12);
insert into purchaser_financing_settings (id, creditor_id, annual_rate_in_bps, purchaser_id, minimum_financing_term_in_days) values (6, 3, 25, 2, 12);

insert into purchaser_financing_settings (id, creditor_id, annual_rate_in_bps, purchaser_id, minimum_financing_term_in_days) values (7, 1, 30, 3, 8);
insert into purchaser_financing_settings (id, creditor_id, annual_rate_in_bps, purchaser_id, minimum_financing_term_in_days) values (8, 2, 50, 3, 8);
insert into purchaser_financing_settings (id, creditor_id, annual_rate_in_bps, purchaser_id, minimum_financing_term_in_days) values (9, 3, 45, 3, 8);