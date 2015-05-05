package com.alibaba.tinker.ex;

public class TinkerWithoutPublisherException extends RuntimeException{ 
	
    private static final long serialVersionUID = 883132628243816501L;

    private String code;

    public TinkerWithoutPublisherException(){
        super();
    }

    public TinkerWithoutPublisherException(String msg){
        super(msg);
    }

    public TinkerWithoutPublisherException(Throwable th){
        super(th);
    }

    public TinkerWithoutPublisherException(String msg, Throwable th){
        super(msg, th);
    }

    public TinkerWithoutPublisherException(String code, String msg){
        super(msg);
        this.code = code;
    }

    public TinkerWithoutPublisherException(String code, String msg, Throwable th){
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
