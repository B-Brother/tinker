package com.alibaba.tinker.ex;

/**
 * 协议处理异常
 * 
 * @author yingchao.zyc
 *
 */
public class TinkerProtocolParseException extends RuntimeException{ 
	
    private static final long serialVersionUID = 883132628243816501L;

    private String code;

    public TinkerProtocolParseException(){
        super();
    }

    public TinkerProtocolParseException(String msg){
        super(msg);
    }

    public TinkerProtocolParseException(Throwable th){
        super(th);
    }

    public TinkerProtocolParseException(String msg, Throwable th){
        super(msg, th);
    }

    public TinkerProtocolParseException(String code, String msg){
        super(msg);
        this.code = code;
    }

    public TinkerProtocolParseException(String code, String msg, Throwable th){
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
