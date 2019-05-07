### java相关
    jstat -gcutil pid interval 查看young old空间和gc执行情况
    jmap -heap pid 可查看对应进程jvm内存使用情况
    jmap -histo pid 可查看对应进程jvm内存中对象数量
    jmap -dump:format=b,file=heap_dump.hprof pid 可dump出hprof文件提供分析
### FTP相关
    service vsftpd start|stop|restart ftp相关命令             
    adduser -g ftp -s /sbin/nologin -d /home/cosee cosee 创建一个ftp组的cosee用户            
    passwd live 设置密码                
    chmod -R 755 cosee  分配读写权限            
    chowm -R cosee:ftp *   修改文件所属权限           
### 系统相关
    ifconfig   查看网卡
    tcpdump -i eth0 port 8080 -s 0 -v -w 1.cap     linux 抓取eth0网卡端口8080的数据包
    echo 3 > /proc/sys/vm/drop_caches  立即清理系统内存缓存
    tar -zcvf archive_name.tar.gz filename         压缩文件
    tar -zxvf archive_name.tar.gz                      解压缩文件