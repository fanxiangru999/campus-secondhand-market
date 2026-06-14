package com.campus.market.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 物品实体类
 */
@Data
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 物品编号
     */
    private String productId;

    /**
     * 发布者学号
     */
    private String sellerStudentId;

    /**
     * 物品分类：书籍教材、电子数码、生活用品、体育器材、其他
     */
    private String category;

    /**
     * 物品名称
     */
    private String productName;

    /**
     * 物品成色：全新、九成新、八成新、其他
     */
    private String condition;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 物品描述
     */
    private String description;

    /**
     * 物品图片URL
     */
    private String imageUrl;

    /**
     * 发布日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishDate;

    /**
     * 物品状态：待交易、已下架、已售出
     */
    private String status;

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
     * 发布者用户名（关联查询）
     */
    private String sellerUsername;

    /**
     * 发布者电话（关联查询）
     */
    private String sellerPhone;
}
