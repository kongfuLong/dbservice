# dbservice
分布式服务－－－－－－数据提供服务层

该层负责数据库的连接 
并执行数据的查询 更新操作

数据源  druid   
读 写分离  
从库扩增只需要修改配置文件

对外提供一个接口
QshDbSuperInterface   数据的操作和访问封装

接口远程调用 使用rmi＋zookeeper
