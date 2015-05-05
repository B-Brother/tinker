package com.alibaba.tinker.ex;

public class TinkerTimeoutException extends Exception{ 
	
    private static final long serialVersionUID = 883132628243816501L;

    private String code;

    public TinkerTimeoutException(){
        super();
    }

    public TinkerTimeoutException(String msg){
        super(msg);
    }

    public TinkerTimeoutException(Throwable th){
        super(th);
    }

    public TinkerTimeoutException(String msg, Throwable th){
        super(msg, th);
    }

    public TinkerTimeoutException(String code, String msg){
        super(msg);
        this.code = code;
    }

    public TinkerTimeoutException(String code, String msg, Throwable th){
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