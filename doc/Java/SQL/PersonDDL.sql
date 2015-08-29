
-- Search and replace XXX with yourUserName before running this script. Also
-- you'll need to make the corresponding change in PersonDAO.java.

DROP TABLE apps.XXX_angularcrud cascade constraints;
DROP SEQUENCE apps.XXX_angularcrud_seq;

CREATE TABLE apps.XXX_angularcrud (
    id       number(10) PRIMARY KEY,
    firstName    varchar2(255) not null,
    lastName     varchar2(255) not null
);

CREATE SEQUENCE apps.XXX_angularcrud_seq
    MINVALUE 1
    MAXVALUE 999999999999999999999999999
    START WITH 1
    INCREMENT BY 1
    CACHE 20;

insert into apps.XXX_angularcrud (id, firstName, lastName) values (apps.XXX_angularcrud_seq.nextval, 'Fred', 'Flintstone');
insert into apps.XXX_angularcrud (id, firstName, lastName) values (apps.XXX_angularcrud_seq.nextval, 'Wilma', 'Flintstone');
insert into apps.XXX_angularcrud (id, firstName, lastName) values (apps.XXX_angularcrud_seq.nextval, 'Barney', 'Rubble');
insert into apps.XXX_angularcrud (id, firstName, lastName) values (apps.XXX_angularcrud_seq.nextval, 'Betty', 'Rubble');

commit