use eazybank;

create table customer (
    id int not null auto_increment,
    email varchar(45) not null,
    pwd varchar(200) not null,
    role varchar(45) not null,
    primary key (id)
);

insert into customer (email, pwd, role) values ('aaa@bbb.ccc', '1234', 'admin');