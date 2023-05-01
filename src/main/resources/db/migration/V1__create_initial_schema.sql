CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

create table set
(
    id          uuid    not null default uuid_generate_v4(),
    name        varchar not null,
    reps        bigint  not null,
    weight      bigint  not null,
    "order"     int     not null,
    exercise_id uuid    not null,
    primary key (id)
);

create table exercise
(
    id             uuid    not null default uuid_generate_v4(),
    name           varchar not null,
    exercise_order int  not null,
    parent_id      uuid    not null,
    is_superset    boolean not null default false,
    primary key (id)
);

create table workout
(
    id           uuid      not null default uuid_generate_v4(),
    name         varchar   not null,
    date         timestamp not null,
    is_completed boolean   not null default false,
    primary key (id)
);

create table template
(
    id   uuid    not null default uuid_generate_v4(),
    name varchar not null,
    primary key (id)
);

alter table set
    add constraint set_exercise_id_fk
        foreign key (exercise_id) references exercise;
