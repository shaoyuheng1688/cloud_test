package com.raymon.frame.web.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggingHandlerExceptionResolver implements
		HandlerExceptionResolver {

	private static Logger logger = LoggerFactory.getLogger(LoggingHandlerExceptionResolver.class);
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		logger.error("Catch exception: ", ex);
		return null;
	}

}
