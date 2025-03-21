-- IMPORTANT: DO NOT CHANGE THE GIVEN SCHEMA UNLESS YOU HAVE A GOOD REASON
-- IF YOU DO CHANGE IT WRITE THE JUSTIFICATION IN A COMMENT ABOVE THE CHANGE

drop database if exists restaurant;

create database restaurant;

use restaurant;

create table customers (
  username varchar(64) not null,
  password varchar(128) not null,
  -- primary key needed to be called in the other tables
  constraint pk_username primary key (username)
);

insert into customers(username, password) values
  ('fred', sha2('fred', 224)),
  ('barney', sha2('barney', 224)),
  ('wilma', sha2('wilma', 224)),
  ('betty', sha2('betty', 224)),
  ('pebbles', sha2('pebbles', 224));

-- TODO: Task 1.2
-- Write your task 1.2 below

create table place_orders (
	order_id char(8),
    payment_id varchar(128) unique,
    order_date date,
    total decimal(8,2),
    username varchar(64) not null,
    constraint pk_order_id primary key (order_id),
    constraint fk_username foreign key (username) references customers (username) ON DELETE CASCADE ON UPDATE RESTRICT  
)

SELECT * FROM customers;
SELECT * FROM place_orders;
