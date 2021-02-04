package com.sardine.cookbook.app.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author keith
 */
@Data
@Builder
public class Cookbook {
    private Long id;

    private String bookName;
}
