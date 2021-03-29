package com.raymon.frame.lock;

public interface ResourceLockFactory {
	public ResourceLock newLock(String resourceName);
}
