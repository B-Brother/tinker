package com.alibaba.tinker.ex;

/**
 * 初始化异常
 * 
 * @author yingchao.zyc
 *
 */
public class TinkerInitException extends RuntimeException{ 
	
    private static final long serialVersionUID = 883132628243816501L;

    private String code;

    public TinkerInitException(){
        super();
    }

    public TinkerInitException(String msg){
        super(msg);
    }

    public TinkerInitException(Throwable th){
        super(th);
    }

    public TinkerInitException(String msg, Throwable th){
        super(msg, th);
    }

    public TinkerInitException(String code, String msg){
        super(msg);
        this.code = code;
    }

    public TinkerInitException(String code, String msg, Throwable th){
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
