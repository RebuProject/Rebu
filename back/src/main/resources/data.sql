create table member (
    id bigint not null auto_increment,
    email varchar(32) not null  unique
)