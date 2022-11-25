INSERT INTO persons (first_name, last_name, patronymic, visibility, version, last_modified_date, date_time_creation)
    VALUES ('firstname', 'lastname', 'patronymic', TRUE, 0, '2022-10-25T14:04:05.995161', '2022-10-25T14:04:05.995161');

INSERT INTO contacts (type, phone_number, person_id, visibility, version, last_modified_date, date_time_creation)
    VALUES ('HOME','+79999999999', 1, TRUE, 0, '2022-10-25T14:04:05.995161', '2022-10-25T14:04:05.995161'),
           ('HOME','+79999999991', 1, TRUE, 0, '2022-10-27T14:22:32.944572', '2022-10-27T14:22:32.944572');