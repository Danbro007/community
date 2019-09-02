create table question
(
  id            bigint auto_increment
    primary key,
  title         varchar(50)             not null,
  description   text                    not null,
  gmt_create    bigint(255)             not null,
  gmt_modified  bigint(255)             not null,
  creator       int(255)                not null,
  comment_count bigint(255) default '0' null,
  view_count    bigint(255) default '0' null,
  like_count    bigint(255) default '0' null,
  tag           varchar(255)            null
);

