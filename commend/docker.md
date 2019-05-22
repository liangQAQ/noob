### 常用命令
    docker images ------------查看所有镜像
    docker ps ----------------查看所有进程
    docker ps -a -------------查看所有容器
    docker run -d -it --name static cosee:innertest /bin/bash 
                --------------将cosee:innertest版本的应用以cosee112233为名启动(-d去掉看日志)
    docker exec -it cosee112233 /bin/bash 
                --------------进入名字为cosee112233的容器

### 设置docker加速地址为阿里云仓库
    1.docker-machine ssh default
    2.sudo sed -i "s|EXTRA_ARGS='|EXTRA_ARGS='--registry-mirror=https://g23j5rvs.mirror.aliyuncs.com |g" /var/lib/boot2docker/profile
    3.exit
    4.docker-machine restart default
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
