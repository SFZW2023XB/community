alter table NOTIFICATION
    add notifier_name VARCHAR(100) not null;

alter table NOTIFICATION
    add outer_title varchar(256) not null;