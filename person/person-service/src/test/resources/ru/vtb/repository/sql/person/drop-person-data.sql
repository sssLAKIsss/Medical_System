ALTER sequence if exists persons_id_seq restart with 1;
ALTER sequence if exists documents_id_seq restart with 1;
delete from documents;
delete from persons;
