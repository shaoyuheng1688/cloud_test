package com.raymon.taxguide.lock;


public abstract class ResourceLock implements IResourceLock {
	
	private String resourceName;
	
	public ResourceLock(String resourceName){
		this.resourceName = resourceName;
	}
	
	public String getResourceName(){
		return this.resourceName;
	}
}
