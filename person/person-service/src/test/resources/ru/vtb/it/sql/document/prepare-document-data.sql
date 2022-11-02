INSERT INTO persons (first_name, last_name, patronymic, visibility, version, last_modified_date, date_time_creation)
    VALUES ('firstname', 'lastname', 'patronymic', TRUE, 0, '2022-10-25T14:04:05.995161', '2022-10-25T14:04:05.995161');

INSERT INTO documents (type, number, person_id, visibility, version, last_modified_date, date_time_creation)
    VALUES ('PASSPORT', '4545001004', 1, true, 0, '2022-05-30T20:04:34.972264', '2022-05-30T20:04:34.972264'),
           ('PASSPORT', '4545001005', 1, true, 0, '2022-05-30T20:04:34.975264', '2022-05-30T20:04:34.975264');