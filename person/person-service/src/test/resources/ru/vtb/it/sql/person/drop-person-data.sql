delete from contacts;
delete from documents;

delete from persons_addresses;
delete from addresses;
delete from persons;

ALTER SEQUENCE if exists persons_addresses_id_seq RESTART WITH 1;
ALTER SEQUENCE if exists persons_id_seq RESTART WITH 1;
ALTER SEQUENCE if exists addresses_id_seq RESTART WITH 1;
ALTER SEQUENCE if exists contacts_id_seq RESTART WITH 1;
ALTER SEQUENCE if exists documents_id_seq RESTART WITH 1;