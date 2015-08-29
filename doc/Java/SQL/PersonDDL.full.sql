
-- Search and replace XXX with yourUserName before running this script. Also
-- you'll need to make the corresponding change in PersonDAO.java.

DROP TABLE apps.XXX_angularcrud cascade constraints;
DROP SEQUENCE apps.XXX_angularcrud_seq;

CREATE TABLE apps.XXX_angularcrud (
    userId       number(10) PRIMARY KEY,
    firstName    varchar2(255) not null,
    lastName     varchar2(255) not null,
    address      varchar2(255),
    city         varchar2(255),
    state        char(2),
    zip          char(5),
    homePhone    char(10),
    workPhone    char(10),
    mobilePhone  char(10),
    email        varchar2(255),
    birthday     date,
    children     integer
);

CREATE SEQUENCE apps.XXX_angularcrud_seq
    MINVALUE 1
    MAXVALUE 999999999999999999999999999
    START WITH 1
    INCREMENT BY 1
    CACHE 20;

insert into apps.XXX_angularcrud (userId, firstName, lastName, address, city, state, zip, homePhone, workPhone, mobilePhone, email, birthday, children)
values (apps.XXX_angularcrud_seq.nextval,
   'Fred', 'Flintstone', '345 Cave Stone Rd', 'Bedrock', 'NA', '123', '1', 'x', 'x', 'fred@bedrock.com', to_date('01/01/1970', 'mm/dd/yyyy'), 1);

insert into apps.XXX_angularcrud (userId, firstName, lastName, address, city, state, zip, homePhone, workPhone, mobilePhone, email, birthday, children)
values (apps.XXX_angularcrud_seq.nextval,
   'Wilma', 'Flintstone', '345 Cave Stone Rd', 'Bedrock', 'NA', '123', '1', 'x', 'x', 'wilma@bedrock.com', to_date('02/01/1970', 'mm/dd/yyyy'), 1);

insert into apps.XXX_angularcrud (userId, firstName, lastName, address, city, state, zip, homePhone, workPhone, mobilePhone, email, birthday, children)
values (apps.XXX_angularcrud_seq.nextval,
   'Barney', 'Rubble', '123 Granite', 'Bedrock', 'NA', '123', '2', 'x', 'x', 'barney@bedrock.com', to_date('03/01/1970', 'mm/dd/yyyy'), 1);

insert into apps.XXX_angularcrud (userId, firstName, lastName, address, city, state, zip, homePhone, workPhone, mobilePhone, email, birthday, children)
values (apps.XXX_angularcrud_seq.nextval,
   'Betty', 'Rubble', '123 Granite', 'Bedrock', 'NA', '123', '2', 'x', 'x', 'betty@bedrock.com', to_date('04/01/1970', 'mm/dd/yyyy'), 1);

commit