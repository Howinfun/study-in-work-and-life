## 一、上节回顾

在上节[《 缓冲池(Buffer Pool) 的设计原理和管理机制》](https://blog.csdn.net/Howinfun/article/details/104380418)中，介绍了缓冲池整体的设计原理。包括几个比较重要的概念：free 链表、flush 链表和 lru 链表。正式因为这一套机制，使得 InnoDB 存储引擎可以基于内存操作，避免了磁盘随机读写的低性能。



## 二、Buffer Pool 如何应对高并发场景

#### 单个 Buffer Pool 的问题

直到现在，估计大家都以为缓冲池只是一个大的内存区域，在 InnoDB 存储引擎中只有一个，这是对的吗？

我们可以想象，如果 InnoDB 存储引擎只有一个 `Buffer Pool`，当高并发时，多个请求进来，那么为了保证数据的一致性（缓存页、free 链表、flush 链表、lru 链表等等多个操作），必须得给缓冲池加锁了，每一时刻只能有一个请求获得锁去操作 `Buffer Pool`，其他请求只能排队等待锁释放。那么此时 MySQL 的性能是多么的低！

#### 多个 Buffer Pool

既然一个 `Buffer Pool` 不够用，那么整多几个呗。

在生产环境中，其实我们是可以给 MySQL 设置多个 `Buffer Pool` 来提升 MySQL 的并发能力的~

**如何设置？**

我们先看看 MySQL 的默认规则：如果你给 Buffer Pool 分配的内存小于1GB，那么最多就只会给你一个 Buffer。

但是呢，如果你给 MySQL 设置的内存很大，此时你可以利用下面两个参数来设置缓冲池（Buffer Pool）的总大小和总实例数，这样，MySQL 就能有多个 `Buffer Pool` 来支撑高并发了。

```
[server] 
innodb_buffer_pool_size = 8589934592 
innodb_buffer_pool_instances = 4
```

解释一下：上面利用参数 `innodb_buffer_pool_size`  来设置 `Buffer Pool`  的总大小为 8G，利用参数 `innodb_buffer_pool_instances`  来设置一共有 4个 `Buffer Pool`  实例。那么就是说，MySQL 一共有 4个 `Buffer Pool` ，每个的大小为 2G。

当然了，每个 `Buffer Pool` 负责管理着自己的描述数据块和缓存页，有自己独立一套 free 链表、flush 链表和 lru 链表。

到这，我们就晓得，只要你能分配足够大的内存给 `Buffer Pool` ，你就能创建尽量多的 `Buffer Pool` 来应对高并发场景~

正所谓，并发性能不高，机器配置来凑，这还是有道理的。但是正经点来说，最基本最主要的还是咱们写的 SQL，能写出一手好 SQL，前提我们还是得理解 MySQL 各个组件的原理，熟悉索引的原理、事务原理和锁原理等。当然了，之后我也会分别对这些做出一个学习总结分享出来。



## 三、生产环境中，如何动态调整 Buffer Pool 的大小

相信基本每个公司，项目上线后，用户和流量会不断地增长，这对于 MySQL 来说，会有什么变化？

首先，访问增多，不断地从磁盘文件中的数据页读取数据到 `Buffer Pool`，也不断地将 `Buffer Pool` 的脏缓存页刷回磁盘文件中。很明显的，`Buffer Pool` 越小，这两个操作就会越频繁，但是磁盘IO操作又是比较耗时的，本来 SQL 执行只要 20 ms，如果碰巧碰到遇到缓存页用完，就要经历一系列的操作，SQL 最后执行完可能就要 200 ms，甚至更多了。

所以我们此时需要及时调整 `Buffer Pool`  的大小。

#### 1、如何动态调整 Buffer Pool 的大小？

但是生产环境，肯定不能让我们直接修改 MySQL 配置然后再重启吧，这估计要骂死。

在 MySQL 5.7 后，MySQL 允许我们动态调整参数 `innodb_buffer_pool_size` 的值来调整 `Buffer Pool` 的大小了。

**存在啥问题？**

给 `Buffer Pool` 调整更大的内存后。

调整前的配置：Buffer Pool 的总大小为8G，一共4个 Buffer Pool，每个大小为 2G。

```
[server] 
innodb_buffer_pool_size = 8589934592 
innodb_buffer_pool_instances = 4
```

假设给 Buffer Pool 调整到 16 G，就是说参数 `innodb_buffer_pool_size` 改为 17,179,869,184。

此时，MySQL 会为 Buffer Pool 申请一块大小为16G 的连续内存，然后分成 4块，接着将每一个 Buffer Pool 的数据都复制到对应的内存块里，最后再清空之前的内存区域。

我们可以发现，全部数据要从一块地方复制到另外一块地方，那这是相当耗费时间的操作，整整8个G的数据要进行复制粘贴呢！而且，如果本来 Buffer Pool 是更大的话，那就更恐怖了。



2、Buffer Pool 的 chunk 机制