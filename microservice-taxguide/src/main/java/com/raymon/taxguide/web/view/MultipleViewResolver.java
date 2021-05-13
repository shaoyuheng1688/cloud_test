package com.raymon.taxguide.web.view;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.Locale;
import java.util.Map;

public class MultipleViewResolver extends UrlBasedViewResolver {

	private Map<String, ViewResolver> resolvers;

	@Override
	protected View loadView(String viewName, Locale locale) throws Exception {
		return internalResolveViewName(viewName, locale);
	}

	public View internalResolveViewName(String viewName, Locale locale)
			throws Exception {
		//System.out.print(viewName);
		int n = viewName.lastIndexOf(".");
		if (n == -1) {
			return null;
		}
		String suffix = viewName.substring(n);
		viewName = viewName.substring(0, n);

		String resolverViewKey = "";
		for (String key : resolvers.keySet()) {
			String[] arr = key.split(",");
			for (String subViewName : arr) {
				if (subViewName.trim().equals(suffix)) {
					resolverViewKey = key;
					break;
				}
			}
		}

		ViewResolver resolver = resolvers.get(resolverViewKey);
		if (resolver != null) {
			return resolver.resolveViewName(viewName, locale);
		}

		return null;
	}

	public Map<String, ViewResolver> getResolvers() {
		return resolvers;
	}

	public void setResolvers(Map<String, ViewResolver> resolvers) {
		this.resolvers = resolvers;
	}

}
