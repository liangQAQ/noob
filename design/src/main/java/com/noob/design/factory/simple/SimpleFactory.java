package com.noob.design.factory.simple;

import com.noob.design.factory.IFruit;

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
