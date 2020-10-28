### 0、前言
相信点进来看这篇文章的同学们已经对 **Docker Dompose** 有一定的了解了，下面，我们拿最简单的例子来介绍如何使用 **Docker Compose** 来管理项目。
本文例子：
一个应用服务（ Spring Boot 的 jar 包）、 Mysql 服务和 Redis 服务。在每次启动，我们要先将 Mysql 容器和 Redis 容器启动起来，再将应用容器运行起来，这其中还不要忘了在创建应用容器时将容器网络连接到 MySQL 容器和 Redis 容器上，以便应用连接上它们并进行数据交换。

#### 1、项目结构
为了方便进行管理和迁移，我们建议将 **Docker Compose** 项目与搭建一个软件开发项目一样，将项目的内容聚集到一个文件目录中，下面是一个比较通用的项目结构：
```
└─ project
   ├─ app
   ├─ compose
   │  └─ docker-compose.yml
   ├─ mysql
   │  └─ my.cnf
   ├─ redis
      └─ redis.conf
 
```
在这个目录结构中，区分了 5 个顶层目录：

- app ：用于存放程序工程，即代码、编译结果以及相关的库、工具等；
- compose ：用于定义 Docker Compose 项目；
- mysql ：与 MySQL 相关配置等内容；
- redis ：与 Redis 相关配置等内容；

#### 2、准备程序配置
为了更方便在开发过程中对 MySQL、Redis 程序本身管理，所以我们会将它们的核心配置放置到项目里，再通过挂载的方式映射到容器中。 
这样一来，我们就可以直接在我们宿主操作系统里直接修改这些配置，无须再进入到容器中了。
基于此，我们在完成目录的设计之后，首要解决的问题就是准备好这些程序中会经常变动的配置，并把它们放置在程序对应的目录之中。

我们常用下列几种方式来获得程序的配置文件：
- 借助配置文档直接编写
- 下载程序源代码中的配置样例
- 通过容器中的默认配置获得

##### 1）借助配置文档直接编写一个 MySQL 的配置文件：
我们先找到 MySQL 文档中关于配置文件的参考，也就是下面这个地址：
https://dev.mysql.com/doc/refman/5.7/en/server-options.html
我们根据这些内容，选取跟我们程序运行有影响的几项需要修改的参数，编写成 MySQL 的配置文件。
```
# ./mysql/my.cnf

[mysqld_safe]
pid-file = /var/run/mysqld/mysqld.pid
socket   = /var/run/mysqld/mysqld.sock
nice     = 0

[mysqld]
skip-host-cache
skip-name-resolve
explicit_defaults_for_timestamp

bind-address = 0.0.0.0
port         = 3306

user      = mysql
pid-file  = /var/run/mysqld/mysqld.pid
socket    = /var/run/mysqld/mysqld.sock
log-error = /var/log/mysql/error.log
basedir   = /usr
datadir   = /var/lib/mysql
tmpdir    = /tmp
sql_mode  = NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES

lc-messages-dir = /usr/share/mysql

symbolic-links = 0
```

##### 2）下载Redis源代码中的配置样例：
在 Redis 源代码中，就包含了一份默认的配置文件，我们可以基于上面做修改：
github上面的链接：https://github.com/antirez/redis/blob/3.2/redis.conf
其中我修改了最主要的密码：
```
# ./redis/redis.conf
##...
################################## SECURITY ###################################

# Require clients to issue AUTH <PASSWORD> before processing any other
# commands.  This might be useful in environments in which you do not trust
# others with access to the host running redis-server.
#
# This should stay commented out for backward compatibility and because most
# people do not need auth (e.g. they run their own servers).
#
# Warning: since Redis is pretty fast an outside user can try up to
# 150k passwords per second against a good box. This means that you should
# use a very strong password otherwise it will be very easy to break.
# 连接Redis需要的密码
requirepass Redis123
##...
```

#### 3、上传应用jar包到app文件夹，并编写Dockerfile文件
这个是基于java:8镜像提交的webapp镜像，大家可参考上一篇文章：https://blog.csdn.net/Howinfun/article/details/102514099

