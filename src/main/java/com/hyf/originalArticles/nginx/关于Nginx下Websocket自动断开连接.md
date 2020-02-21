keepalive_timeout 用于闲置的连接（没有正在进行数据传输的连接），proxy_read_timeout 用于从 upstream 读取返回数据时，这时连接并不是闲置的。

proxy_read_timeout大于keepalive_timeout 意味着 upstream 正在返回数据时，可以多等一些时间 (proxy_read_timeout) ，传输完成之后，经过一段不太长的时间（keepalive_timeout)，如果没有新的请求需要复用这个连接，这个连接将被关闭。





最终解决方法：

增加nginx的proxy_read_timeout，增大为90s