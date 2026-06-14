package com.campus.market.service.impl;

import com.campus.market.dto.PageRequest;
import com.campus.market.dto.PageResponse;
import com.campus.market.entity.Product;
import com.campus.market.mapper.ProductMapper;
import com.campus.market.service.ProductService;
import com.campus.market.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 物品服务实现类
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    @Transactional
    public void publishProduct(Product product) {
        product.setProductId(IdGenerator.generateProductId());
        product.setStatus("待交易");
        productMapper.insert(product);
    }

    @Override
    @Transactional
    public void updateProduct(Product product) {
        Product existProduct = productMapper.selectByProductId(product.getProductId());
        if (existProduct == null) {
            throw new RuntimeException("物品不存在");
        }
        if ("已售出".equals(existProduct.getStatus())) {
            throw new RuntimeException("已售出的物品不能修改");
        }
        productMapper.update(product);
    }

    @Override
    @Transactional
    public void offlineProduct(String productId, String studentId) {
        Product product = productMapper.selectByProductId(productId);
        if (product == null) {
            throw new RuntimeException("物品不存在");
        }
        if (!product.getSellerStudentId().equals(studentId)) {
            throw new RuntimeException("无权操作该物品");
        }
        if ("已售出".equals(product.getStatus())) {
            throw new RuntimeException("已售出的物品不能下架");
        }
        productMapper.updateStatus(productId, "已下架");
    }

    @Override
    public Product getProductById(String productId) {
        Product product = productMapper.selectByProductId(productId);
        if (product == null) {
            throw new RuntimeException("物品不存在");
        }
        return product;
    }

    @Override
    public List<Product> getProductsBySeller(String sellerStudentId) {
        return productMapper.selectBySellerStudentId(sellerStudentId);
    }

    @Override
    public PageResponse<Product> searchProducts(String category, String productName, String status, PageRequest pageRequest) {
        Map<String, Object> params = new HashMap<>();
        params.put("category", category);
        params.put("productName", productName);
        params.put("status", status);

        Long total = productMapper.countByCondition(params);

        int offset = (pageRequest.getPageNum() - 1) * pageRequest.getPageSize();
        params.put("offset", offset);
        params.put("pageSize", pageRequest.getPageSize());

        List<Product> products = productMapper.selectByCondition(params);

        return new PageResponse<>(total, products, pageRequest.getPageNum(), pageRequest.getPageSize());
    }

    @Override
    public List<Product> getAllProducts() {
        return productMapper.selectAll();
    }

    @Override
    @Transactional
    public void markAsSold(String productId) {
        productMapper.updateStatus(productId, "已售出");
    }

    @Override
    public Long countByDateRange(String startDate, String endDate) {
        return productMapper.countByDateRange(startDate, endDate);
    }

    @Override
    @Transactional
    public void forceOffline(String productId) {
        Product product = productMapper.selectByProductId(productId);
        if (product == null) {
            throw new RuntimeException("物品不存在");
        }
        if ("已售出".equals(product.getStatus())) {
            throw new RuntimeException("已售出的物品不能下架");
        }
        if ("已下架".equals(product.getStatus())) {
            throw new RuntimeException("物品已经下架");
        }
        productMapper.updateStatus(productId, "已下架");
    }
}
