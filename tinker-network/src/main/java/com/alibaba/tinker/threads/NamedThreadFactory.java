package com.alibaba.tinker.threads;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory{

	private static final AtomicInteger poolNumber = new AtomicInteger(1);
	
	private final AtomicInteger threadNumber = new AtomicInteger(1);
	
	private final String namePrefix = null;
	
	private final boolean isDeamon;
	
	public NamedThreadFactory(String namePrefix, boolean deamon) { 
		namePrefix = namePrefix + "-" + poolNumber.incrementAndGet() + "-thread-";
		isDeamon = deamon;
	}
	
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r, namePrefix + threadNumber.incrementAndGet());
		t.setDaemon(isDeamon);
		return t;
	}

}
