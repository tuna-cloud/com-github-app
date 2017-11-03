/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/9/23 9:50:59                            */
/*==============================================================*/


drop table if exists T_DEVICE;

drop table if exists T_FILE;

drop table if exists T_MENU;

drop table if exists T_OPERATION;

drop table if exists T_PAGE;

drop table if exists T_POPEDOM;

drop table if exists T_PRODUCT;

drop table if exists T_REL_DEVICE_USER;

drop table if exists T_REL_POPEDOM_FILE;

drop table if exists T_REL_POPEDOM_MENU;

drop table if exists T_REL_POPEDOM_OPERATION;

drop table if exists T_REL_POPEDOM_PAGE;

drop table if exists T_REL_PRODUCT_USER;

drop table if exists T_REL_ROLE_POPEDOM;

drop table if exists T_REL_ROLE_USER;

drop table if exists T_ROLE;

drop table if exists T_TOPIC;

drop table if exists T_USER;

/*==============================================================*/
/* Table: T_DEVICE                                              */
/*==============================================================*/
create table T_DEVICE
(
   number               int not null auto_increment,
   secret               varchar(50),
   remark               varchar(60),
   product_number       int,
   primary key (number)
);

/*==============================================================*/
/* Table: T_FILE                                                */
/*==============================================================*/
create table T_FILE
(
   number               int not null auto_increment,
   name                 varchar(50),
   path                 varchar(80),
   primary key (number)
);

/*==============================================================*/
/* Table: T_MENU                                                */
/*==============================================================*/
create table T_MENU
(
   number               int not null auto_increment,
   url                  varchar(100),
   name                 varchar(50),
   parent_number        int,
   primary key (number)
);

/*==============================================================*/
/* Table: T_OPERATION                                           */
/*==============================================================*/
create table T_OPERATION
(
   number               int not null auto_increment,
   name                 varchar(50),
   code                 varchar(50),
   filter_url           varchar(80),
   parent_number        int,
   primary key (number)
);

/*==============================================================*/
/* Table: T_PAGE                                                */
/*==============================================================*/
create table T_PAGE
(
   number               int not null auto_increment,
   name                 varchar(50),
   primary key (number)
);

/*==============================================================*/
/* Table: T_POPEDOM                                             */
/*==============================================================*/
create table T_POPEDOM
(
   number               int not null auto_increment,
   type                 varchar(30),
   primary key (number)
);

/*==============================================================*/
/* Table: T_PRODUCT                                             */
/*==============================================================*/
create table T_PRODUCT
(
   number               int not null auto_increment,
   code                 varchar(50),
   name                 varchar(50),
   primary key (number)
);

/*==============================================================*/
/* Table: T_REL_DEVICE_USER                                     */
/*==============================================================*/
create table T_REL_DEVICE_USER
(
   device_number        int,
   user_number          int
);

/*==============================================================*/
/* Table: T_REL_POPEDOM_FILE                                    */
/*==============================================================*/
create table T_REL_POPEDOM_FILE
(
   popedom_number       int,
   file_number          int
);

/*==============================================================*/
/* Table: T_REL_POPEDOM_MENU                                    */
/*==============================================================*/
create table T_REL_POPEDOM_MENU
(
   popedom_number       int,
   menu_number          int
);

/*==============================================================*/
/* Table: T_REL_POPEDOM_OPERATION                               */
/*==============================================================*/
create table T_REL_POPEDOM_OPERATION
(
   popedom_number       int,
   op_number            int
);

/*==============================================================*/
/* Table: T_REL_POPEDOM_PAGE                                    */
/*==============================================================*/
create table T_REL_POPEDOM_PAGE
(
   popedom_number       int,
   page_number          int
);

/*==============================================================*/
/* Table: T_REL_PRODUCT_USER                                    */
/*==============================================================*/
create table T_REL_PRODUCT_USER
(
   product_number       int,
   user_number          int
);

/*==============================================================*/
/* Table: T_REL_ROLE_POPEDOM                                    */
/*==============================================================*/
create table T_REL_ROLE_POPEDOM
(
   role_number          int,
   popedom_number       int
);

