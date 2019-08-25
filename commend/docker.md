### 常用命令
    docker images ------------查看所有镜像
    docker ps ----------------查看所有进程
    docker ps -a -------------查看所有容器
    docker run -d -it --name static cosee:innertest /bin/bash 
                --------------将cosee:innertest版本的应用以cosee112233为名启动(-d去掉看日志)
    docker exec -it cosee112233 /bin/bash 
                --------------进入名字为cosee112233的容器
    docker rmi ...------------删除imageId为...的镜像
    docker rm ...-------------删除...的容器
### 设置docker加速地址为阿里云仓库
    1.打开终端执行命令：docker-machine ssh
    2.修改配置文件：sudo vi /var/lib/boot2docker/profile在--label provider=virtualbox的下一行添加：--registry-mirror http://724ffc86.m.daocloud.io
    3.重启docker docker-machine.exe restart

### redis相关
    创建并启动：
    docker run -p 6379:6379 -d redis:3.2 redis-server --appendonly yes
    进入虚拟机:
    docker-machine ssh default
    连接客户端:
    docker exec -it imageId redis-cli
    启动redis服务:
    docker start imagesId
### rocketmq相关
    创建容器:
    docker run -d -p 9876:9876 --name rmqnamesrv -e "JAVA_OPT_EXT=-server -Xms256m -Xmx256m -Xmn128m" rocketmq:4.3
    docker run -d -p 9876:9876 --name rmqserver  foxiswho/rocketmq:server
### zookeeper相关
    docker pull zookeeper  ---------------拉取镜像
    docker run --name hl-zookeeper -p 2181:2181 --restart always -d zookeeper 
                           ----------以hl-zookeeper启动镜像映射出2181端口
    docker run -it --rm --link hl-zookeeper:zookeeper zookeeper zkCli.sh -server zookeeper           
                           ----------client连接到zookeeper
