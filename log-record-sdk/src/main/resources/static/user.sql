-- auto-generated definition
create table user
(
    id        bigint unsigned auto_increment
        primary key,
    name      varchar(20) not null,
    gender    char        not null,
    user_type varchar(2)  not null
);

