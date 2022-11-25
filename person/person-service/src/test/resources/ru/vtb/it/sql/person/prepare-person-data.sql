INSERT INTO persons (last_name, first_name, patronymic, visibility, version, last_modified_date, date_time_creation)
    VALUES ('Федоров', 'Федор', 'Федорович', true, 0, '2022-10-31T15:52:49.593993', '2022-10-31T15:52:49.593993'),
           ('Федоров1', 'Федор1', 'Федорович1', true, 0, '2022-10-31T15:52:49.593993', '2022-10-31T15:52:49.593993'),
           ('Федоров2', 'Федор2', null, true, 0, '2022-10-31T15:52:49.593993', '2022-10-31T15:52:49.593993');

INSERT INTO documents (type, number, person_id, visibility, version, last_modified_date, date_time_creation)
    VALUES ('PASSPORT', '4545001001', 1, true, 0, '2022-10-31T15:52:49.593993', '2022-10-31T15:52:49.593993'),
           ('PASSPORT', '4545001002', 2, true, 0, '2022-10-31T15:52:49.593993', '2022-10-31T15:52:49.593993'),
           ('PASSPORT', '4545001003', 3, true, 0, '2022-10-31T15:52:49.593993', '2022-10-31T15:52:49.593993'),
           ('SNILS', '4545001004', 1, true, 0, '2022-10-31T15:52:49.593993', '2022-10-31T15:52:49.593993');

INSERT INTO contacts (type, phone_number, person_id, visibility, version, last_modified_date, date_time_creation)
    VALUES ('HOME', '+79999999991', 1, true, 0, '2022-10-31T15:52:49.593993', '2022-10-31T15:52:49.593993'),
           ('HOME', '+79999999992', 1, true, 0, '2022-10-31T15:52:49.593993', '2022-10-31T15:52:49.593993'),
           ('HOME', '+79999999993', 2, true, 0, '2022-10-31T15:52:49.593993', '2022-10-31T15:52:49.593993'),
           ('HOME', '+79999999994', 3, true, 0, '2022-10-31T15:52:49.593993', '2022-10-31T15:52:49.593993');

INSERT INTO addresses (type, country, region, city, street, home, flat, visibility, version, last_modified_date, date_time_creation)
    VALUES ('REGISTRATION', 'Russia', 'RB', 'UFA', 'GOGOl9', '1', '1', true, 0, '2022-10-31T15:52:49.593993', '2022-10-31T15:52:49.593993'),
           ('REGISTRATION', 'Russia', 'RB', 'UFA', 'GOGOl9', '2', '2', true, 0, '2022-10-31T15:52:49.593993', '2022-10-31T15:52:49.593993'),
           ('REGISTRATION', 'Russia', 'RB', 'UFA', 'GOGOl9', '3', '3', true, 0, '2022-10-31T15:52:49.593993', '2022-10-31T15:52:49.593993');

INSERT INTO persons_addresses (persons_id, addresses_id)
    VALUES (1, 1), (2, 2), (3, 3);