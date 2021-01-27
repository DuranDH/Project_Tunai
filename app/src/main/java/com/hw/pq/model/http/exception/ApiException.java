package com.hw.pq.model.http.exception;

/**
 * Created by codeest on 2016/8/4.
 */
public class ApiException extends Exception {

    private int code;
    private String displayMessage;

    public ApiException(String msg)
    {
        super(msg);
    }

    public ApiException(Throwable throwable, int code){

        super(throwable);
        this.code = code;
    }

    public void setDisplayMessage(String displayMessage){
        this.displayMessage = displayMessage;
    }

    public String getDisplayMessage(){
        return displayMessage;
    }

    public int getCode(){
        return code;
    }
}
