create table if not exists Creditor(
    `id` bigint not null auto_increment primary key,
    `name` varchar not null,
    `maxFinancingRateInBps` int not null
);
create table if not exists Debtor(
    `id` bigint not null auto_increment primary key,
    `name` varchar not null
);
create table if not exists Invoice(
    `id` bigint not null auto_increment primary key,
    `maturityDate` date not null,
    `valueInCents` bigint not null,
    `creditorId` bigint not null,
    `debtorId` bigint,
    `processed` boolean not null default false,
    foreign key (`creditorId`) references Creditor(id),
    foreign key (`debtorId`) references Debtor(id)
);
create table if not exists Purchaser(
    `id` bigint not null auto_increment primary key,
    `name` varchar not null,
    `minimumFinancingTermInDays` varchar not null
);
create table if not exists PurchaserFinancingSettings(
    `id` bigint not null auto_increment primary key,
    `creditorId` bigint not null,
    `annualRateInBps` int not null,
    `purchaserId` bigint not null,
    foreign key (`creditorId`) references Creditor(id),
    foreign key (`purchaserId`) references Purchaser(id)
);