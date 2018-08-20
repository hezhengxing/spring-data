package com.example.data.config;

/**
 * @Author: hzx
 * @Date: 2018/8/13 16:08
 * @Description:
 * @Modify By:
 */
public class BusinessException extends RuntimeException{
    private int errorCode;

    public BusinessException() {
    }

    public BusinessException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
