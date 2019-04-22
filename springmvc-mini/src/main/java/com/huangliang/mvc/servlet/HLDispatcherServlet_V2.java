package com.huangliang.mvc.servlet;

import com.huangliang.mvc.framework.annotation.HLAutowire;
import com.huangliang.mvc.framework.annotation.HLController;
import com.huangliang.mvc.framework.annotation.HLRequestMapping;
import com.huangliang.mvc.framework.annotation.HLService;
import com.huangliang.mvc.framework.context.HLApplicationContext;
import com.huangliang.mvc.framework.webmvc.HLHandlerAdapter;
import com.huangliang.mvc.framework.webmvc.HLHandlerMapping;
import com.huangliang.mvc.framework.webmvc.HLModelAndView;
import lombok.extern.slf4j.Slf4j;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class HLDispatcherServlet_V2 extends HttpServlet {

    //web.xml配置的内容
    private final String LOCATION = "contextConfigLocation";

    private HLApplicationContext context;

    private List<HLHandlerMapping> handlerMappings;

    private Map<HLHandlerMapping,HLHandlerAdapter> handlerAdapters = new HashMap<HLHandlerMapping,HLHandlerAdapter>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化容器
        context = new HLApplicationContext(config.getInitParameter(LOCATION));
        initStrategies(context);
    }

    private void initStrategies(HLApplicationContext context) {
        //有九种策略
        // 针对于每个用户请求，都会经过一些处理的策略之后，最终才能有结果输出
        // 每种策略可以自定义干预，但是最终的结果都是一致
        // ============= 这里说的就是传说中的九大组件 ================
        initMultipartResolver(context);//文件上传解析，如果请求类型是 multipart 将通过MultipartResolver 进行文件上传解析
        initLocaleResolver(context);//本地化解析
        initThemeResolver(context);//主题解析
        /** 实现这个 */
        //HLHandlerMapping 用来保存 Controller 中配置的 RequestMapping 和 Method 的一个对应关系
        initHandlerMappings(context);//通过 HandlerMapping，将请求映射到处理器
        /** 实现这个 */
        //HandlerAdapters 用来动态匹配 Method 参数，包括类转换，动态赋值
        initHandlerAdapters(context);//通过 HandlerAdapter 进行多类型的参数动态匹配
        initHandlerExceptionResolvers(context);//如果执行过程中遇到异常，将交给HandlerExceptionResolver 来解析
        initRequestToViewNameTranslator(context);//直接解析请求到视图名
        /** 实现这个 */
        //通过 ViewResolvers 实现动态模板的解析
        //自己解析一套模板语言
        initViewResolvers(context);//通过 viewResolver 解析逻辑视图到具体视图实现
        initFlashMapManager(context);//flash 映射管理器
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        //根据用户请求的 URL 来获得一个 Handler
        HLHandlerMapping handler = getHandler(req);
        if(handler == null){
            processDispatchResult(req,resp,new HLModelAndView("404"));
            return;
        }
        HLHandlerAdapter ha = getHandlerAdapter(handler);
        //这一步只是调用方法，得到返回值
        HLModelAndView mv = ha.handle(req, resp, handler);
        //这一步才是真的输出
        processDispatchResult(req,resp, mv);
    }

    private HLHandlerAdapter getHandlerAdapter(HLHandlerMapping handler) {
        return handlerAdapters.get(handler);
    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, HLModelAndView mv) {

    }

    private HLHandlerMapping getHandler(HttpServletRequest req) {
        String requestUrl = req.getRequestURI();
        String contextPath = req.getContextPath();
        requestUrl = requestUrl.replace(contextPath,"").replace("/+","/");
        for(HLHandlerMapping handlerMapping : handlerMappings){
            Matcher matcher = handlerMapping.getPattern().matcher(requestUrl);
            if(matcher.matches()){
                return handlerMapping;
            }
        }
        return null;
    }

    private void initFlashMapManager(HLApplicationContext context) {

    }

    private void initViewResolvers(HLApplicationContext context) {
    }

    private void initRequestToViewNameTranslator(HLApplicationContext context) {
    }

    private void initHandlerExceptionResolvers(HLApplicationContext context) {
    }

    private void initHandlerAdapters(HLApplicationContext context) {
        //在初始化阶段，我们能做的就是，将这些参数的名字或者类型按一定的顺序保存下来
        //因为后面用反射调用的时候，传的形参是一个数组
        //可以通过记录这些参数的位置 index,挨个从数组中填值，这样的话，就和参数的顺序无关了
        for (HLHandlerMapping handlerMapping : this.handlerMappings){
            //每一个方法有一个参数列表，那么这里保存的是形参列表
            this.handlerAdapters.put(handlerMapping,new HLHandlerAdapter());
        }
    }

    private void initHandlerMappings(HLApplicationContext context) {
        String[] beanNames = context.getBeanNames();
        for(String beanName : beanNames){
            Object obj = context.getBean(beanName);
            Class clazz = obj.getClass();
            if(!clazz.isAnnotationPresent(HLController.class)){continue;}
            String baseUrl = "";
            if (clazz.isAnnotationPresent(HLRequestMapping.class)) {
                HLRequestMapping annotation = (HLRequestMapping) clazz.getAnnotation(HLRequestMapping.class);
                baseUrl = annotation.value();
            }
            Method[] methods = clazz.getMethods();
            for(Method m : methods){
                if(!m.isAnnotationPresent(HLRequestMapping.class)){continue;}
                HLRequestMapping annotation =m.getAnnotation(HLRequestMapping.class);
                baseUrl  = baseUrl + annotation.value();
                String regex = ("/" + baseUrl + annotation.value().replaceAll("\\*",
                        ".*")).replaceAll("/+", "/");
                Pattern pattern = Pattern.compile(regex);
                this.handlerMappings.add(new HLHandlerMapping(obj,m,pattern));
                System.out.println("Mapping: " + regex + " , " + m);
            }
        }
    }

    private void initThemeResolver(HLApplicationContext context) {
    }

    private void initLocaleResolver(HLApplicationContext context) {
    }

    private void initMultipartResolver(HLApplicationContext context) {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
