package com.alibaba.tinker.ex;

/**
 * 线程处理异常
 * 
 * @author yingchao.zyc
 *
 */
public class TinkerThreadException extends RuntimeException{ 
	
    private static final long serialVersionUID = 883132628243816501L;

    private String code;

    public TinkerThreadException(){
        super();
    }

    public TinkerThreadException(String msg){
        super(msg);
    }

    public TinkerThreadException(Throwable th){
        super(th);
    }

    public TinkerThreadException(String msg, Throwable th){
        super(msg, th);
    }

    public TinkerThreadException(String code, String msg){
        super(msg);
        this.code = code;
    }

    public TinkerThreadException(String code, String msg, Throwable th){
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
