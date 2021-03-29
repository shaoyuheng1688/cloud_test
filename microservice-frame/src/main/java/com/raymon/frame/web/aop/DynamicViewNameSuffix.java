package com.raymon.frame.web.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截controller调用, 根据uri上后缀, 动态添加viewName后缀, 以实现动态view输出
 * <!-- 根据uri上后缀, 动态添加view后缀 -->
 * <bean id="dynamicViewNameSuffixAdvice" class="com.raymon.web.aop.DynamicViewNameSuffix" />
 * <aop:config>
 * <aop:aspect ref="dynamicViewNameSuffixAdvice">
 * <aop:around method="around"
 * pointcut="execution(* org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(..)) and args(request,response,handler)"
 * arg-names="request,response,handler"/>
 * </aop:aspect>
 * </aop:config>
 *
 * @author Chan
 */

@Aspect
@Component
public class DynamicViewNameSuffix {

	@Pointcut(value = "execution(* org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(..)) && args(request,response,handler)", argNames = "request,response,handler")
	public void pointcut(HttpServletRequest request, HttpServletResponse response, Object handler) {

	}

	@Around(value = "pointcut(request,response,handler)")
	public Object around(ProceedingJoinPoint pjp, HttpServletRequest request, HttpServletResponse response, Object handler) throws Throwable {
		ModelAndView retVal = (ModelAndView) pjp.proceed();
		if (retVal != null) {
			String viewName = retVal.getViewName();
			if (viewName != null && viewName.lastIndexOf(".") < 0) {
				String newViewName = resolverViewName(request, viewName);
				retVal.setViewName(newViewName);
			}
		}
		return retVal;
	}

	protected String resolverViewName(HttpServletRequest request, String viewName) {
		String uri = request.getRequestURI();
		int n = uri.lastIndexOf(".");
		String suffix = "";
		if (n > -1)
			suffix = uri.substring(n);
		return viewName + suffix;
	}
}