#### 4、编写Docker Compose项目的定义文件
**docker-compose.yml** 文件在compose文件夹下，下面是我自己根据需求编写的：
```
version: "3"

services:

  redis:
    image: redis:3.2
    container_name: app_redis
    volumes:
      - ../redis/redis.conf:/etc/redis/redis.conf:ro
      - ../redis/data:/data
    command:
      - redis-server
      - /etc/redis/redis.conf
    ports:
     - 6379:6379

  mysql:
    image: mysql:5.7
    container_name: app_mysql
    volumes:
      - ../mysql/my.cnf:/etc/mysql/my.cnf:ro
      - ../mysql/data:/var/lib/mysql
    environment:
      MYSQL_ROOT_PASSWORD: 123456
    ports:
      - 3306:3306
   
  webapp:
    build: ../app
    container_name: app_web
    depends_on:
        - mysql
        - redis
    ports:
        - "8888:8888"
```
稍微讲解一下：
**version**：版本号，最新为3
**services**：Compose项目的服务配置
**redis**:
- image：基于redis:3.2镜像
- container_name：容器名
- volumes：第一个是将项目中的写好的配置文件通过挂载的方式映射到容器中，而且ro指定了文件为只读；第二个是将Redis 容器中的 /data 目录通过挂载的方式绑定到了宿主机上的目录中
- command：创建容器时执行的命令，这里是启动redis服务的命令
- ports：对外开放的访问端口号

**mysql**：
- image：基于mysql:5.7镜像
- container_name：容器名
- volumes：第一个是将项目中的写好的配置文件通过挂载的方式映射到容器中，而且ro指定了文件为只读；第二个是将MySQL 容器中的 /var/lib/mysql 目录通过挂载的方式绑定到了宿主机上的目录中
- environment：为 MySQL 设置了初始密码
- ports：对外开放的访问端口号

**webapp**:
- build：通过Dockerfile构建镜像，这里是指定Dockerfile文件的路径
- container_name：容器名
- depends_on：依赖于mysql和redis容器（在 Docker Compose 为我们启动项目的时候，会检查所有依赖，形成正确的启动顺序并按这个顺序来依次启动容器。）
- ports：对外开放的访问端口号
在这个项目里，我将 Redis 和 MySQL 的数据存储目录，也就是 Redis 容器中的 /data 目录和 MySQL 容器中的 /var/lib/mysql 目录通过挂载的方式绑定到了宿主机上的目录中。 
这么做的目的是为了让 Redis 和 MySQL 的数据能够持久化存储，避免我们在创建和移除容器时造成数据的流失。
同时，这种将数据挂载出来的方法，可以直接方便我们打包数据并传送给其他开发者，方便开发过程中进行联调。

#### 5、启动Compose项目
**docker-compose up** 命令类似于 Docker Engine 中的 **docker run**，它会根据 docker-compose.yml 中配置的内容，创建所有的容器、网络、数据卷等等内容，并将它们启动。与 **docker run** 一样，默认情况下 **docker-compose up** 会在“前台”运行，我们可以用 -d 选项使其“后台”运行。事实上，我们大多数情况都会加上 -d 选项。
```
docker-compose -p appweb -f ./compose/docker-compose.yml up -d
```
命令参数：
- -p：项目名称
- -f：指定docker-compose.yml文件路径
- -d：后台启动
```
[root@izwz90lvzs7171wgdhul8az project2]# docker-compose -p appweb -f ./compose/docker-compose.yml up -d
Building webapp
Step 1/5 : FROM java:8
 ---> d23bdf5b1b1b
Step 2/5 : MAINTAINER Howinfun
 ---> Using cache
 ---> f72d0468f1ff
Step 3/5 : VOLUME /tmp
 ---> Using cache
 ---> 902b3df17b06
Step 4/5 : ADD  app.jar /root/docker_test/app.jar
 ---> 511f44917a82
Step 5/5 : ENTRYPOINT ["nohup","java","-jar","/root/docker_test/app.jar",">","/root/docker_test/app.log","&"]
 ---> Running in 4825ec9daf24
Removing intermediate container 4825ec9daf24
 ---> 5e009b2fc6ae
Successfully built 5e009b2fc6ae
Successfully tagged appweb_webapp:latest
WARNING: Image for service webapp was built because it did not already exist. To rebuild this image you must use `docker-compose build` or `docker-compose up --build`.
Creating app_mysql ... done
Creating app_redis ... done
Creating app_web   ... done
```
查看创建的镜像：
```
[root@izwz90lvzs7171wgdhul8az project2]# docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
appweb_webapp       latest              5e009b2fc6ae        45 seconds ago      686MB
mysql               5.7                 383867b75fd2        4 weeks ago         373MB
redis               3.2                 87856cc39862        12 months ago       76MB
```
查看正在启动的容器：
```
[root@izwz90lvzs7171wgdhul8az project2]# docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED              STATUS              PORTS                               NAMES
ccaa96d508d1        appweb_webapp       "nohup java -jar /ro??   About a minute ago   Up About a minute   0.0.0.0:8888->8888/tcp              app_web
790629b5e7d0        mysql:5.7           "docker-entrypoint.s??   About a minute ago   Up About a minute   0.0.0.0:3306->3306/tcp, 33060/tcp   app_mysql
610d0b89c267        redis:3.2           "docker-entrypoint.s??   About a minute ago   Up About a minute   0.0.0.0:6379->6379/tcp              app_redis
```

