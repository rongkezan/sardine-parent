package com.sardine.common.entity.http;

import java.util.List;

/**
 * @author keith
 */
public class CursorDatas {

    public static <T> CursorData<T> newCursorData(int totalCount, List<T> records) {
        CursorData<T> cursorData = new CursorData<>();
        cursorData.setTotal(totalCount);
        cursorData.setRecords(records);
        return cursorData;
    }
}
