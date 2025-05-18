--liquibase formatted sql

--changeset guronas:init-2
create type archetype as enum ('SWORDSMAN', 'CAVALRY', 'SPEARMAN', 'NEUTRAL');

--changeset guronas:init-3
create table if not exists pve.monster_dictionary (
    id integer generated always as identity primary key,
    name varchar(32) not null unique,
    type archetype not null,
    base_hp integer not null,
    base_atk integer not null,
    base_def integer not null,
    army_name varchar(32) not null unique,
    army_type archetype not null,
    army_base_hp integer not null,
    army_base_quantity integer not null,
    army_base_atk integer not null,
    army_base_def integer not null,
    army_tier integer not null
);
