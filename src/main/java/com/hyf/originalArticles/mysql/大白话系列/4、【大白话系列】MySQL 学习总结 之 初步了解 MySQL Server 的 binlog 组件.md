## 一、上节回顾

上节我们讲到，建议将 `redo log` 的刷盘策略设置为1：即提交事务时，强制将 `redo log buffer` 里的 `redo log` 刷入到磁盘后才算事务提交成功。

但是我们都知道，`redo log buffer` 是 InnoDB 存储引擎的组件，而 MySQL 支持很多种存储引擎，那么 MySQL 在撇除存储引擎后，自己就没有记录一下关于数据更新的日志吗？



## 二、binlog 日志文件

`redo log` 本身是 InnoDB 存储引擎特有的一个东西，所以 MySQL 也提供了一种所有存储引擎共享的日志文件，叫 `binlog`。

所以在提交事务时，不但会有 `rodo log`，还有会 `binlog ` 产生。

**binlog 写入到哪里？**

由于 `binlog` 不是 innoDB 存储引擎特有的组件，所以 `binlog` 不会像 `redo log` 一样先写入到缓冲池中的 `redo log buffer` 组件然后再刷回到磁盘中，所以应该是直接写入磁盘中。

**binlog 的刷盘策略？**

`binlog` 也有刷盘策略，那么就是说，`binlog` 并不一定是直接写入磁盘文件中。

`binlog` 的刷盘策略由参数 `sync_binlog` 参数控制。默认值是0，提交事务时，会将 `binlog` 写入 os cache 内存缓存中。但是这样会出现 MySQL 宕机导致内存缓存中的 `binlog` 丢失的问题。

所以我们建议将 `sync_binlog` 的值设置为1。此时当你提交事务时，会强制将 `binlog` 写入磁盘文件中，就不会出现上面数据丢失的情况了。



## 三、基于 redo log 和binlog 的事务提交

上面讲到，我们都是建议在事务提交时，强制将 `redo log` 和 `binlog` 刷入磁盘文件中，保证事务提交后，不存在日志丢失的问题。

**那么，这两个日志在事务提交时的原理是怎么样的呢？**

①、首先会将 `rodo log buffer` 中的 `redo log` 刷回磁盘文件中，此时磁盘文件有了对应的 `redo log` 日志文件；

②、接着，执行器会将 `binlog` 也写入到磁盘文件中，此时磁盘文件中也有了对应的 `binlog` 日志文件；

③、最后，还会将 `binlog` 日志文件的名称和位置写入到 `redo log` 日志文件中，同时，会在 `redo log` 日志文件中加上一个 `commit` 标记。



## 四、为什么有了 MySQL 的 binlog ，还有  InnoDB 的 redo log？

`binlog` 主要用来做数据归档的，它是逻辑日志，主要记录偏向逻辑性的日志，类似于“对表 t 中的 id=1 的一行数据进行了更新操作，更新以后的值是什么”。

`redo log` 主要用来做 MySQL 崩溃后数据恢复，它是物理日志，主要记录偏向物理性的日志，类似于“对哪个数据页中的哪一个记录做了什么修改”。

`redo log` 是 InnoDB 存储引擎特有的，而 `binlog` 是 MySQL Server 的组件，所以是所有存储引擎共享的。



## 五、为什么最后要在 redo log 中加入 commit 标记？

上面也说到 `redo log` 是 InnoDB 存储引擎特有的，所以它一定能被保证写入到磁盘，但是 `binlog` 却是 MySQL Server 的组件，所以 InnoDB 存储引擎不能保证它一定能成功写入磁盘，所以需要 `rodo log` 中 `commit` 标记来表明 `binlog` 写入成功了。

**那为什么要保证 binlog 成功写入磁盘呢？**

因为需要保证数据的一致性。

我们假设没有 `commit` 标记：

提交事务时，`redo log` 写入成功，而 `binlog` 写入失败，但是 InnoDB 存储引擎并不知道 `binlog` 是否写入成功，然后返回成功。当利用 `binlog` 来做数据归档时，例如 MySQL 的主从复制就是利用 `binlog` 来做的，那此时会因为 `binlog` 日志的缺失，而导致主从的数据不一致。

但是如果加入 `commit` 标记了，只有当 `redo log` 中有 `commit` 标记才算事务提交成功，这样就能保证两个日志文件的一致性了。 