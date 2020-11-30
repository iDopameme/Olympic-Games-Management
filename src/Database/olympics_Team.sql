create table Team
(
    id            int          not null
        primary key,
    team_name     varchar(100) not null,
    tournament_id int          not null,
    constraint Team_Tournament_id_fk
        foreign key (tournament_id) references Tournament (id)
);
