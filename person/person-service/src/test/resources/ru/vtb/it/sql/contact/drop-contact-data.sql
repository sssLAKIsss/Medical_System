delete from contacts;
delete from persons;

ALTER SEQUENCE if exists persons_id_seq RESTART WITH 1;
ALTER SEQUENCE if exists contacts_id_seq RESTART WITH 1;