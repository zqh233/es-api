package com.yincieryi.esapi.exception;

import lombok.Getter;
import lombok.ToString;

/**
 * @Description:业务异常
 * @Author: chen qiang
 * @Date: 2020/9/21 20:33
 */
@Getter
@ToString
public class BusinessException extends RuntimeException{
    /**
     * 异常码
     */
    private int code;

    /**
     * 异常内容
     */
    private String msg;

    public BusinessException(int code, String msg){
        super();
        this.code = code;
        this.msg = msg;
    }
}
