create table user
(
  id           int(255) auto_increment
    primary key,
  account_Id   varchar(100) null,
  name         varchar(50)  null,
  gmt_create   bigint(255)  null,
  gmt_modified bigint(255)  null,
  token        varchar(36)  null,
  avatar_url   varchar(255) null
);

