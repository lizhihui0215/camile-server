drop table if exists permission;
create table permission
(
  id bigint not null auto_increment comment '权限id'
    primary key,
  permission_name varchar(32) null comment '权限名',
  permission_sign varchar(128) null comment '权限标识,程序中判断使用,如"user:create"',
  description varchar(256) null comment '权限描述,UI界面显示使用'
)
  comment '权限表'
;
drop table if exists role;
create table role
(
  id bigint not null auto_increment comment '角色id'
    primary key,
  role_name varchar(32) null comment '角色名',
  role_sign varchar(128) null comment '角色标识,程序中判断使用,如"admin"',
  description varchar(256) null comment '角色描述,UI界面显示使用'
)
  comment '角色表'
;

drop table if exists role_permission;
create table role_permission
(
  id bigint not null auto_increment comment '表id'
    primary key,
  role_id bigint null comment '角色id',
  permission_id bigint null comment '权限id'
)
  comment '角色与权限关联表'
;

drop table if exists user;
create table user
(
  id bigint not null auto_increment comment '用户id'
    primary key,
  username varchar(255) not null comment '用户名',
  password varchar(255) not null comment '密码',
  name varchar(255) not null comment '姓名',
  nickname varchar(255) not null comment '昵称',
  brithday date not null comment '生日',
  email varchar(255) not null comment '邮件',
  phone varchar(255) not null comment '电话',
  state varchar(32) null comment '状态',
  address varchar(255) null comment '家庭住址',
  create_time datetime null comment '创建时间',
  constraint user_username_uindex
  unique (username)
)
  comment '用户表'
;

drop table if exists user_role;
create table user_role
(
  id bigint not null auto_increment comment '表id'
    primary key,
  user_id bigint null comment '用户id',
  role_id bigint null comment '角色id'
)
  comment '用户与角色关联表'
;

insert  into `permission`(`id`,`permission_name`,`permission_sign`,`description`) values (1,'用户新增','user:create',NULL);
insert  into `role`(`id`,`role_name`,`role_sign`,`description`) values (1,'admin','admin','管理员');
insert  into `role_permission`(`id`,`role_id`,`permission_id`) values (1,2,1);
INSERT INTO user (id, username, password, name, nickname, brithday, email, phone,address, state, create_time) VALUES (1,'lizhihui0215','dsn4cgwy','李智慧', 'just', now(),'lizhihui0215@gmail.com','18710427678','陕西省，西安市，灞桥区，矿山路','1', now());
insert  into `user_role`(`id`,`user_id`,`role_id`) values (1,1,1);
