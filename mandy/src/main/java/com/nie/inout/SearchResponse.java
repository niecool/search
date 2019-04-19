package com.nie.inout;

public class SearchResponse implements java.io.Serializable {

    private static final long serialVersionUID = -4161652024606512336L;
    private int code;
    private String message;
    private String causeMsg;

    public boolean isSuccess() {
        return code == 0;
    }

    public void setError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCauseMsg() {
        return causeMsg;
    }

    public void setCauseMsg(String causeMsg) {
        this.causeMsg = causeMsg;
    }


}
