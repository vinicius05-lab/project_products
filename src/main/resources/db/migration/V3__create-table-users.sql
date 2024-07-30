create table users(
    id int primary key not null auto_increment,
    name varchar(100) not null,
    email varchar(100) not null,
    password varchar(100) not null,
    role varchar(5) not null
);