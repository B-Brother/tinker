package com.alibaba.tinker.ex;

/**
 * 连接异常
 * 
 * @author yingchao.zyc
 *
 */
public class TinkerConnectException extends RuntimeException{ 
	
    private static final long serialVersionUID = 883132628243816501L;

    private String code;

    public TinkerConnectException(){
        super();
    }

    public TinkerConnectException(String msg){
        super(msg);
    }

    public TinkerConnectException(Throwable th){
        super(th);
    }

    public TinkerConnectException(String msg, Throwable th){
        super(msg, th);
    }

    public TinkerConnectException(String code, String msg){
        super(msg);
        this.code = code;
    }

    public TinkerConnectException(String code, String msg, Throwable th){
        super(msg, th);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
