package com.raymon.taxguide.web.controller;


import com.raymon.taxguide.web.view.CustomTimestampEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AbstractController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	protected static final String SOURCE_KEY = "queue.sourcekey";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Timestamp.class, new CustomTimestampEditor(timeFormat, false, 8));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	protected String resolverViewName(HttpServletRequest req, String viewName) {
		String uri = req.getRequestURI();
		log.info(uri + " from " + req.getLocalAddr());
		int n = uri.lastIndexOf(".");
		String suffix = "";
		if (n > -1) {
			suffix = uri.substring(n);
		}
		return viewName + suffix;
	}
}
