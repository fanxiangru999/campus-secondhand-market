package com.campus.market.mapper;

import com.campus.market.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 物品Mapper接口
 */
@Mapper
public interface ProductMapper {

    /**
     * 根据物品编号查询
     */
    Product selectByProductId(@Param("productId") String productId);

    /**
     * 插入物品
     */
    int insert(Product product);

    /**
     * 更新物品
     */
    int update(Product product);

    /**
     * 删除物品
     */
    int deleteByProductId(@Param("productId") String productId);

    /**
     * 根据卖家学号查询物品列表
     */
    List<Product> selectBySellerStudentId(@Param("studentId") String studentId);

    /**
     * 条件查询物品列表（分页）
     */
    List<Product> selectByCondition(Map<String, Object> params);

    /**
     * 统计条件查询结果数量
     */
    Long countByCondition(Map<String, Object> params);

    /**
     * 查询所有物品
     */
    List<Product> selectAll();

    /**
     * 更新物品状态
     */
    int updateStatus(@Param("productId") String productId, @Param("status") String status);

    /**
     * 统计指定时间段内的物品发布数量
     */
    Long countByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
