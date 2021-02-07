package com.sardine.common.entity.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页返回结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    /* 返回码 */
    private int code;

    /* 返回信息 */
    private String msg;

    /* 总记录数 */
    private int total;

    /* 总页数 */
    private int totalPage;

    /* 记录 */
    private List<T> records;

    public static <T> PageResult<T> success(String msg, int total, int totalPage, List<T> records){
        return new PageResult<>(ResultCodeEnum.SUCCESS.getCode(), msg, total, totalPage, records);
    }

    public static <T> PageResult<T> failed(String msg, int total, int totalPage, List<T> records){
        return new PageResult<>(ResultCodeEnum.FAILED.getCode(), msg, total, totalPage, records);
    }
}
