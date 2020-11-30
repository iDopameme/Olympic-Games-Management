create table countries
(
    ID      int          not null
        primary key,
    cName   varchar(100) null,
    cAbbrev varchar(2)   null,
    constraint cAbbrev_UNIQUE
        unique (cAbbrev)
);