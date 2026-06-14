package com.campus.market.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 注册请求DTO
 */
@Data
public class RegisterRequest {
    /**
     * 学号
     */
    @NotBlank(message = "学号不能为空")
    private String studentId;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 联系电话
     */
    private String phone;
}
