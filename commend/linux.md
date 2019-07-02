### java相关
    jstat -gcutil pid interval 查看young old空间和gc执行情况，pid进程号，interval刷新时间
    jmap -heap pid 可查看对应进程jvm内存使用情况
    jmap -histo pid 可查看对应进程jvm内存中对象数量
    jmap -dump:format=b,file=heap_dump.hprof pid 可dump出hprof文件提供分析
    jstack -l pid >> 1.log  将进程pid中的线程栈打印至1.log文件中
    top -H -p pid 查看对应pid进程的哪个线程tid占用CPU过高
    printf "%x\n" tid 将10进制tid转化为16进制TID
    jstack pid | grep TID -A 30 查看pid进程中Tid的栈信息30前行
    
### FTP相关
    service vsftpd start|stop|restart ftp相关命令             
    adduser -g ftp -s /sbin/nologin -d /home/abcde abcde 创建一个ftp组的abcde用户            
    passwd abcde 设置密码                
    chmod -R 755 abcde  分配读写权限            
    chowm -R abcde:ftp *   修改文件所属权限           
### 系统相关
    ifconfig   查看网卡
    tcpdump -i eth0 port 8080 -s 0 -v -w 1.cap  抓取eth0网卡端口8080的数据包
    echo 3 > /proc/sys/vm/drop_caches  立即清理系统内存缓存
    tar -zcvf archive_name.tar.gz filename 压缩文件
    tar -zxvf archive_name.tar.gz  解压缩文件
