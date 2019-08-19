create table notification
(
  id           bigint auto_increment
    primary key,
  notifier     int          not null,
  reciever     int          not null,
  type         int          not null,
  gmt_create   bigint       not null,
  status       int          not null,
  outer_id     bigint       not null,
  outer_tittle varchar(256) not null
);