/*==============================================================*/
/* Table: T_REL_ROLE_USER                                       */
/*==============================================================*/
create table T_REL_ROLE_USER
(
   role_number          int,
   user_number          int
);

/*==============================================================*/
/* Table: T_ROLE                                                */
/*==============================================================*/
create table T_ROLE
(
   number               int not null auto_increment,
   name                 varchar(50),
   primary key (number)
);

/*==============================================================*/
/* Table: T_TOPIC                                               */
/*==============================================================*/
create table T_TOPIC
(
   number               int not null auto_increment,
   name                 varchar(60),
   popedom              int,
   remark               varchar(100),
   device_number        int,
   primary key (number)
);

/*==============================================================*/
/* Table: T_USER                                                */
/*==============================================================*/
create table T_USER
(
   number               int not null auto_increment,
   account              varchar(50),
   password             varchar(50),
   name                 varchar(50),
   sex                  varchar(20),
   email                varchar(50),
   mobile               varchar(30),
   is_enable            smallint default 0,
   address              varchar(100),
   photo_url            varchar(100),
   remark               text,
   parent_number        int,
   primary key (number),
   key AK_MOBILE (mobile),
   key AK_EMAIL (email),
   key AK_ACCOUNT (account)
);

alter table T_DEVICE add constraint FK_Reference_18 foreign key (product_number)
      references T_PRODUCT (number) on delete restrict on update restrict;

alter table T_REL_DEVICE_USER add constraint FK_Reference_20 foreign key (device_number)
      references T_DEVICE (number) on delete restrict on update restrict;

alter table T_REL_DEVICE_USER add constraint FK_Reference_21 foreign key (user_number)
      references T_USER (number) on delete restrict on update restrict;

alter table T_REL_POPEDOM_FILE add constraint FK_Reference_14 foreign key (file_number)
      references T_FILE (number) on delete restrict on update restrict;

alter table T_REL_POPEDOM_FILE add constraint FK_Reference_15 foreign key (popedom_number)
      references T_POPEDOM (number) on delete restrict on update restrict;

alter table T_REL_POPEDOM_MENU add constraint FK_Reference_10 foreign key (menu_number)
      references T_MENU (number) on delete restrict on update restrict;

alter table T_REL_POPEDOM_MENU add constraint FK_Reference_11 foreign key (popedom_number)
      references T_POPEDOM (number) on delete restrict on update restrict;

alter table T_REL_POPEDOM_OPERATION add constraint FK_Reference_16 foreign key (op_number)
      references T_OPERATION (number) on delete restrict on update restrict;

alter table T_REL_POPEDOM_OPERATION add constraint FK_Reference_17 foreign key (popedom_number)
      references T_POPEDOM (number) on delete restrict on update restrict;

alter table T_REL_POPEDOM_PAGE add constraint FK_Reference_12 foreign key (page_number)
      references T_PAGE (number) on delete restrict on update restrict;

alter table T_REL_POPEDOM_PAGE add constraint FK_Reference_13 foreign key (popedom_number)
      references T_POPEDOM (number) on delete restrict on update restrict;

alter table T_REL_PRODUCT_USER add constraint FK_Reference_22 foreign key (product_number)
      references T_PRODUCT (number) on delete restrict on update restrict;

alter table T_REL_PRODUCT_USER add constraint FK_Reference_23 foreign key (user_number)
      references T_USER (number) on delete restrict on update restrict;

alter table T_REL_ROLE_POPEDOM add constraint FK_Reference_8 foreign key (role_number)
      references T_ROLE (number) on delete restrict on update restrict;

alter table T_REL_ROLE_POPEDOM add constraint FK_Reference_9 foreign key (popedom_number)
      references T_POPEDOM (number) on delete restrict on update restrict;

alter table T_REL_ROLE_USER add constraint FK_Reference_6 foreign key (user_number)
      references T_USER (number) on delete restrict on update restrict;

alter table T_REL_ROLE_USER add constraint FK_Reference_7 foreign key (role_number)
      references T_ROLE (number) on delete restrict on update restrict;

alter table T_TOPIC add constraint FK_Reference_19 foreign key (device_number)
      references T_DEVICE (number) on delete restrict on update restrict;

