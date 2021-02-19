create table employee
(
	id serial not null
		constraint employee_pkey
			primary key,
	email varchar(100) not null
		constraint employee_uniquekey_email
			unique,
	first_name varchar(100) not null,
	last_name  varchar(100) not null
);
