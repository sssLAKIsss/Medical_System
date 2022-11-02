delete from documents;
delete from persons;

ALTER SEQUENCE if exists persons_id_seq RESTART WITH 1;
ALTER SEQUENCE if exists documents_id_seq RESTART WITH 1;