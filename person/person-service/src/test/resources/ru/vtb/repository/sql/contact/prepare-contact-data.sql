insert into contacts (type, phone_number, visibility, version)
    VALUES ('HOME', '+79152734431', true, 0);
insert into persons (last_name, first_name, patronymic, version, visibility)
    VALUES ('lastname', 'firstname', 'patronymic', 0, true);
insert into contacts (type, phone_number, person_id, visibility, version)
    VALUES ('HOME', '+12345678901', 1, false, 0);