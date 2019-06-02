package com.huangliang.mvc.servlet;

import com.huangliang.framework.annotation.HLController;
import com.huangliang.framework.annotation.HLRequestMapping;
import com.huangliang.framework.beans.HLBeanDefinition;
import com.huangliang.framework.context.HLApplicationContext;
import com.huangliang.framework.webmvc.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HLDispatcherServlet_V2 extends HttpServlet {

    //web.xml配置的内容
    private final String LOCATION = "contextConfigLocation";

    private HLApplicationContext context;

    private List<HLHandlerMapping> handlerMappings = new ArrayList<>();

    private Map<HLHandlerMapping,HLHandlerAdapter> handlerAdapters = new HashMap<HLHandlerMapping,HLHandlerAdapter>();

    private List<HLViewResolver> viewResolvers = new ArrayList<HLViewResolver>();

    @Override
    public void init(ServletConfig config) {
        System.out.println("初始化容器");
        //初始化容器
        context = new HLApplicationContext(config.getInitParameter(LOCATION));
        try {
            initStrategies(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initStrategies(HLApplicationContext context) throws Exception {
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

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, HLModelAndView mv) throws Exception {
        //调用 viewResolver 的 resolveView 方法
        if(null == mv){ return;}
        if(this.viewResolvers.isEmpty()){ return;}
        if (this.viewResolvers != null) {
            for (HLViewResolver viewResolver : this.viewResolvers) {
                HLView view = viewResolver.resolveViewName(mv.getViewName(), null);
                if (view != null) {
                    view.render(mv.getModel(),req,resp);
                    return;
                }
            }
        }
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
        //在页面敲一个 http://localhost/first.html
        //解决页面名字和模板文件关联的问题
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath =
                this.getClass().getClassLoader().getResource(templateRoot).getFile();
        File templateRootDir = new File(templateRootPath);
        for (File template : templateRootDir.listFiles()) {
            this.viewResolvers.add(new HLViewResolver(templateRoot));
        }
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

    private void initHandlerMappings(HLApplicationContext context) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        String[] beanNames = context.getBeanNames();
        List<String> beanNames = context.getBeanNamesList();
        for(String simpleBeanName : beanNames){
            Object controller = context.getBean(simpleBeanName);
            Class clazz = controller.getClass();
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
                String regex = ("/" + baseUrl + annotation.value().replaceAll("\\*",
                        ".*")).replaceAll("/+", "/");
                Pattern pattern = Pattern.compile(regex);
                this.handlerMappings.add(new HLHandlerMapping(controller,m,pattern));
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
