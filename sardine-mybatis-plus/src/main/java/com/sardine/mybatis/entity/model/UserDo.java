package com.sardine.mybatis.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author keith
 * @since 2023-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_user")
public class UserDo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private String username = "zhangsan";

    private String password = "123456";

    private String phone = "12845451145";

    private String realName = "张三";

    private String avatar;

    private Integer gender = 1;

    private Boolean status = false;

    private Boolean isDeleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
