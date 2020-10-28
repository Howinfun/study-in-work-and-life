### Docker 的环境依赖
由于 Docker 的容器隔离依赖于 Linux 内核中的相关支持，所以使用 Docker 首先需要确保安装机器的 Linux kernel 中包含Docker 所需要使用的特性。以目前 Docker 官方主要维护的版本为例，我们需要使用基于 Linux kernel 3.10 以上版本的 Linux 系统来安装 Docker，下面的表格将直接展示 Docker 对主流几款 Linux 系统版本的要求。
<table>
<thead>
<tr>
<th style="text-align:left">操作系统</th>
<th style="text-align:left">支持的系统版本</th>
</tr>
</thead>
<tbody>
<tr>
<td style="text-align:left">CentOS</td>
<td style="text-align:left">CentOS 7</td>
</tr>
<tr>
<td style="text-align:left">Debian</td>
<td style="text-align:left">Debian Wheezy 7.7 (LTS) <br> Debian Jessie 8 (LTS) <br> Debian Stretch 9 <br> Debian Buster 10</td>
</tr>
<tr>
<td style="text-align:left">Fedora</td>
<td style="text-align:left">Fedora 26 <br> Fedora 27</td>
</tr>
<tr>
<td style="text-align:left">Ubuntu</td>
<td style="text-align:left">Ubuntu Trusty 14.04 (LTS) <br> Ubuntu Xenial 16.04 (LTS) <br> Ubuntu Artful 17.10</td>
</tr>
</tbody>
</table>
当然，在较低版本的 Linux 系统中也能安装 Docker，不过只能是版本较低的 Docker，其功能存在一些缺失，或者与最新版本有所区别。所以如果条件允许，建议将系统升级到支持最新版本 Docker 的系统版本。

### CentOS7安装最新DOcker的命令。
因为我的阿里云是CentOS7，所以就直接上相关的命令了。
```shell
$ sudo yum install yum-utils device-mapper-persistent-data lvm2
$ sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
$ sudo yum install docker-ce
$ sudo systemctl enable docker
$ sudo systemctl start docker
```

### 简单介绍上手命令：
- 启动Docker服务：
```shell
$ sudo systemctl start docker
```
- 实现Docker服务开机自启动的命令：
```shell
$ sudo systemctl enable docker
```
- 查看Docker的版本信息：
```shell
$ sudo docker version
```
下面为命令的内容：我们可以看到我们的Docker的版本号是19.03.2，表示是19年3月的第2次修正
```shell
[root@izwz90lvzs7171wgdhul8az ~]# docker version
Client: Docker Engine - Community
 Version:           19.03.2
 API version:       1.40
 Go version:        go1.12.8
 Git commit:        6a30dfc
 Built:             Thu Aug 29 05:28:55 2019
 OS/Arch:           linux/amd64
 Experimental:      false

Server: Docker Engine - Community
 Engine:
  Version:          19.03.2
  API version:      1.40 (minimum version 1.12)
  Go version:       go1.12.8
  Git commit:       6a30dfc
  Built:            Thu Aug 29 05:27:34 2019
  OS/Arch:          linux/amd64
  Experimental:     false
 containerd:
  Version:          1.2.6
  GitCommit:        894b81a4b802e4eb2a91d1ce216b8817763c29fb
 runc:
  Version:          1.0.0-rc8
  GitCommit:        425e105d5a03fabd737a126ad93d62a9eeede87f
 docker-init:
  Version:          0.18.0
  GitCommit:        fec3683
```
- 查看Docker更多的信息：
```shell
$ sudo docker info
```
下面为命令的内容：
```shell
[root@izwz90lvzs7171wgdhul8az ~]# docker info
Client:
 Debug Mode: false

Server:
 Containers: 0
  Running: 0
  Paused: 0
  Stopped: 0
 Images: 0
 Server Version: 19.03.2
 Storage Driver: overlay2
  Backing Filesystem: extfs
  Supports d_type: true
  Native Overlay Diff: true
 Logging Driver: json-file
 Cgroup Driver: cgroupfs
 Plugins:
  Volume: local
  Network: bridge host ipvlan macvlan null overlay
  Log: awslogs fluentd gcplogs gelf journald json-file local logentries splunk syslog
 Swarm: inactive
 Runtimes: runc
 Default Runtime: runc
 .......
```

