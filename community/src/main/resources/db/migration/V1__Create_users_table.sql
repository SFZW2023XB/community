CREATE TABLE USERS(
    id int auto_increment primary key not null ,
    account_id varchar(100),
    name varchar(50),
    token char(36),
    gmt_create BIGINT,
    gmt_modified BIGINT
);