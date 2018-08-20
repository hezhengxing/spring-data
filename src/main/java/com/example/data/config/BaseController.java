package com.example.data.config;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: hzx
 * @Date: 2018/8/13 15:59
 * @Description:
 * @Modify By:
 */
public class BaseController {
    public BaseController() {
    }

    public String getUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Object o = request.getAttribute("userId");
        if (o == null) {
            throw new BusinessException(300, "请先登录");
        } else {
            return (String)o;
        }
    }

    public BaseResult sendResult200() {
        return new BaseResult(200, "success");
    }

    public BaseResult sendResult200(Object data) {
        return new BaseResult(200, "success", data);
    }

    public BaseResult sendResult400() {
        return new BaseResult(400, "parameter error");
    }

    public BaseResult sendResult400(Object data) {
        return new BaseResult(400, "parameter error", data);
    }

    public BaseResult sendResult500() {
        return new BaseResult(500, "server error");
    }

    public BaseResult sendResult500(Object data) {
        return new BaseResult(500, "server error", data);
    }

    public BaseResult sendResult(int code, String message) {
        return new BaseResult(code, message);
    }

    public BaseResult sendResult(int code, String message, Object data) {
        return new BaseResult(code, message, data);
    }

    public BaseResult sendResult(int code, String message, Object data, String mark) {
        if (data == null) {
            if (mark.equals("object")) {
                data = new JSONObject();
            } else if (mark.equals("collection")) {
                data = new JSONArray();
            }
        }

        return new BaseResult(code, message, data);
    }
}
