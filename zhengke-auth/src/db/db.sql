/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/8/31 10:31:05                           */
/*==============================================================*/


drop index Index_4 on zhengke_auth;

drop index Index_3 on zhengke_auth;

drop index Index_2 on zhengke_auth;

drop index Index_1 on zhengke_auth;

drop table if exists zhengke_auth;

/*==============================================================*/
/* Table: zhengke_auth                                          */
/*==============================================================*/
create table zhengke_auth
(
   id                   int not null,
   phoneId              varchar(64),
   token                varchar(32),
   updateTime           datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   createTime           datetime default CURRENT_TIMESTAMP,
   expireTime           datetime comment 'token 有效时间',
   primary key (id)
);

alter table zhengke_auth comment '存储验证信息';

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create unique index Index_1 on zhengke_auth
(
   id
);

/*==============================================================*/
/* Index: Index_2                                               */
/*==============================================================*/
create index Index_2 on zhengke_auth
(
   phoneId
);

/*==============================================================*/
/* Index: Index_3                                               */
/*==============================================================*/
create index Index_3 on zhengke_auth
(
   token
);

/*==============================================================*/
/* Index: Index_4                                               */
/*==============================================================*/
create index Index_4 on zhengke_auth
(
   expireTime
);

