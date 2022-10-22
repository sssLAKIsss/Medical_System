ALTER sequence if exists contacts_id_seq restart with 1;
ALTER sequence if exists persons_id_seq restart with 1;
delete from contacts;
delete from persons;