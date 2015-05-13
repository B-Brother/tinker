package com.alibaba.tinker.future;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * provider向注册中心发起通知后返回的结果future
 * 
 * @author beckham
 *
 */
public class RegisterSuccessFuture { 
	
	private boolean isReady = false; 
	 
	private CountDownLatch countDownLatch = new CountDownLatch(1);

	/**
	 * 数据回写
	 * 
	 * @param responseData
	 */
	public void putStatus(boolean isReady) {
		this.isReady = isReady;
		
		// 数据减少，await释放
		countDownLatch.countDown();
	}

	/**
	 * 获取response数据
	 * 
	 * @return
	 */
	public boolean get() {
		try {
			countDownLatch.await(3000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return isReady;
	}
}
