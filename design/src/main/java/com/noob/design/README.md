工厂：隐藏对象的创建过程，暴露出创建方法
单例：只能存在唯一的一个对象
原型：以一个对象为标准复制出另一个对象
代理：隐藏对象的直接访问，通过代理来进行保护和增强
委派：自己不干活，委派给别人
策略：简化大量的ifelse逻辑
模板：有一个统一的流程
适配：一种兼容
装饰：层层包装
观察：一类事件的监听与触发

AOP:
    
    public class WebLogAspect {

    @Pointcut("execution(public * com.huangliang.cloudpushportal..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        System.out.println("我是前置通知");
        Object[] obj = joinPoint.getArgs();//获取目标方法的参数信息
        joinPoint.getThis(); // AOP代理类信息
        joinPoint.getTarget(); // 代理的目标对象
        Signature signature = joinPoint.getSignature(); //  用的最多，通知的签名
        System.out.println("代理的方法是 ： " + signature.getName()); //  打印 代理的是哪一个方法
        // AOP 代理的名字
        System.out.println("AOP 代理的名字 ： " + signature.getDeclaringTypeName());
        signature.getDeclaringType();

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("RESPONSE : " + ret);
    }
    }
IOC:
    
    @RestController
    public class MessageController {
    
        @Autowired
        private MessageService messageService;
    
        @RequestMapping(value="/message/send",method = RequestMethod.POST)
        public String send(@RequestBody @Valid SendFrom form){
            messageService.execute(form);
            System.out.println(form);
    
            return form.toString();
        }
    }