create table products(
    id int primary key not null auto_increment,
    name varchar(100) not null,
    description varchar(100) not null,
    price double not null,
    quantity int not null,
    category_id int not null,
    foreign key (category_id) references categories(id) on delete cascade
);