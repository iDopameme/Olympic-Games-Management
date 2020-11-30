create table `Match`
(
    id            int auto_increment
        primary key,
    tournament_id int         null,
    team_a_score  int         null,
    team_b_score  int         null,
    date          timestamp   null,
    status        varchar(50) null,
    team_a_id     int         null,
    team_b_id     int         null,
    constraint Match_Team_id_fk
        foreign key (team_a_id) references Team (id),
    constraint Match_Team_id_fk_2
        foreign key (team_b_id) references Team (id),
    constraint Match_Tournament_id_fk
        foreign key (tournament_id) references Tournament (id)
);