insert into persons (last_name, first_name, patronymic, version, visibility)
VALUES ('lastname', 'firstname', 'patronymic', 0, false);
insert into persons (last_name, first_name, patronymic, version, visibility)
VALUES ('lastname', 'firstname', 'patronymic', 0, true);

insert into documents (type, number, person_id, version, visibility)
    VALUES ('PASSPORT', '4545001001', 1, 0, true);
