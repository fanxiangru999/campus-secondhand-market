package com.campus.market.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 学号（唯一）
     */
    private String studentId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户类型：student-学生，admin-管理员
     */
    private String userType;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 联系电话
     */
    private String phone;

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
}
