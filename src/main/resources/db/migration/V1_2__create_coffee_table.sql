create table COFFEE
(
    coffee_id bigint identity
        constraint coffee_pk
        primary key nonclustered,
    brand nvarchar(20) not null,
    origin nvarchar(20) not null,
    characteristics nvarchar(30) not null
);

exec sp_addextendedproperty 'MS_Description', 'Used to demo import', 'SCHEMA', 'dbo', 'TABLE', 'coffee';