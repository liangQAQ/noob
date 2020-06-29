### 常用命令
    docker images ------------查看所有镜像
    docker ps ----------------查看所有进程
    docker ps -a -------------查看所有容器
    docker run -d --name test-tomcat -p 8080:8080 882487b8be1d
                --------------将imageId=882487b8be1d的应用以test-tomcat为名启动(-d后台，-p映射宿主机和创建出来的docker容器端口)
    docker logs CONTAINER_ID 
                --------查看CONTAINER_ID的容器的日志
    docker exec -it cosee112233 /bin/bash 
                --------------进入名字为cosee112233的容器
    docker rmi ...------------删除imageId为...的镜像
    docker rm ...-------------删除...的容器
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
