package com.huangliang.mvc.servlet;

import com.huangliang.framework.annotation.HLAutowire;
import com.huangliang.framework.annotation.HLController;
import com.huangliang.framework.annotation.HLRequestMapping;
import com.huangliang.framework.annotation.HLService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class HLDispatcherServlet_V1 extends HttpServlet {

    //保存所有带实例化的内名
    private List<String> classes = new ArrayList<>();
    //ioc容器
    private Map<String,Object> ioc = new HashMap<>();

    private Map<String,Method> handlerMapping = new HashMap<>();

    //保存 application.properties 配置文件中的内容
    private Properties contextConfig = new Properties();

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
        //1、加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));
        //2、扫描相关的类
        doScanner(contextConfig.getProperty("package"));
        //3、初始化扫描到的类，并且将它们放入到 ICO 容器之中
        doInstance();
        //4、完成依赖注入
        doAutowired();
//        //5、初始化 HandlerMapping
        initHandlerMapping();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initHandlerMapping() {
        //遍历ioc如果类中存在autowire则给该类中带这个注解的属性赋值
        for(Map.Entry entry :ioc.entrySet()){
            Class clazz = entry.getValue().getClass();
            if(!clazz.isAnnotationPresent(HLController.class)){
                //如果不是controller开头就直接跳过
                continue;
            }
            String url = "";
            if(clazz.isAnnotationPresent(HLRequestMapping.class)){
                HLRequestMapping requestMapping = (HLRequestMapping) clazz.getAnnotation(HLRequestMapping.class);
                url = requestMapping.value();
            }
            for(Method m : clazz.getDeclaredMethods()){
                if(m.isAnnotationPresent(HLRequestMapping.class)){
                    url = url + m.getAnnotation(HLRequestMapping.class).value();
                    handlerMapping.put(url,m);
                }
            }
        }

    }

    private void doAutowired() throws IllegalAccessException {
        //遍历ioc如果类中存在autowire则给该类中带这个注解的属性赋值
        for(Map.Entry entry :ioc.entrySet()){
            for(Field field : entry.getValue().getClass().getDeclaredFields()){
                if(field.isAnnotationPresent(HLAutowire.class)){
                    Class fieldClass = field.getType();
                    field.setAccessible(true);
                    field.set(entry.getValue(),ioc.get(fieldClass.getName()));
                }
            }
        }
    }

    private void doInstance() {
        for(String className : classes){
            try {
                Class clazz = Class.forName(className);
                if(clazz.isAnnotationPresent(HLController.class)){
                    ioc.put(className,clazz.newInstance());
                }else if (clazz.isAnnotationPresent(HLService.class)){
                    //把实现的接口作为key
                    for (Class aaa :clazz.getInterfaces()){
                        ioc.put(aaa.getName(),clazz.newInstance());
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

        }
    }

    private void doScanner(String aPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" +
                aPackage.replaceAll("\\.","/"));
        //得到配置文件中包的文件夹路径
        File classPath = new File(url.getFile());
        //取出该包下的所有class文件
        for (File file : classPath.listFiles()){
            if (file.isFile()){
                if(file.getName().endsWith(".class")){
                    classes.add(aPackage+"."+file.getName().replace(".class",""));
                }
            }else{
                doScanner(aPackage+"."+file.getName());
            }
        }

    }

    private void doLoadConfig(String contextConfigLocation) {
        InputStream fis = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != fis){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = req.getRequestURI();
        if(!handlerMapping.containsKey(requestUrl)){
            resp.getWriter().write("404");
            return ;
        }
        Method method = handlerMapping.get(requestUrl);
        Class clazz = method.getDeclaringClass();
        try {
            method.invoke(ioc.get(clazz.getName()),req,resp);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
