create table Match_Knockout
(
    match_id    int not null,
    winner_a_of int not null,
    winner_b_of int not null,
    constraint Match_Knockout_Match_id_fk
        foreign key (match_id) references `Match` (id),
    constraint Match_Knockout_Match_id_fk_2
        foreign key (winner_a_of) references `Match` (id),
    constraint Match_Knockout_Match_id_fk_3
        foreign key (winner_b_of) references `Match` (id)
);

