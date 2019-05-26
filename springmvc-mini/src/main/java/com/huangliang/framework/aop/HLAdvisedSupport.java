package com.huangliang.framework.aop;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Data
public class HLAdvisedSupport {

    private Class targetClass;
    private Object target;
    private Pattern pointCutClassPattern;
    private transient Map<Method, List<Object>> methodCache;

    private HLAopConfig config;

    public HLAdvisedSupport(HLAopConfig config){
        this.config = config;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?>
            targetClass) throws Exception {
        List<Object> cached = methodCache.get(method);
        // 缓存未命中，则进行下一步处理
        if (cached == null) {
            Method m = targetClass.getMethod(method.getName(),method.getParameterTypes());
            cached = methodCache.get(m);
            // 存入缓存
            this.methodCache.put(m, cached);
        }
        return cached;
    }
    public boolean pointCutMatch(){
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }
}
