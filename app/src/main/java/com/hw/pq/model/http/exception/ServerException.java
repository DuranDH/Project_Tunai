package com.hw.pq.model.http.exception;

/**
 * Created by xiejian on 2017/6/20.
 */

public class ServerException extends RuntimeException {

    private int code;
    private String displayMessage;

    public ServerException(int code, String message)
    {
        this.code = code;
        this.displayMessage = message;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public int getCode() {
        return code;
    }
}
