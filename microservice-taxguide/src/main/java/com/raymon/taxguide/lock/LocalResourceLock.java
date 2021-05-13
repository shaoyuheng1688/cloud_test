package com.raymon.taxguide.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LocalResourceLock extends ResourceLock {

	
	private Lock lock = new ReentrantLock();
	
	private Long timeWait;
	
	
	/**
	 * 
	 * @param resourceName
	 */
	public LocalResourceLock(String resourceName) {
		this(resourceName, -1, null);
	}
	
	/**
	 * construct LocalResourceLock by defaultTimeWait setting.
	 * NULL unit also means INFINTE timewait.
	 * @param resourceName
	 * @param unit
	 */
	public LocalResourceLock(String resourceName, long maxTimeWait, TimeUnit unit) {
		super(resourceName);
		this.timeWait = (unit != null) ? unit.toMillis(maxTimeWait) : null;
	}

	@Override
	public void lock() {
		if(timeWait == null){
			lock.lock();
		}else{
			try {
				if(!lock.tryLock(timeWait, TimeUnit.MILLISECONDS)){
					throw new IllegalStateException("Get LocalResourceLock Timeout, name: " + this.getResourceName() + ", timewait: " + timeWait);
				};
			} catch (InterruptedException e) {
				throw new IllegalStateException("Get LocalResourceLock Interrupted, name: " + this.getResourceName(),  e);
			}
		}
	}

	@Override
	public boolean tryLock() {
		return lock.tryLock();
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return lock.tryLock(time, unit);
	}

	@Override
	public void unlock() {
		lock.unlock();
	}
	
	

}
