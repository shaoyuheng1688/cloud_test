package com.raymon.frame.web.aop;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Proxy;
import java.util.List;

public class PackageBeanNameAutoProxyCreator extends BeanNameAutoProxyCreator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3483210158535286472L;
	
	private List<String> includePackages;
	private List<String> excludePackages;

	
	private boolean isSubPackage(String sub, String parent){
		if(sub.startsWith(parent))
			return true;
		else
			return false;
	}
	
	private boolean shouldProxy(Class<?> beanClass, String beanName){
		if(Proxy.isProxyClass(beanClass))
			return true;
		String beanPackage = ClassUtils.getPackageName(beanClass);
		// includes 无配置时, 默认代理(should == true); 配置时, 默认不代理(should == false)
		boolean should = includePackages == null ? true : false;
		String included = null;
		if(includePackages != null){
			for(String pn : includePackages){
				if(isSubPackage(beanPackage, pn)){
					should = true;
					included = pn;
					break;
				}
			}
		}
		if(excludePackages != null){
			if(should == true){
				for(String expn : excludePackages){
					if(isSubPackage(beanPackage, expn)){
						if(included != null){
							if(isSubPackage(included, expn)){
								should = true;
								break;
							}else if(isSubPackage(expn, included)){
								should = false;
								break;
							}
						} else {
							should = false;
							break;
						}
					}
				}
			}
		}
		
		return should;
	}
	
	@Override
	protected boolean shouldSkip(Class<?> beanClass, String beanName) {
		return (!shouldProxy(beanClass, beanName)) || super.shouldSkip(beanClass, beanName);
	}

	public void setIncludePackages(List<String> includePackages) {
		this.includePackages = includePackages;
	}

	public void setExcludePackages(List<String> excludePackages) {
		this.excludePackages = excludePackages;
	}

	

}
