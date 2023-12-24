create schema hci;
use hci;
drop table if exists `user`;
create table `user` (
    `id` int not null auto_increment comment '主键',
    `username` varchar(32) default null comment '用户名',
    `email` varchar(32) default null comment '邮箱',
    `password` varchar(256) default null comment '密码',
    primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 comment='用户信息';

drop table if exists `fellow`;
create table `fellow` (
    `id` int not null auto_increment comment '主键',
    `userId` int default null comment '所属用户',
    `nickname` varchar(32) default null comment '昵称',
    `email` varchar(32) default null comment '邮箱',
    `sex` varchar(32) default null comment '性别',
    `birthYear` Integer default null comment '出生年份',
    `birthMonth` INTEGER default null comment '出生月份',
    `vocation` varchar(32) default null comment '职务',
    `note` varchar(256) default null comment '备注',
    primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 comment='同伴信息';

drop table if exists `article`;
create table `article` (
    `id` int not null auto_increment comment '主键',
    `title` varchar(32) default null comment '标题',
    `subtitle` varchar(256) default null comment '子标题',
    `type` int(1) default null comment '类型',
    `date` varchar(32) default null comment '日期',
    `pic` varchar(256) default null comment '图片',
    `isAvailable` int(1) default null comment '可获得',
    `eventId` INTEGER default null comment '所属项目',
    `text` text default null comment '内容',
    primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 comment='文章信息';

drop table if exists `counselor`;
create table `counselor` (
    `id` int not null auto_increment comment '主键',
    `name` varchar(32) default null comment '姓名',
    `sex` int(1) default null comment '性别',
    `position` int(1) default null comment '等级',
    `location` varchar(256) default null comment '地点',
    `introduction` text default null comment '介绍',
    `field` text default null comment '领域',
    `method` text default null comment '方法',
    `price` text default null comment '价格',
    `poison` text default null comment '鸡汤',
    `profile` varchar(256) default null comment '图像',
    `basic` Integer default null comment '基础价格',
    primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 comment='咨询师信息';

drop table if exists `counselor_award`;
create table `counselor_award` (
    `counselorId` INTEGER default null comment '所属咨询师',
    `award` varchar(32) default null comment '荣誉'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment='咨询师荣誉信息';

drop table if exists `counselor_field`;
create table `counselor_field` (
    `counselorId` INTEGER default null comment '所属咨询师',
    `field` varchar(32) default null comment '领域'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment='咨询师领域信息';

drop table if exists `counselor_form`;
create table `counselor_form` (
    `counselorId` INTEGER default null comment '所属咨询师',
    `form` varchar(32) default null comment '形式'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment='咨询师形式信息';

drop table if exists `inspirations`;
create table `inspirations` (
    `id` int not null auto_increment comment '主键',
    `poison` varchar(256) default null comment '鸡汤',
    `pic` varchar(256) default null comment '图片',
    primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 comment='鸡汤信息';

drop table if exists `event_book`;
create table `event_book` (
    `id` int not null auto_increment comment '主键',
    `counselorId` INTEGER default null comment '咨询师',
    `name` varchar(32) default null comment '活动名称',
    `startTime` varchar(32) default null comment '开始时间',
    `endTime` varchar(32) default null comment '结束时间',
    `price` FLOAT default null comment '价格',
    `remain` INTEGER default null comment '剩余人数',
    `isOnline` varchar(32) default null comment '是否线上',
    primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 comment='活动预定信息';

drop table if exists `event_fellow`;
create table `event_fellow` (
    `userId` INTEGER default null comment '所属用户',
    `eventId` INTEGER default null comment '所属活动',
    `fellowId` INTEGER default null comment '参加人员'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment='活动参与信息';

drop table if exists `user_event`;
create table `user_event` (
    `id` int not null auto_increment comment '主键',
    `eventId` INTEGER default null comment '所属活动',
    `userId` INTEGER default null comment '所属用户',
    `expectation` varchar(256) default null comment '你想从活动中了解一些什么？',
    `question` varchar(256) default null comment '你有什么想问活动嘉宾的吗？',
    `road` int(1) default null comment '你怎么知道我们的？',
    `reason` varchar(32) default null comment '你选择我们的原因是？',
    `type` int(1) default null comment'已预约 已取消 已完成',
    `cancel` varchar(256) default null comment '取消原因',
    primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 comment='用户活动预定信息';

drop table if exists `counselor_book`;
create table `counselor_book` (
    `id` int not null auto_increment comment '主键',
    `counselorId` INTEGER default null comment '咨询师',
    `name` varchar(32) default null comment '名称',
    `startTime` varchar(32) default null comment '开始时间',
    `endTime` varchar(32) default null comment '结束时间',
    `price` float default null comment '价格',
    `upLimit` INTEGER default null comment '人数上限',
    `isAvailable` int(1) default null comment '是否有空',
    primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 comment='咨询师预定信息';

drop table if exists `counselor_fellow`;
create table `counselor_fellow` (
    `userId` INTEGER default null comment '所属用户',
    `counselorBookId` INTEGER default null comment '所属咨询',
    `fellowId` INTEGER default null comment '参加人员'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment='咨询预定信息';

drop table if exists `user_counselor`;
create table `user_counselor` (
    `id` int not null auto_increment comment '主键',
    `counselorBookId` INTEGER default null comment '所属咨询师咨询',
    `userId` INTEGER default null comment '所属用户',
    `isOnline` int(1) default null comment '是否线上',
    `basicInfo` varchar(256) default null comment '（必填）你在为他人预约吗？你所帮助预约的对象和你是什么关系？',
    `relation` varchar(256) default null comment '咨询者之间是什么关系？',
    `problem` varchar(32) default null comment '你/咨询者遇到了什么问题？',
    `addition` varchar(256) default null comment '你有什么想要告诉我们的吗？',
    `road` int(1) default null comment '你怎么知道我们的？',
    `reason` varchar(32) default null comment '你选择我们的原因是？',
    `type` int(1) default null comment'已预约 已取消 已完成',
    `cancel` varchar(256) default null comment '取消原因',
    primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 comment='用户咨询师预定信息';
