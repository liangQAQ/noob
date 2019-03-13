package com.noob.design.factory.simple;

import com.noob.design.factory.IFruit;

/**
 * ## 简单工厂模式
 * 是指由一个工厂对象决定创建出哪一种产品类的实例。
 * ### 适用场景
 * 工厂类负责创建的对象较少。
 * 客户端只需要传入工厂类的参数，对于如何创建对象的逻辑不需要关心。
 * ### 优点
 * 只需传入一个正确的参数，就可以获取你所需要的对象
 * 无须知道其创建的细节。
 * ### 缺点
 * 工厂类的职责相对过重，增加新的产品时需要修改工厂类的判断逻辑，违背开闭原则。
 * 不易于扩展过于复杂的产品结构。
 */
public class SimpleFactory {

    public IFruit create(Class<? extends IFruit> clazz){
        try {
            if (clazz!=null){
                return clazz.newInstance();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
