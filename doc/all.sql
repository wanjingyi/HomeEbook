drop table if exists `test`;
create table `test`
(
    `id`   bigInt(20) unsigned NOT NULL comment 'id',
    `name` varchar(255) NOT NULL comment 'name',
    `password`  varchar(255)  NOT NULL comment 'password',
    primary key (`id`)
)engine=innodb default charset=utf8mb4 comment '测试';

insert into `test` (`id`, `name`, `password`) values (1, 'admin', 123456);


drop table if exists `demo`;
create table `demo`
(
    `id`   bigInt(20) unsigned NOT NULL comment 'id',
    `name` varchar(255) NOT NULL comment 'name',
    primary key (`id`)
)engine=innodb default charset=utf8mb4 comment '测试';
insert into `demo` (`id`, `name`) values (1, 'wangjingyi');