#### 6、访问接口：
容器启动后，我们尝试使用postman或者其他http工具去访问部署在容器中的应用接口。
![](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9naXRlZS5jb20vSG93aW5mdW4vaW5mb3JtYWwtZXNzYXkvcmF3L21hc3Rlci90aGlua2luZy9pbWFnZXMvMjgucG5n?x-oss-process=image/format,png)

#### 7、网络配置：
我们都知道，使用 Docker 打包镜像，我们可以在任意的环境下部署和运行项目，Docker 的容器能够很轻松的运行在开发者本地的电脑，数据中心的物理机或虚拟机，云服务商提供的云服务器，甚至是混合环境中。
但是，这里的每一个环境的IP地址可是不一样，而且，应用服务链接的Mysql数据库，或者Redis缓存那必定是要和部署环境的IP地址一一对应的，那么难道我们还需要每次都去修改配置文件的IP吗？
**答案是：不用的！**
在 Docker Compose 里，我们可以为整个应用系统设置一个或多个网络。要使用网络，我们必须先声明网络。声明网络的配置同样独立于 services 存在，是位于根配置下的 **networks** 配置。
下面我们尝试在docker-compose.yml中添加网络配置。
```
version: "3"

services:

  redis:
    image: redis:3.2
    container_name: app_redis
    volumes:
      - ../redis/redis.conf:/etc/redis/redis.conf:ro
      - ../redis/data:/data
    command:
      - redis-server
      - /etc/redis/redis.conf
    ports:
      - 6379:6379
    networks:
      - appnet

  mysql:
    image: mysql:5.7
    container_name: app_mysql
    volumes:
      - ../mysql/my.cnf:/etc/mysql/my.cnf:ro
      - ../mysql/data:/var/lib/mysql
    environment:
      MYSQL_ROOT_PASSWORD: 123456
    ports:
      - 3306:3306
    networks:
      - appnet
   
  webapp:
    build: ../app
    container_name: app_web
    depends_on:
      - mysql
      - redis
    ports:
      - "8888:8888"
    networks:
      - appnet

networks:
  appnet:
    driver: bridge
```
可以看到，我们在docker-compose.yml文件的最下面添加了关于网络的配置。网络名为**appnet**，网络驱动为 **Bridge**。
**Bridge** 网络是 Docker 容器的默认网络驱动，简而言之其就是通过网桥来实现网络通讯。
然后在每个服务的下面都配置上服务所在的网格，就是上面的 **appnet** 了。
接下来，修改 application.properties 中的IP，改为容器名或者服务名作为网络地址。
例如我的配置文件要将 MySql 和 Redis 的IP，改为各自服务的容器名：
```
#Mysql
spring.datasource.url=jdbc\:mysql\://app_mysql\:3306/test?useUnicode\=true&characterEncoding\=utf-8&allowMultiQueries=true&&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=123456

#Redis
spring.redis.host=app_redis
spring.redis.password=Redis123..
spring.redis.database=1
```

