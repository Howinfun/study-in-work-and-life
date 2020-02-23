### 镜像源
由于众所周知的原因，我们直接连接这些位于国外服务器上的仓库去获取依赖包速度是非常慢的，这时候我们通常会采用国内一些组织或开发者贡献的国内镜像仓库。
首先Docker也提供了国内的镜像源：https://registry.docker-cn.com
然后国内也有不少组织是提供了镜像源的，我们习惯于使用阿里云提供的加速器，
详情可到下面的链接看看：https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors

### 如何修改镜像源
1、打开Git Bash（或者Docker QuickStart Terminal），使用下面命令进入VM Bash
    docker-machine ssh default
2、打开/var/lib/boot2docker/profile
    sudo vi /var/lib/boot2docker/profile
3、在--label provider=virtualbox的下一行添加镜像源配置 
    --registry-mirror=https://ns2wtlx2.mirror.aliyuncs.com
4、重启VM
    exit退出VM bash，最后在Git Bash中执行docker-machine restart default

### 在Linux系统中修改
这个相对于上面的是比较简单的，毕竟Docker 本身就基于 Linux 的核心能力，所以在 Linux 中操作 Docker 是想当的简单的。
可以参考此文章提供的三种解决方案，不过我只实践过第一种，大家可以都试试： https://blog.csdn.net/skh2015java/article/details/82631633
