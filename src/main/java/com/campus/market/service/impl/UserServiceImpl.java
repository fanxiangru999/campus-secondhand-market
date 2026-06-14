package com.campus.market.service.impl;

import com.campus.market.dto.LoginRequest;
import com.campus.market.dto.LoginResponse;
import com.campus.market.dto.RegisterRequest;
import com.campus.market.entity.User;
import com.campus.market.mapper.UserMapper;
import com.campus.market.service.UserService;
import com.campus.market.util.JwtUtil;
import com.campus.market.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        User existUser = userMapper.selectByStudentId(request.getStudentId());
        if (existUser != null) {
            throw new RuntimeException("学号已存在");
        }

        User user = new User();
        user.setStudentId(request.getStudentId());
        user.setUsername(request.getUsername());
        user.setUserType("student");
        user.setPassword(PasswordUtil.encrypt(request.getPassword()));
        user.setPhone(request.getPhone());

        userMapper.insert(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userMapper.selectByStudentId(request.getStudentId());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        if (!PasswordUtil.verify(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        String token = jwtUtil.generateToken(user.getStudentId(), user.getUserType());

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setStudentId(user.getStudentId());
        response.setUsername(user.getUsername());
        response.setUserType(user.getUserType());
        response.setPhone(user.getPhone());

        return response;
    }

    @Override
    public User getUserByStudentId(String studentId) {
        User user = userMapper.selectByStudentId(studentId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        user.setPassword(null);
        return user;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        User existUser = userMapper.selectByStudentId(user.getStudentId());
        if (existUser == null) {
            throw new RuntimeException("用户不存在");
        }
        userMapper.update(user);
    }

    @Override
    @Transactional
    public void updatePassword(String studentId, String oldPassword, String newPassword) {
        User user = userMapper.selectByStudentId(studentId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        if (!PasswordUtil.verify(oldPassword, user.getPassword())) {
            throw new RuntimeException("原密码错误");
        }

        String encryptedPassword = PasswordUtil.encrypt(newPassword);
        userMapper.resetPassword(studentId, encryptedPassword);
    }

    @Override
    public List<User> getAllStudents() {
        List<User> students = userMapper.selectAllStudents();
        students.forEach(user -> user.setPassword(null));
        return students;
    }

    @Override
    @Transactional
    public void resetPassword(String studentId, String newPassword) {
        User user = userMapper.selectByStudentId(studentId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        String encryptedPassword = PasswordUtil.encrypt(newPassword);
        userMapper.resetPassword(studentId, encryptedPassword);
    }
}
