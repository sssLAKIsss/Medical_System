insert into addresses (type, country, region, city, street, home, visibility, version)
    VALUES ('REGISTRATION', 'Russia', 'RB', 'UFA', 'GOGOl9', '106', false, 0);

insert into addresses (type, country, region, city, street, home, flat, visibility, version)
    VALUES ('REGISTRATION', 'Russia', '123', '123', '123', '111', '111', true, 0);

insert into persons (last_name, first_name, patronymic, version, visibility)
    VALUES ('firstname', 'lastname', 'patronymic', 0, true);

insert into persons_addresses (persons_id, addresses_id)
    VALUES (1, 1),
           (1, 2);