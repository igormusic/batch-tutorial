create table TRADE.COFFEE
(
    coffee_id bigint identity
        constraint coffee_pk
        primary key nonclustered,
    brand nvarchar(20) not null,
    origin nvarchar(20) not null,
    characteristics nvarchar(30) not null
);
