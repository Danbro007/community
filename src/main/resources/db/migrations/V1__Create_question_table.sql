-- auto-generated definition
create table question
(
  id            int auto_increment
    primary key,
  title         varchar(50)          null,
  description   varchar(255)         null,
  gmt_create    bigint(255)          null,
  gmt_modified  bigint(255)          null,
  creator       int(255)             null,
  comment_count int(255) default 0 null,
  view_count    int(255) default 0 null,
  like_count    int(255) default 0 null,
  tag           varchar(255)         null
);
