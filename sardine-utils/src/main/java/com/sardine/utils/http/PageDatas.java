package com.sardine.utils.http;

import java.util.List;

/**
 * @author keith
 */
public class PageDatas {

    public static <T> PageData<T> newPageData(int total, int size, int current, List<T> records) {
        PageData<T> pageData = new PageData<>();
        pageData.setTotal(total);
        pageData.setSize(size);
        pageData.setPages(total % size == 0 ? total / size : (total / size) + 1);
        pageData.setCurrent(current);
        pageData.setRecords(records);
        return pageData;
    }
}
