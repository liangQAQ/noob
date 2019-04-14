package com.huangliang.mvc.framework.context;

import com.huangliang.mvc.framework.annotation.HLAutowire;
import com.huangliang.mvc.framework.annotation.HLController;
import com.huangliang.mvc.framework.annotation.HLService;
import com.huangliang.mvc.framework.beans.HLBeanDefinition;
import com.huangliang.mvc.framework.beans.config.HLBeanDefinitionReader;
import com.huangliang.mvc.framework.beans.support.HLDefaultListableBeanFactory;
import com.huangliang.mvc.framework.context.support.HLAbstractApplicationContext;
import com.huangliang.mvc.framework.core.HLFactoryBean;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HLApplicationContext extends HLDefaultListableBeanFactory implements HLFactoryBean {

    //配置文件集合
    private String[] configLoactions;
    //配置文件定位对象
    private HLBeanDefinitionReader reader;

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
        //2.加载对象把它们封装成 BeanDefinition
        List<HLBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
        //3、注册，把配置信息放到容器里面(伪 IOC 容器)
        try {
            doRegisterBeanDefinition(beanDefinitions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //4、把不是延时加载的类，有提前初始化
        doAutowrited();
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
                autowiredBeanName = field.getType().getName();
            }
            field.setAccessible(true);
            try {
                field.set(instants,this.beanWrapperMap.get(autowiredBeanName).getWrappedInstance());
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

}
