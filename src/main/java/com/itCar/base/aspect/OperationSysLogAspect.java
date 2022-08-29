package com.itCar.base.aspect;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.itCar.base.annotation.OperationLog;
import com.itCar.base.api.log.entity.MyLog;
import com.itCar.base.api.log.service.MyLogService;
import com.itCar.base.api.log.utils.LogUtils;
import com.itCar.base.api.log.utils.RequestHolder;
import com.itCar.base.tools.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @ClassName: OperationSysLogAspect 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/28 16:20
 * @Version: v1.0
 */
@Component
@Aspect
@Slf4j
public class OperationSysLogAspect {

    @Autowired
    private MyLogService service;

    @Pointcut("@annotation(com.itCar.base.annotation.OperationLog)")
    public void logPoinCut() {
    }

    //切面 配置通知
//    @AfterReturning("logPoinCut()") // 返回增强：目标方法执行return之后返回结果之前调用增强方法，如果出异常则不执行
    @Before("logPoinCut()") // 前置增强：目标方法执行之前先调用增强方法
    public void saveSysLog(JoinPoint joinPoint) {
        MyLog log = this.a(joinPoint);
        service.addOperationLog(log);
    }


    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "logPoinCut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        MyLog log = this.a(joinPoint);
        log.setType("ERROR");
        log.setExceptionDetail(LogUtils.getStackTrace(e));
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        service.addOperationLog(log);
//        logService.save(SecurityUtils.getCurrentUsername(), LogUtils.getBrowser(request), LogUtils.getIp(request), (ProceedingJoinPoint)joinPoint, log);
    }



    public MyLog a(JoinPoint joinPoint){
        //获取到请求的属性
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取到请求对象
        HttpServletRequest request = attributes.getRequest();
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取操作的方法名
        OperationLog operationLog = method.getAnnotation(OperationLog.class);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();

        MyLog log = new MyLog();
        String date = DateUtil.getDate(0);
        log.setId(uuid);
        log.setUserName(username);
        log.setIp(LogUtils.getIp(request));
        log.setExecutionTime(date);
        log.setType("INFO");
        if (operationLog != null) {
            String value = operationLog.value();
            String moduleName = operationLog.moduleName();
            log.setDescription(value); // 保存获取的操作方法名
            log.setModuleName(moduleName); // 保存获取的模快名
        }
        //获取请求的方法名
        String methodName = method.getName();
        log.setMethod(methodName);
        String requestURI = request.getRequestURI();
        log.setDoInterfacePath(requestURI);
        // 请求的参数
        Map<String, Object> rtnMap = this.converMap(request.getParameterMap(), joinPoint);
        String reqParam = JSONUtils.toJSONString(rtnMap);
        log.setParams(reqParam);

        return log;
    }


    public static Map<String, Object> converMap(Map<String, String[]> paramMap, JoinPoint joinPoint) {
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
//        if (rtnMap.size() <= 0) {
//            String parameter = getRequestParameter(joinPoint);
//            rtnMap.put("key", parameter);
//        }
        return rtnMap;
    }

    public static String getRequestParameter(JoinPoint joinPoint) {
        //请求的参数
        Object[] args = joinPoint.getArgs();
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                //ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
                continue;
            }
            arguments[i] = args[i];
        }
        String paramter = "";
        if (arguments != null) {
            try {
                Object argument = arguments[0];
                Map<String, Object> map = Obj2Map(argument);
                paramter = map.toString();
                paramter = JSONObject.toJSONString(arguments);
            } catch (Exception e) {
                paramter = arguments.toString();
            }
        }
        return paramter;
    }

    public static Map<String, Object> Obj2Map(Object obj) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }

}

