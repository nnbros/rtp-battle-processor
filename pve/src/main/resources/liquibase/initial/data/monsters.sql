--liquibase formatted sql

--changeset guronas:init-4
insert into monster_dictionary (name, type, base_hp, base_atk, base_def, army_name, army_type, army_base_hp,
army_base_quantity, army_base_atk, army_base_def, army_tier)
values
('slime', 'SWORDSMAN', 1000, 200, 10, 'slime', 'SWORDSMAN', 0, 0, 0, 0, 0),
('bandit_leader', 'SWORDSMAN', 450, 100, 50, 'bandits', 'SWORDSMAN', 360, 3, 70, 30, 0);