package com.huangliang.mvc.framework.beans.config;

import com.huangliang.mvc.framework.beans.HLBeanDefinition;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//用对配置文件进行查找，读取、解析
public class HLBeanDefinitionReader {

    //注册的bean对象
    List<String> registerBean = new ArrayList<>();
    //配置文件
    private Properties properties = new Properties();

    //相当于xml中的指定扫描的目录(从properties中读取)
    private final String SCAN_PACKAGE = "scanPackage";

    public HLBeanDefinitionReader(String... str){
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(str[0]);
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //扫描当前目录下的所有文件
        scanPackageFiles(properties.getProperty(SCAN_PACKAGE));
    }

    //扫描当前目录下的所有文件
    private void scanPackageFiles(String property) {
        URL url = this.getClass().getClassLoader().getResource("/" +
                property.replaceAll("\\.","/"));
        //得到配置文件中包的文件夹路径
        File classPath = new File(url.getFile());
        //取出该包下的所有class文件
        for (File file : classPath.listFiles()){
            if (file.isFile()){
                if(file.getName().endsWith(".class")){
                    registerBean.add(property+"."+file.getName().replace(".class",""));
                }
            }else{
                scanPackageFiles(property+"."+file.getName());
            }
        }
    }

    //把配置文件中扫描到的所有的配置信息转换为 HLBeanDefinition 对象，以便于之后 IOC 操作方便
    public List<HLBeanDefinition> loadBeanDefinitions(){
        List<HLBeanDefinition> result = new ArrayList<>();
        for(String beanName : registerBean){
            Class clazz = null;
            try {
                clazz  = Class.forName(beanName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(clazz.isInterface()){
                continue;//接口类不做处理
            }
            HLBeanDefinition definition = createBeanDefinition(toLowerFirstCase(clazz.getSimpleName()),beanName);
            result.add(definition);

            Class<?> [] interfaces = clazz.getInterfaces();
            for (Class<?> i : interfaces) {
                result.add(createBeanDefinition(i.getName(),clazz.getName()));
            }
        }
        return result;
    }

    private String toLowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray();
        //之所以加，是因为大小写字母的 ASCII 码相差 32，
        // 而且大写字母的 ASCII 码要小于小写字母的 ASCII 码
        //在 Java 中，对 char 做算学运算，实际上就是对 ASCII 码做算学运算
        chars[0] += 32;
        return String.valueOf(chars);
    }

    private HLBeanDefinition createBeanDefinition(String factoryBeanName,String beanClassName){
        HLBeanDefinition beanDefinition = new HLBeanDefinition();
        beanDefinition.setBeanClassName(beanClassName);
        beanDefinition.setFactoryBeanName(factoryBeanName);
        return beanDefinition;
    }
}
