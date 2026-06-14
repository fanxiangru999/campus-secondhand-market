package com.campus.market.dto;

import lombok.Data;

/**
 * 登录响应DTO
 */
@Data
public class LoginResponse {
    /**
     * JWT token
     */
    private String token;

    /**
     * 学号
     */
    private String studentId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 联系电话
     */
    private String phone;
}
