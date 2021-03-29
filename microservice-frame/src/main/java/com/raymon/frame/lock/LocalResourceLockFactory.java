package com.raymon.frame.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class LocalResourceLockFactory implements ResourceLockFactory, InitializingBean  {

	private static Logger log = LoggerFactory.getLogger(LocalResourceLockFactory.class);
	
	private long maxLockWaitMilliSeconds = 10 * 1000L;
	
	@Override
	public ResourceLock newLock(String resourceName) {
		return new LocalResourceLock(resourceName, maxLockWaitMilliSeconds, TimeUnit.MILLISECONDS);
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("MaxLockWaitMilliSeconds is {}ms", maxLockWaitMilliSeconds);
	}


	public void setMaxLockWaitMilliSeconds(long maxLockWaitMilliSeconds) {
		this.maxLockWaitMilliSeconds = maxLockWaitMilliSeconds;
	}

}
