create table comment
(
    id           BIGINT auto_increment,
    parent_id    BIGINT not null,
    type         INT    not null,
    commentator  INT    not null,
    gmt_create   BIGINT not null,
    gmt_modified BIGINT not null,
    like_count   INTEGER,
    constraint comment_pk
        primary key (id)
);

