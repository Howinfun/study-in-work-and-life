-- auto-generated definition
create table log_record
(
    id            bigint unsigned auto_increment comment '主键'
        primary key,
    log_type      varchar(2)    default '1' not null comment '日志类型：1增2改3删',
    before_record varchar(1000) default ''  null comment '方法执行前数据库记录内容',
    after_record  varchar(1000) default ''  null comment '方法执行后数据库记录内容',
    operator      varchar(100)  default ''  null comment '操作者',
    create_time   datetime(3)               null comment '创建时间',
    success_msg   varchar(1000) default ''  not null comment '成功信息',
    error_msg     varchar(1000) default ''  not null comment '错误信息'
);

