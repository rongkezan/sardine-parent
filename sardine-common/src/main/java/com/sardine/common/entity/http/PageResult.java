package com.sardine.common.entity.http;

import lombok.Data;

import java.util.List;

/**
 * 分页返回结果
 */
@Data
public class PageResult<T> {
    private Integer code;
    private String msg;
    private Long total;
    private Long totalPage;
    private List<T> items;

    public PageResult(){}

    public PageResult(Integer code, String msg, Long total, Long totalPage, List<T> items){
        this.code = code;
        this.msg = msg;
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

    public static <T> PageResult<T> success(String msg, Long total, Long totalPage, List<T> items){
        return new PageResult<>(ResultCodeEnum.SUCCESS.getCode(), msg, total, totalPage, items);
    }

    public static <T> PageResult<T> failed(String msg, Long total, Long totalPage, List<T> items){
        return new PageResult<>(ResultCodeEnum.FAILED.getCode(), msg, total, totalPage, items);
    }
}
