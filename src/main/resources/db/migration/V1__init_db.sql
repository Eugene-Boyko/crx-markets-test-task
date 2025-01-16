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
    `name` varchar not null,
    `minimum_financing_term_in_days` varchar not null
);
create table if not exists purchaser_financing_settings(
    `id` bigint not null auto_increment primary key,
    `creditor_id` bigint not null,
    `annual_rate_in_bps` int not null,
    `purchaser_id` bigint not null,
    foreign key (`creditor_id`) references creditor(id),
    foreign key (`purchaser_id`) references purchaser(id)
);