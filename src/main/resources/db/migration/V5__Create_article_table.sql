-- auto-generated definition
create table article
(
  id            bigint auto_increment
    primary key,
  title         varchar(255)    not null,
  content       text            null,
  author        varchar(255)    null,
  comment_count int default '0' null,
  view_count    int default '0' null,
  gmt_create    bigint          not null,
  gmt_modify    bigint          not null
);

