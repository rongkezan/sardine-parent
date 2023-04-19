package com.demo.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author yesitao
 * @since 2021-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_wallet")
public class WalletDo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private Integer age;

    private BigDecimal balance;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;
}
