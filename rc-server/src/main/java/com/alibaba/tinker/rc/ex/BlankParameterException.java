package com.alibaba.tinker.rc.ex;

/**
 * 空参数异常
 * 
 * @author yingchao.zyc
 *
 */
public class BlankParameterException extends RuntimeException{
	
    private static final long serialVersionUID = 883132628243816501L;

    private String code;

    public BlankParameterException(){
        super();
    }

    public BlankParameterException(String msg){
        super(msg);
    }

    public BlankParameterException(Throwable th){
        super(th);
    }

    public BlankParameterException(String msg, Throwable th){
        super(msg, th);
    }

    public BlankParameterException(String code, String msg){
        super(msg);
        this.code = code;
    }

    public BlankParameterException(String code, String msg, Throwable th){
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
