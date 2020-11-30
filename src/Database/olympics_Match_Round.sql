create table Match_Round
(
    match_id  int not null,
    team_a_id int not null,
    team_b_id int not null,
    constraint Match_Round_Match_id_fk
        foreign key (match_id) references `Match` (id),
    constraint Match_Round_Team_id_fk
        foreign key (team_a_id) references Team (id),
    constraint Match_Round_Team_id_fk_2
        foreign key (team_b_id) references Team (id)
);

