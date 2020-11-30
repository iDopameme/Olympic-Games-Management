create table participants
(
    playerID    int          not null
        primary key,
    firstName   varchar(25)  null,
    lastName    varchar(25)  null,
    age         int          null,
    country     varchar(100) null,
    playerSport varchar(25)  null
);

create index participants_countries_cAbbrev_fk
    on participants (country);