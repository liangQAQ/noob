### 设置docker加速地址为阿里云仓库
1.docker-machine ssh default
2.sudo sed -i "s|EXTRA_ARGS='|EXTRA_ARGS='--registry-mirror=https://g23j5rvs.mirror.aliyuncs.com |g" /var/lib/boot2docker/profile
3.exit
4.docker-machine restart default


###  docker redis相关
启动：
docker run -p 6379:6379 -d redis:3.2 redis-server --appendonly yes
进入虚拟机:
docker-machine ssh default
连接客户端:
docker exec -it imageId redis-cli