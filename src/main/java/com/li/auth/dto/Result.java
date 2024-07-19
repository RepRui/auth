package com.li.auth.dto;

public class Result {
    private boolean success = true;
    private int code;
    private String msg;
    private Object data;

    public static Result getInstance(boolean success, int code, String msg, Object data){
        Result resultObject = new Result();
        resultObject.setCode(code);
        resultObject.setMsg(msg);
        resultObject.setSuccess(success);
        resultObject.data = data;
        return resultObject;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{\"success\":"+success+",\"code\":"+code+",\"msg\":"+msg+",\"data\":"+data+"}";
    }
}
