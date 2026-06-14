package com.campus.market.service;

import com.campus.market.dto.LoginRequest;
import com.campus.market.dto.LoginResponse;
import com.campus.market.dto.RegisterRequest;
import com.campus.market.entity.User;
import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 用户注册
     */
    void register(RegisterRequest request);

    /**
     * 用户登录
     */
    LoginResponse login(LoginRequest request);

    /**
     * 根据学号查询用户
     */
    User getUserByStudentId(String studentId);

    /**
     * 更新用户信息
     */
    void updateUser(User user);

    /**
     * 修改密码
     */
    void updatePassword(String studentId, String oldPassword, String newPassword);

    /**
     * 查询所有学生用户
     */
    List<User> getAllStudents();

    /**
     * 重置用户密码（管理员功能）
     */
    void resetPassword(String studentId, String newPassword);
}
