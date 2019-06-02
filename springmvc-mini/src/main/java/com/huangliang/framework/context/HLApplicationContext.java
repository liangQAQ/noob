package com.huangliang.framework.context;

import com.huangliang.framework.annotation.HLAutowire;
import com.huangliang.framework.annotation.HLController;
import com.huangliang.framework.annotation.HLService;
import com.huangliang.framework.aop.*;
import com.huangliang.framework.aop.advice.HLAdvisedSupport;
import com.huangliang.framework.beans.HLBeanDefinition;
import com.huangliang.framework.beans.HLBeanWrapper;
import com.huangliang.framework.beans.config.HLBeanDefinitionReader;
import com.huangliang.framework.beans.support.HLDefaultListableBeanFactory;
import com.huangliang.framework.core.HLFactoryBean;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class HLApplicationContext extends HLDefaultListableBeanFactory implements HLFactoryBean {

    //配置文件集合
    private String[] configLoactions;
    //配置文件定位对象
    private HLBeanDefinitionReader reader;

    private Properties config;

    //用来保证注册式单例的容器
    private Map<String,Object> singletonBeanCacheMap = new HashMap<String, Object>();
    //用来存储所有的被代理过的对象
    private Map<String,HLBeanWrapper> beanWrapperMap = new ConcurrentHashMap<String,
                HLBeanWrapper>();

    public HLApplicationContext(String... arr){
        this.configLoactions = arr;
        refresh();
    }


    @Override
    public void refresh(){
        //1.定位配置文件
        reader = new HLBeanDefinitionReader(configLoactions);
        config = reader.getProperties();
        //2.加载对象把它们封装成 BeanDefinition
        List<HLBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
        try {
            //3、注册，把配置信息放到容器里面(伪 IOC 容器)
            doRegisterBeanDefinition(beanDefinitions);
            //4、把不是延时加载的类，有提前初始化
            doAutowrited();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("实例化一次");
    }

    public List getBeanNamesList(){
        return this.beanDefinitionMap.keySet().stream().collect(Collectors.toList());
    }

    private void doAutowrited() {
        for(Map.Entry<String, HLBeanDefinition> entry:beanDefinitionMap.entrySet()){
            if(!entry.getValue().isLazyInit()){
                getBean(entry.getKey());
            }
        }
    }

    private void doRegisterBeanDefinition(List<HLBeanDefinition> beanDefinitions) throws Exception {
        for (HLBeanDefinition beanDefinition: beanDefinitions) {
            if(super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())){
                throw new Exception("The “" + beanDefinition.getFactoryBeanName() + "” is exists!!");
            }
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
        }
    }

    //依赖注入，从这里开始，通过读取 BeanDefinition 中的信息
    //然后，通过反射机制创建一个实例并返回
    //Spring 做法是，不会把最原始的对象放出去，会用一个 BeanWrapper 来进行一次包装
    //装饰器模式：
    //1、保留原来的 OOP 关系
    //2、需要对它进行扩展，增强（为了以后 AOP 打基础）
    @Override
    public Object getBean(String beanName) {
        //实例化对象
        Object instants = instantiateBean(beanDefinitionMap.get(beanName));
        if(instants==null){
            return null;
        }
        beanWrapperMap.put(beanName,new HLBeanWrapper(instants));
        //DI依赖注入
        populateBean(beanName,instants);
        return instants;
    }

    private void populateBean(String beanName, Object instants) {
        Class clazz = instants.getClass();
        if(!clazz.isAnnotationPresent(HLController.class)||!!clazz.isAnnotationPresent(HLService.class)){
            return ;
        }
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            if (!field.isAnnotationPresent(HLAutowire.class)){ continue; }
            HLAutowire autowired = field.getAnnotation(HLAutowire.class);
            String autowiredBeanName = autowired.value().trim();
            if("".equals(autowiredBeanName)){
                autowiredBeanName = field.getName();
            }
            Object fieldValue = null;
            HLBeanWrapper wrapper = this.beanWrapperMap.get(autowiredBeanName);
            if(wrapper!=null){
                fieldValue = wrapper.getWrappedInstance();
            }
            if(fieldValue == null){
                //如果所需属性为空，则递归调用getBean实例化所需的属性
                fieldValue = getBean(autowiredBeanName);
            }
            field.setAccessible(true);
            try {
                field.set(instants,fieldValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    //传一个 BeanDefinition，就返回一个实例 Bean
    private Object instantiateBean(HLBeanDefinition beanDefinition){
        String simpleClassName = beanDefinition.getFactoryBeanName();
        if(singletonBeanCacheMap.containsKey(simpleClassName)){
            return singletonBeanCacheMap.get(simpleClassName);
        }else{
            Object instants = null;
            try {
                instants = Class.forName(beanDefinition.getBeanClassName()).newInstance();
                //判断该实例化的类是aop配置扫描的包下的类
                // 如果是，则用代理对象覆盖原对象
                HLAdvisedSupport config = instantionAopConfig(beanDefinition);
                config.setTargetClass(instants.getClass());
                config.setTarget(instants);
                if(config.pointCutMatch()) {
                    instants = createProxy(config).getProxy();
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            singletonBeanCacheMap.put(simpleClassName,instants);
            return instants;
        }
    }

    private HLAopProxy createProxy(HLAdvisedSupport config) {
        HLAopProxy proxy = null;
        if(config.getTargetClass().getInterfaces().length>0){
            //如果实现了接口，则使用jdk动态代理
            proxy = new HLJdkAopProxy(config);
        }else{
            //如果没有实现接口，则使用cglib代理(暂时没实现)
            proxy = new HLCglibAopProxy();
        }
        return proxy;
    }

    private HLAdvisedSupport instantionAopConfig(HLBeanDefinition beanDefinition) {
        HLAopConfig config = new HLAopConfig();
        config.setPointCut(reader.getProperties().getProperty("pointCut"));
        config.setAspectClass(reader.getProperties().getProperty("aspectClass"));
        config.setAspectBefore(reader.getProperties().getProperty("aspectBefore"));
        config.setAspectAfter(reader.getProperties().getProperty("aspectAfter"));
        config.setAspectAfterThrow(reader.getProperties().getProperty("aspectAfterThrow"));
        config.setAspectAfterThrowingName(reader.getProperties().getProperty("aspectAfterThrowingName"));
        return new HLAdvisedSupport(config);
    }

    public Properties getConfig() {
        return config;
    }

    public void setConfig(Properties config) {
        this.config = config;
    }
}
