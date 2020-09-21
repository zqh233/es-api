package com.yincieryi.esapi.enums;

/**
 * @Description:
 * @Author: chen qiang
 * @Date: 2020/9/21 20:38
 */
public enum BusinessError {
    SUCCESS(10000,"成功"),

    ES_INDEX_DELETE_FAILED(20001,"es索引删除失败"),
    ES_INDEX_EXIST(20002,"es索引已存在"),
    ES_INDEX_CREATE_FAILED(20001,"es索引创建失败"),
    ;
    /**
     * 错误码
     */
    private int code;

    /**
     * 错误信息
     */
    private String msg;

    BusinessError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
