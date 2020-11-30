create table leaderboards
(
    countryID int not null
        primary key,
    gold      int null,
    silver    int null,
    bronze    int null,
    constraint leaderboards_countries_ID_fk
        foreign key (countryID) references countries (ID)
);