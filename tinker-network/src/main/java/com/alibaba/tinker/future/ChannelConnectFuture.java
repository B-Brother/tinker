package com.alibaba.tinker.future;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 通道连接Future。当前专值客户端和服务端的连接。
 * 
 * @author beckham
 *
 */
public class ChannelConnectFuture {
 
	private boolean isReady;
	   
	// countdown数据
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
	public Object get(String serviceName) {
		try {
			countDownLatch.await(3000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return isReady;
	}
}
