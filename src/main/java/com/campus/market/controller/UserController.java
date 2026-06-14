package com.campus.market.controller;

import com.campus.market.dto.*;
import com.campus.market.entity.User;
import com.campus.market.service.UserService;
import com.campus.market.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<Void> register(@Validated @RequestBody RegisterRequest request) {
        try {
            userService.register(request);
            return Result.success("注册成功", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@Validated @RequestBody LoginRequest request) {
        try {
            LoginResponse response = userService.login(request);
            return Result.success("登录成功", response);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public Result<User> getCurrentUser(HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String studentId = jwtUtil.getStudentIdFromToken(token);
            User user = userService.getUserByStudentId(studentId);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 修改个人信息
     */
    @PutMapping("/update")
    public Result<Void> updateUser(@RequestBody User user, HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String studentId = jwtUtil.getStudentIdFromToken(token);
            user.setStudentId(studentId);
            userService.updateUser(user);
            return Result.success("修改成功", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    public Result<Void> updatePassword(@RequestBody Map<String, String> params, HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String studentId = jwtUtil.getStudentIdFromToken(token);
            String oldPassword = params.get("oldPassword");
            String newPassword = params.get("newPassword");
            userService.updatePassword(studentId, oldPassword, newPassword);
            return Result.success("密码修改成功", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询所有学生用户（管理员功能）
     */
    @GetMapping("/students")
    public Result<List<User>> getAllStudents(HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String userType = jwtUtil.getUserTypeFromToken(token);
            if (!"admin".equals(userType)) {
                return Result.error(403, "无权限访问");
            }
            List<User> students = userService.getAllStudents();
            return Result.success(students);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 重置用户密码（管理员功能）
     */
    @PutMapping("/reset-password")
    public Result<Void> resetPassword(@RequestBody Map<String, String> params, HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String userType = jwtUtil.getUserTypeFromToken(token);
            if (!"admin".equals(userType)) {
                return Result.error(403, "无权限访问");
            }
            String studentId = params.get("studentId");
            String newPassword = params.get("newPassword");
            userService.resetPassword(studentId, newPassword);
            return Result.success("密码重置成功", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 从请求中获取Token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
