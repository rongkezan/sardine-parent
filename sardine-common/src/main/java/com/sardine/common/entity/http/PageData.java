package com.sardine.common.entity.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Common return value of page
 *
 * @author keith
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageData<T> implements Serializable {

    private static final long serialVersionUID = -8940366960899264820L;

    /** Total record count */
    private int total;

    /** Page size */
    private int size;

    /** Total pages */
    private int pages;

    /** Current page */
    private int current;

    /** data records */
    private List<T> records;
}
