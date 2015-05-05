package com.alibaba.tinker.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * 和IP地址相关的操作工具类
 * 
 * @author yingchao.zyc
 *
 */
public class IpAddressUtil {
	
	private static final String LOCALHOSTIP = "127.0.0.1";
	
	@SuppressWarnings("rawtypes")
	public static String getHostIp() {
		Enumeration netInterfaces = null;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			return getLocalhostIp();
		}
		InetAddress ip = null;
		while (netInterfaces.hasMoreElements()) {
			NetworkInterface ni = (NetworkInterface) netInterfaces
					.nextElement();
			if (ni.getInetAddresses().hasMoreElements()) {
				ip = (InetAddress) ni.getInetAddresses().nextElement();
				if (!ip.isLoopbackAddress()
						&& ip.getHostAddress().indexOf(":") == -1) {
					String _ip = ip.getHostAddress();
					if (!LOCALHOSTIP.equals(_ip)) {
						return _ip;
					}
				}
			}
		}

		return getLocalhostIp();
	}

	/**
	 * 获取本地IP
	 * 
	 * @return
	 */
	private static String getLocalhostIp() {
		try {
			return InetAddress.getByName(
					InetAddress.getLocalHost().getHostName()).getHostAddress();
		} catch (UnknownHostException e) {
			return LOCALHOSTIP;
		}
	}
	
	public static void main(String[] args) {
		String[] arr = getHostIp().split("\\.");
		for(String s : arr){
			System.out.println(s);
		}
	}
}

















