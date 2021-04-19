##### 在windows和mac系统中使用Docker Desktop安装Docker对系统的要求是很高的。
对于 Windows 系统来说，安装 Docker for Windows 需要符合以下条件：

- 必须使用 Windows 10 Pro ( 专业版 )
- 必须使用 64 bit 版本的 Windows

对于 macOS 系统来说，安装 Docker for Mac 需要符合以下条件：

- Mac 硬件必须为 2010 年以后的型号
- 必须使用 macOS El Capitan 10.11 及以后的版本

下面提供两个下载链接：
[Docker for Windows](https://hub.docker.com/editions/community/docker-ce-desktop-windows)
![](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9naXRlZS5jb20vSG93aW5mdW4vaW5mb3JtYWwtZXNzYXkvcmF3L21hc3Rlci90aGlua2luZy9pbWFnZXMvMTAucG5n?x-oss-process=image/format,png)
[Docker for Mac](https://hub.docker.com/editions/community/docker-ce-desktop-mac)
![](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9naXRlZS5jb20vSG93aW5mdW4vaW5mb3JtYWwtZXNzYXkvcmF3L21hc3Rlci90aGlua2luZy9pbWFnZXMvOS5wbmc?x-oss-process=image/format,png)
Docker Desktop 系列为我们在 Windows 和 macOS 中使用 Docker 提供了巨大的便利，几乎让我们可以在数分钟内搭建 Windows 和 macOS 中 Docker 的运行环境，并得到像Linux 中使用 Docker 一样的体验。
但 Docker Desktop 依然存在一定的局限性，其中最大的莫过于其对 Windows 和 macOS 的苛刻要求。虽然我们提倡保持操作系统的更新换代，以得到最新的功能以及更好的安全保障，但依然有很多情况下我们不得不使用低版本的 Windows 和 macOS。

##### 对于这种情况，Docker 官方也提供了相应的解决方案：Docker Toolbox
下载地址：https://github.com/docker/toolbox/releases
![](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9naXRlZS5jb20vSG93aW5mdW4vaW5mb3JtYWwtZXNzYXkvcmF3L21hc3Rlci90aGlua2luZy9pbWFnZXMvOC5wbmc?x-oss-process=image/format,png)
注意：这里我给大家的建议是安装下载最新的，至于为什么，大家可以继续往下面看。

##### 准备操作：
在启动Docker Quickstart Terminal前：
**<font color="red">ps：我的Docker Toolbox安装在D:\ID\docker下</font>**
###### 1、我们在Docker Toolbox的同级目录上创建machine文件夹，然后在machine下创建cache文件夹。
![创建文件夹](https://img-blog.csdnimg.cn/20190930155639906.png)
###### 2、我们要将Docker Toolbox安装路径下的boot2docker.iso复制到上面的cache文件夹下。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190930155758245.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0hvd2luZnVu,size_16,color_FFFFFF,t_70)
##### 为啥这么准备？
假设：Docker ToolBox的安装路径为：<font color="red">D:\IT\Docker\Docker Toolbox</font>
在第一次启动Docker Quickstart Terminal时会对docker环境进行初始化，这时候首先会检查【<font color="red">D:\IT\Docker\machine\cache</font>】是否有boot2docker.iso，并且会链接到github上检查boot2docker.iso是否是最新的，如果不是还会重新下载最新的boot2docker.iso，最后会将cache下的boot2docker.iso复制到【<font color="red">D:\IT\Docker\machine\machines\default</font>】下，然后进行虚拟机安装。
**ps：Docker Toolbox安装的虚拟机默认叫default。**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190930160544516.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0hvd2luZnVu,size_16,color_FFFFFF,t_70)
##### boot2docker.iso不是最新怎么办？
当然了，如果你已经安装了不是最新版本的，怎么办？那是相当的简单呐，就是断网！没错，你没看错，就是断网，断网了就不能链接到github上检测。

当我们上面的都没问题了，以后双击Docker Quickstart Terminal，就会显示小鲸鱼：
![](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9naXRlZS5jb20vSG93aW5mdW4vaW5mb3JtYWwtZXNzYXkvcmF3L21hc3Rlci90aGlua2luZy9pbWFnZXMvMTMucG5n?x-oss-process=image/format,png)
 