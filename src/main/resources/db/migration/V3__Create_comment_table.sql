create table comment
(
  id            bigint auto_increment
    primary key,
  commenter     int(255)                not null,
  type          int(255)                not null,
  gmt_create    bigint(255)             not null,
  gmt_modified  bigint(255)             not null,
  content       varchar(128)            not null,
  like_count    bigint(255) default '0' null,
  comment_count int(255) default '0'    null,
  parent_id     bigint                  not null
);

