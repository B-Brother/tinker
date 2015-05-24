package com.alibaba.tinker.threads;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.alibaba.tinker.ex.TinkerThreadException;

public class ThreadPoolManager {
	private static final long KEEP_ALIVE_TIME = 300L;
	
	private static final int MAX_THREAD_COUNT = 600;
	
	private static ThreadPoolManager instance = null; 
	
	private int idleCount = MAX_THREAD_COUNT;
	
	private ThreadFactory threadFactory = null;
	
	private final RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
	
	private final Map<String, ThreadPoolExecutor> poolCache = new HashMap<String, ThreadPoolExecutor>();
	 
	private ThreadPoolManager(){
		threadFactory = new NamedThreadFactory("TinkerBizProcessor", true); 
	}
	
	public static ThreadPoolManager getInstance(){
		if(instance == null){
			instance = new ThreadPoolManager();
		}
		
		return instance;
	}
	
	public void allocateThreadPool(String serviceName, int corePoolSize, int maximumPoolSize){
		if(poolCache.get(serviceName) != null){
			throw new TinkerThreadException("已经为该服务分配了线程池，请勿再次分配!");
		}
		
		if(idleCount < maximumPoolSize){
			throw new TinkerThreadException("系统线程已不够再次分配，请重新设置!");
		}
		
		ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>(), threadFactory, handler);
		poolCache.put(serviceName, executor);
		
		idleCount = idleCount - maximumPoolSize;
	}
	
	public Executor getExecutor(String serviceName){
		return poolCache.get(serviceName);
	}
}












