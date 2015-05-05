package com.alibaba.tinker.ex;

/**
 * 序列化异常
 * 
 * @author yingchao.zyc
 *
 */
public class TinkerSerializationException extends Exception{ 
	
    private static final long serialVersionUID = 883132628243816501L;

    private String code;

    public TinkerSerializationException(){
        super();
    }

    public TinkerSerializationException(String msg){
        super(msg);
    }

    public TinkerSerializationException(Throwable th){
        super(th);
    }

    public TinkerSerializationException(String msg, Throwable th){
        super(msg, th);
    }

    public TinkerSerializationException(String code, String msg){
        super(msg);
        this.code = code;
    }

    public TinkerSerializationException(String code, String msg, Throwable th){
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
