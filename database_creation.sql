create database spring_security_homework;
use spring_security_homework;

create table users (
	id int auto_increment primary key,   
    email nvarchar(50) not null unique,
    password nvarchar(100) not null,
    first_name nvarchar(50) not null,
    role nvarchar(10) not null
);

-- select * from users;
-- select * from spring_session;
-- select * from spring_session_attributes;

-- delete from spring_session;
-- delete from spring_session_attributes;