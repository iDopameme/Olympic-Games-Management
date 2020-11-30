create table Tournament
(
    id              int         not null
        primary key,
    tournament_name varchar(50) null,
    tournament_type varchar(50) null,
    status          varchar(50) not null
);