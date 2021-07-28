package com.sardine.utils.http;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 游标结果集封装
 *
 * @author keith
 */
@Data
public class CursorData<T> implements Serializable {

    private static final long serialVersionUID = -9172090008158364734L;

    /** 总记录数 */
    private int total;

    /** 数据列表集合 */
    private List<T> records;
}
