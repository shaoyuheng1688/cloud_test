package com.raymon.frame.web.aop;

import com.raymon.frame.utils.DateTimeUtils;
import com.raymon.frame.web.exception.ApplicationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Configuration
public class NormalControllerExceptionAdvice {

    static Logger log = LoggerFactory.getLogger(NormalControllerExceptionAdvice.class);

    @Pointcut(value = "execution(* com.raymon.*.web.controller.*Controller..*(..)) && args(model,req, ..)", argNames = "model,req")
    public void pointcut(Model model, HttpServletRequest req) {

    }

    @Around(value = "pointcut(model, req)")
    public Object around(ProceedingJoinPoint pjp, Model model, HttpServletRequest req) throws Throwable {
        String viewName;
        long startTime = DateTimeUtils.currentTimeInMillisecond();
        try {
            viewName = (String) pjp.proceed(pjp.getArgs());
            long endTime = DateTimeUtils.currentTimeInMillisecond();
            log.info("test:viewName = " + viewName + ", time=" + (endTime - startTime));
        } catch (Throwable ex) {
            if (ex instanceof ApplicationException) {
                log.error("Application error, " + ((ApplicationException) ex).getMessage());
            } else
                log.error("Controller error, redirect to error view.", ex);
            model.addAttribute("error", new RuntimeException(ex.getMessage()));
            viewName = resolverViewName(req, "error");
            long endTime = DateTimeUtils.currentTimeInMillisecond();
            log.info("test:viewName = " + viewName + ", time=" + (endTime - startTime));
        }
        return viewName;
    }

    protected String resolverViewName(HttpServletRequest req, String viewName) {
        String uri = req.getRequestURI();
        log.info(uri + " from " + req.getLocalAddr());
        int n = uri.lastIndexOf(".");
        String suffix = "";
        if (n > -1) suffix = uri.substring(n);
        return viewName + suffix;
    }
}

