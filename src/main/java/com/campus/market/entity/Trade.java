package com.campus.market.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 交易记录实体类
 */
@Data
public class Trade implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 交易编号
     */
    private String tradeId;

    /**
     * 卖家学号
     */
    private String sellerStudentId;

    /**
     * 买家学号
     */
    private String buyerStudentId;

    /**
     * 物品编号
     */
    private String productId;

    /**
     * 成交价格
     */
    private BigDecimal tradePrice;

    /**
     * 交易日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date tradeDate;

    /**
     * 交易状态：待确认、待完成、已完成、已拒绝
     */
    private String tradeStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedAt;

    /**
     * 卖家用户名（关联查询）
     */
    private String sellerUsername;

    /**
     * 买家用户名（关联查询）
     */
    private String buyerUsername;

    /**
     * 物品名称（关联查询）
     */
    private String productName;

    /**
     * 物品分类（关联查询）
     */
    private String category;
}
