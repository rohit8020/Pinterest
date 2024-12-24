drop schema `pinterest-business`;
create schema `businessservice`;
use `businessservice`;

create table business(
id BIGINT auto_increment primary key,
user_id BIGINT,
comment_id BIGINT
);

select * from business;