- 列出Docker中所有镜像：
```shell
$ sodu docker images
```
下面为命令内容：
```shell
[root@izwz90lvzs7171wgdhul8az ~]# docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
ubuntu              latest              2ca708c1c9cc        9 days ago          64.2MB
```

- 拉取镜像：
```shell
$ sodu docker pull ubuntu
```
下面为命令内容：
```
[root@izwz90lvzs7171wgdhul8az ~]# docker pull ubuntu
Using default tag: latest
latest: Pulling from library/ubuntu
5667fdb72017: Pull complete 
d83811f270d5: Pull complete 
ee671aafb583: Pull complete 
7fc152dfb3a6: Pull complete 
Digest: sha256:b88f8848e9a1a4e4558ba7cfc4acc5879e1d0e7ac06401409062ad2627e6fb58
Status: Downloaded newer image for ubuntu:latest
docker.io/library/ubuntu:latest
```
当我们没有提供镜像标签时，Docker 会默认使用 latest 这个标签

- 查看某个镜像的详细信息[除了传递镜像或容器的名称外，还可以传入镜像 ID 或容器 ID]：
```shell
$ sodu docker inspect ubuntu:latest
```
下面为命令内容：
```shell
[root@izwz90lvzs7171wgdhul8az ~]# docker inspect ubuntu:latest
[
    {
        "Id": "sha256:2ca708c1c9ccc509b070f226d6e4712604e0c48b55d7d8f5adc9be4a4d36029a",
        "RepoTags": [
            "ubuntu:latest"
        ],
        "RepoDigests": [
            "ubuntu@sha256:b88f8848e9a1a4e4558ba7cfc4acc5879e1d0e7ac06401409062ad2627e6fb58"
        ],
     ......
    }
]
```
- 删除镜像[参数是镜像的名称或 ID]：
```shell
$ sudo docker rmi ubuntu:latest'
```
下面为命令内容：
```
[root@izwz90lvzs7171wgdhul8az ~]# docker rmi ubuntu:latest
Untagged: ubuntu:latest
Untagged: ubuntu@sha256:b88f8848e9a1a4e4558ba7cfc4acc5879e1d0e7ac06401409062ad2627e6fb58
Deleted: sha256:2ca708c1c9ccc509b070f226d6e4712604e0c48b55d7d8f5adc9be4a4d36029a
Deleted: sha256:bd416bed302bc2f061a2f6848a565483a5f265932d2d4fa287ef511b7d1151c8
Deleted: sha256:5308e2e4a70bd4344383b8de54f8a52b62c41afb5caa16310326debd1499b748
Deleted: sha256:dab02287e04c8b8207210b90b4056bd865fcfab91469f39a1654075f550c5592
Deleted: sha256:a1aa3da2a80a775df55e880b094a1a8de19b919435ad0c71c29a0983d64e65db
```
docker rmi 命令也支持同时删除多个镜像，只需要通过空格传递多个镜像 ID 或镜像名即可。

### 更换阿里云镜像源
Docker官方也提供了一个国内镜像源，但是呢，必须还是阿里的香，当然了，我们也可以两个都配置上。
在 Linux 环境下，我们可以通过修改 /etc/docker/daemon.json ( 如果文件不存在，你可以直接创建它 ) 这个 Docker 服务的配置文件达到效果。
```
{
    "registry-mirrors": [
        "https://ns2wtlx2.mirror.aliyuncs.com",
        "https://registry.docker-cn.com"
    ]
}
```
修改后，记得重启Docker来让配置生效：
```shell
$ sudo systemctl restart docker
```
最后通过docker info 来查阅当前注册的镜像源列表。
```shell
$ sudo docker info
```
下面为命令内容：

```
[root@izwz90lvzs7171wgdhul8az ~]# docker info
......
 Registry Mirrors:
  https://registry.docker-cn.com/
  https://ns2wtlx2.mirror.aliyuncs.com/
 Live Restore Enabled: false
 .....
```