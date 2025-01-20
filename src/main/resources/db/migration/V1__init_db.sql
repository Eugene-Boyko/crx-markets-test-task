create table if not exists creditor(
    `id` bigint not null auto_increment primary key,
    `name` varchar not null,
    `max_financing_rate_in_bps` int not null
);
create table if not exists debtor(
    `id` bigint not null auto_increment primary key,
    `name` varchar not null
);
create table if not exists invoice(
    `id` bigint not null auto_increment primary key,
    `maturity_date` date not null,
    `value_in_cents` bigint not null,
    `creditor_id` bigint not null,
    `debtor_id` bigint,
    `processed` boolean not null default false,
    foreign key (`creditor_id`) references creditor(id),
    foreign key (`debtor_id`) references debtor(id)
);
create table if not exists purchaser(
    `id` bigint not null auto_increment primary key,
    `name` varchar not null
);
create table if not exists purchaser_financing_settings(
    `id` bigint not null auto_increment primary key,
    `creditor_id` bigint not null,
    `annual_rate_in_bps` int not null,
    `purchaser_id` bigint not null,
    `minimum_financing_term_in_days` int not null,
    foreign key (`creditor_id`) references creditor(id),
    foreign key (`purchaser_id`) references purchaser(id)
);
create table if not exists financing_results(
    `id` bigint not null auto_increment primary key,
    `financing_term` int not null,
    `financing_rate` int not null,
    `financing_date` date not null,
    `early_payment_amount` bigint not null,
    `invoice_id` bigint not null,
    `purchaser_financing_settings_id` bigint,
    foreign key (`invoice_id`) references invoice(id),
    foreign key (`purchaser_financing_settings_id`) references purchaser_financing_settings(id)
);