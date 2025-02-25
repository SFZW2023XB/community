create table question
(
    id            int auto_increment,
    title         varchar(50),
    description   text,
    gmt_create    BIGINT,
    gmt_modified  BIGINT,
    creator       INT,
    comment_count INT default 0,
    like_count    INT default 0,
    view_count    INT default 0,
    tag           VARCHAR(256),
    constraint question_pk
        primary key (id)
);