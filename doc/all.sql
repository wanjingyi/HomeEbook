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

#分类
drop table if exists `category`;
create table `category`
(
    `id`   bigInt unsigned NOT NULL comment 'id',
    `parent` bigint not null default 0 comment '父id',
    `name` varchar(255) NOT NULL comment '名称',
    `sort` int comment '顺序',
    primary key (`id`)
)engine=innodb default charset=utf8mb4 comment '分类';

insert into `category` (id, parent, name, sort) values (100, 000, '前端开发', 100);
insert into `category` (id, parent, name, sort) values (101, 100, 'JavaScript', 100);
insert into `category` (id, parent, name, sort) values (102, 100, 'Vue', 200);
insert into `category` (id, parent, name, sort) values (103, 100, 'React', 300);
insert into `category` (id, parent, name, sort) values (200, 000, '后端开发', 200);
insert into `category` (id, parent, name, sort) values (201, 200, 'Java', 100);
insert into `category` (id, parent, name, sort) values (202, 200, 'Python', 200);
insert into `category` (id, parent, name, sort) values (203, 200, 'Go', 300);
insert into `category` (id, parent, name, sort) values (300, 000, '数据库', 300);
insert into `category` (id, parent, name, sort) values (301, 300, 'MySQL', 100);
insert into `category` (id, parent, name, sort) values (302, 300, 'PostgreSQL', 200);
insert into `category` (id, parent, name, sort) values (303, 300, 'MongoDB', 300);
insert into `category` (id, parent, name, sort) values (400, 000, '运维', 400);
insert into `category` (id, parent, name, sort) values (401, 400, 'Linux', 100);
insert into `category` (id, parent, name, sort) values (402, 400, 'Windows', 200);
insert into `category` (id, parent, name, sort) values (403, 400, 'Docker', 300);
insert into `category` (id, parent, name, sort) values (500, 000, '其他', 500);
insert into `category` (id, parent, name, sort) values (501, 500, '其他', 100);
insert into `category` (id, parent, name, sort) values (502, 500, '其他', 200);
insert into `category` (id, parent, name, sort) values (503, 500, '其他', 300);

select * from category;

# 文档表的设计
drop table if exists `document`;
create table `document`
(
    `id`   bigInt unsigned NOT NULL comment 'id',
    `ebook_id` bigint NOT NULL default 0 comment '电子书id',
    `parent` bigint NOT NULL default 0 comment '父ID',
    `name` varchar(50) NOT NULL  comment '名称',
    `sort` int comment '顺序',
    `view_count` int default 0 comment '阅读数',
    `vote_count` int default 0 comment '点赞数',
    `comment_count` int default 0 comment '评论数',
    primary key (`id`)
)engine=innodb default charset=utf8mb4 comment '文档';

insert into `document` (id,ebook_id, parent, name, sort,view_count,vote_count) values (1,1,0,'文档1',1,0,0);
insert into `document` (id,ebook_id, parent, name, sort,view_count,vote_count) values (2,1,1,'示例1',1,0,0);
insert into `document` (id,ebook_id, parent, name, sort,view_count,vote_count) values (3,1,2,'示例2',2,0,0);
insert into `document` (id,ebook_id, parent, name, sort,view_count,vote_count) values (4,1,3,'文档2.1',1,0,0);
insert into `document` (id,ebook_id, parent, name, sort,view_count,vote_count) values (5,1,3,'示例2.2',2,0,0);
insert into `document` (id,ebook_id, parent, name, sort,view_count,vote_count) values (6,1,5,'文档2.2.1',1,0,0);

