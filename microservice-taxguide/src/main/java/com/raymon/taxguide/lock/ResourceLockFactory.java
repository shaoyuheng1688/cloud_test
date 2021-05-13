package com.raymon.taxguide.lock;

public interface ResourceLockFactory {
	public ResourceLock newLock(String resourceName);
}
