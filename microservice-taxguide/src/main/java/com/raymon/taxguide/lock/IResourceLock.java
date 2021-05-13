package com.raymon.taxguide.lock;

import java.util.concurrent.TimeUnit;

public interface IResourceLock {
	public String getResourceName();
	public void lock();

	public boolean tryLock();
	
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
	
	public void unlock();
}