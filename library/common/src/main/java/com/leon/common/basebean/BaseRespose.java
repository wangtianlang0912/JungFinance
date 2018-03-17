package com.leon.common.basebean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * des:封装服务器返回数据
 * Created by xsf
 * on 2016.09.9:47
 */
public class BaseRespose<T> implements Serializable {
    @SerializedName("error-code")
    public String code;
    @SerializedName("error")
    public String msg;

    public T data;

    public boolean success() {
        return code == null;
    }

    @Override
    public String toString() {
        return "BaseRespose{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
