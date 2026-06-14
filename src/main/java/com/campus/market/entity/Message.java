package com.campus.market.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 留言实体类
 */
@Data
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 留言编号
     */
    private String messageId;

    /**
     * 物品编号
     */
    private String productId;

    /**
     * 发送者学号
     */
    private String senderStudentId;

    /**
     * 接收者学号（卖家）
     */
    private String receiverStudentId;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 父留言ID（用于回复）
     */
    private String parentMessageId;

    /**
     * 是否已读：0-未读，1-已读
     */
    private Integer isRead;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;

    /**
     * 发送者用户名（关联查询）
     */
    private String senderUsername;

    /**
     * 接收者用户名（关联查询）
     */
    private String receiverUsername;

    /**
     * 物品名称（关联查询）
     */
    private String productName;
}
