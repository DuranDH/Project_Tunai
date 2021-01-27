package com.hw.pq.model.bean;


import com.google.gson.annotations.SerializedName;

public class BaseModel<T> {
    /**
     * status : true
     * errcode : 0
     * message : success
     * data : {}
     */
    @SerializedName("status")
    private boolean status;
    private String msg;
    private T data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