#### 8、停止项目
与 **docker-compose up** 相反，**docker-compose down** 命令用于停止所有的容器，并将它们删除，同时消除网络等配置内容，也就是几乎将这个 Docker Compose 项目的所有影响从 Docker 中清除。
```
docker-compose -p appweb -f ./compose/docker-compose.yml down --rmi all
```
命令参数：
- -p：项目名称（up的时候有的，down一定也要带上，不然找不到network）
- -f：docker-compose.yml路径
- down：停止容器，并删除容器
- --rmi all：删除项目的所有镜像
```
[root@izwz90lvzs7171wgdhul8az project2]# docker-compose -p appweb -f ./compose/docker-compose.yml down --rmi all
Stopping app_web   ... done
Stopping app_mysql ... done
Stopping app_redis ... done
Removing app_web   ... done
Removing app_mysql ... done
Removing app_redis ... done
Removing network appweb_default
Removing image redis:3.2
Removing image mysql:5.7
Removing image appweb_webapp
```
<font color="red">注意：如果在使用 up 命令时指定了项目名，而在 down 命令时没有指定项目名会报错</font>：
```
[root@izwz90lvzs7171wgdhul8az project2]# docker-compose -f ./compose/docker-compose.yml down
Removing network compose_default
WARNING: Network compose_default not found.
```
#### 9、重新利用up命令启动项目
```
docker-compose -p appweb -f ./compose/docker-compose.yml up -d
```
命令参数：
- -p：项目名称
- -f：指定docker-compose.yml文件路径
- -d：后台启动
```
[root@izwz90lvzs7171wgdhul8az project2]# docker-compose -p appweb -f ./compose/docker-compose.yml up -d
Creating network "appweb_appnet" with driver "bridge"
Pulling redis (redis:3.2)...
3.2: Pulling from library/redis
f17d81b4b692: Pull complete
b32474098757: Pull complete
8980cabe8bc2: Pull complete
58af19693e78: Pull complete
a977782cf22d: Pull complete
9c1e268980b7: Pull complete
Digest: sha256:7b0a40301bc1567205e6461c5bf94c38e1e1ad0169709e49132cafc47f6b51f3
Status: Downloaded newer image for redis:3.2
Pulling mysql (mysql:5.7)...
5.7: Pulling from library/mysql
8f91359f1fff: Pull complete
6bbb1c853362: Pull complete
e6e554c0af6f: Pull complete
f391c1a77330: Pull complete
414a8a88eabc: Pull complete
fee78658f4dd: Pull complete
9568f6bff01b: Pull complete
76041efb6f83: Pull complete
ea54dbd83183: Pull complete
566857d8f022: Pull complete
01c09495c6e7: Pull complete
Digest: sha256:f7985e36c668bb862a0e506f4ef9acdd1254cdf690469816f99633898895f7fa
Status: Downloaded newer image for mysql:5.7
Building webapp
Step 1/5 : FROM java:8
 ---> d23bdf5b1b1b
Step 2/5 : MAINTAINER Howinfun
 ---> Using cache
 ---> f72d0468f1ff
Step 3/5 : VOLUME /tmp
 ---> Using cache
 ---> 902b3df17b06
Step 4/5 : ADD  app.jar /root/docker_test/app.jar
 ---> 674b9e9f9766
Step 5/5 : ENTRYPOINT ["nohup","java","-jar","/root/docker_test/app.jar",">","/root/docker_test/app.log","&"]
 ---> Running in 428e426ccede
Removing intermediate container 428e426ccede
 ---> f0f5e8bf51cb
Successfully built f0f5e8bf51cb
Successfully tagged appweb_webapp:latest
WARNING: Image for service webapp was built because it did not already exist. To rebuild this image you must use `docker-compose build` or `docker-compose up --build`.
Creating app_mysql ... done
Creating app_redis ... done
Creating app_web   ... done
[root@izwz90lvzs7171wgdhul8az project2]# 
```
上面我们可以很清楚的看到，启动项目时第一件事情就是创建项目配置的网络 **appnet** 了。
最后我们再尝试测试一下接口，可以发现是没有问题的。那么就是说，现在这个项目打包到哪里都可以运行了，而不用理会不同环境下的不同网络。
#### 10、结束
到这里，利用 Docker Compose 搭建 Spring Boot 运行环境已经全部完成。其实只要掌握了这个搭建方法，以后不管是搭建 Spring Cloud 微服务，甚至是其他语言，例如 Python、Go等，方法都是一样的，最主要的是服务的配置和之间的依赖，即编写 Docker Compose 的定义文件 docker-compose.yml。

