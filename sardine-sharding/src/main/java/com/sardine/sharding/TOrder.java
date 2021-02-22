package com.sardine.sharding;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TOrder {

    private Long id;

    private String name;
}
