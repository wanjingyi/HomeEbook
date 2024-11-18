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

drop table if exists `ebook`;
create table `ebook`
(
    `id`   bigInt unsigned NOT NULL comment 'id',
    `name` varchar(255) NOT NULL comment 'name',
    `category1_id` bigint comment '分类1',
    `category2_id` bigint comment '分类2',
    `cover` varchar(200) comment '封面',
    `description` varchar(200) comment '描述',
    `doc_count` int comment '文档数',
    `view_count` int comment '阅读数',
    `vote_count` int comment '点赞数',
    primary key (`id`)
)engine=innodb default charset=utf8mb4 comment '电子书';

insert into `ebook` (id,name,description) values (1,'spring boot 入门教程','零基础入门Java开发，企业级应用开发最佳首选框架');
insert into `ebook` (id,name,description) values (2,'vue 入门教程','前端框架，页面展示，让程序员只关心数据的交互，不在乎与样式的调整');
insert into `ebook` (id,name,description) values (3,'mybatis 入门教程','了解数据库，对于数据的更加深层次的操作');
insert into `ebook` (id,name,description) values (4,'鬼吹灯','改革开放以后，对于老一辈手札的学习，还有自己的实践');
insert into `ebook` (id,name,description) values (5,'盗墓笔记','鬼故事，一笔笔深入人心的盗墓笔记');
insert into `ebook` (id,name,description) values (6,'CFA','让小白明白金融，理解经济形势发展');
insert into `ebook` (id,name,description) values (7,'算法工程师','计算机算法类零基础学习');
insert into `ebook` (id,name,description) values (8,'断舍离','打开自己的心扉，明白哪些自己需要和不需要');
insert into `ebook` (id,name,description) values (9,'解忧杂货铺','解忧故事丛书');
insert into `ebook` (id,name,description) values (10,'白夜行','侦探类小说，悬疑调查类');

select * from ebook;


