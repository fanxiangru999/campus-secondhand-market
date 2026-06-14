package com.campus.market.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 登录请求DTO
 */
@Data
public class LoginRequest {
    /**
     * 学号
     */
    @NotBlank(message = "学号不能为空")
    private String studentId;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
