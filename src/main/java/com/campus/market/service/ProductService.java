package com.campus.market.service;

import com.campus.market.dto.PageRequest;
import com.campus.market.dto.PageResponse;
import com.campus.market.entity.Product;
import java.util.List;

/**
 * 物品服务接口
 */
public interface ProductService {

    /**
     * 发布物品
     */
    void publishProduct(Product product);

    /**
     * 更新物品信息
     */
    void updateProduct(Product product);

    /**
     * 下架物品
     */
    void offlineProduct(String productId, String studentId);

    /**
     * 根据物品编号查询
     */
    Product getProductById(String productId);

    /**
     * 根据卖家学号查询物品列表
     */
    List<Product> getProductsBySeller(String sellerStudentId);

    /**
     * 条件查询物品（分页）
     */
    PageResponse<Product> searchProducts(String category, String productName, String status, PageRequest pageRequest);

    /**
     * 查询所有物品（管理员）
     */
    List<Product> getAllProducts();

    /**
     * 标记物品为已售出
     */
    void markAsSold(String productId);

    /**
     * 统计指定时间段内的物品发布数量
     */
    Long countByDateRange(String startDate, String endDate);

    /**
     * 管理员强制下架物品
     */
    void forceOffline(String productId);
}
