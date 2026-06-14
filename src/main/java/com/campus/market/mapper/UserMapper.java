package com.campus.market.mapper;

import com.campus.market.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper {

    /**
     * 根据学号查询用户
     */
    User selectByStudentId(@Param("studentId") String studentId);

    /**
     * 插入用户
     */
    int insert(User user);

    /**
     * 更新用户信息
     */
    int update(User user);

    /**
     * 根据学号删除用户
     */
    int deleteByStudentId(@Param("studentId") String studentId);

    /**
     * 查询所有学生用户
     */
    List<User> selectAllStudents();

    /**
     * 重置用户密码
     */
    int resetPassword(@Param("studentId") String studentId, @Param("password") String password);

    /**
     * 统计用户总数
     */
    Long countAll();
}
