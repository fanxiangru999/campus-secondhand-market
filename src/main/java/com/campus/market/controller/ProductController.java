package com.campus.market.controller;

import com.campus.market.dto.PageRequest;
import com.campus.market.dto.PageResponse;
import com.campus.market.dto.Result;
import com.campus.market.entity.Product;
import com.campus.market.service.ProductService;
import com.campus.market.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 物品控制器
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 发布物品
     */
    @PostMapping("/publish")
    public Result<Void> publishProduct(@RequestBody Product product, HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String studentId = jwtUtil.getStudentIdFromToken(token);
            product.setSellerStudentId(studentId);
            productService.publishProduct(product);
            return Result.success("发布成功", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 修改物品信息
     */
    @PutMapping("/update")
    public Result<Void> updateProduct(@RequestBody Product product, HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String studentId = jwtUtil.getStudentIdFromToken(token);
            Product existProduct = productService.getProductById(product.getProductId());
            if (!existProduct.getSellerStudentId().equals(studentId)) {
                return Result.error(403, "无权操作该物品");
            }
            productService.updateProduct(product);
            return Result.success("修改成功", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 下架物品
     */
    @PutMapping("/offline/{productId}")
    public Result<Void> offlineProduct(@PathVariable String productId, HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String studentId = jwtUtil.getStudentIdFromToken(token);
            productService.offlineProduct(productId, studentId);
            return Result.success("下架成功", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询物品详情
     */
    @GetMapping("/{productId}")
    public Result<Product> getProduct(@PathVariable String productId) {
        try {
            Product product = productService.getProductById(productId);
            return Result.success(product);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询我的发布
     */
    @GetMapping("/my-products")
    public Result<List<Product>> getMyProducts(HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String studentId = jwtUtil.getStudentIdFromToken(token);
            List<Product> products = productService.getProductsBySeller(studentId);
            return Result.success(products);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 搜索物品（分页）
     */
    @GetMapping("/search")
    public Result<PageResponse<Product>> searchProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            PageRequest pageRequest = new PageRequest();
            pageRequest.setPageNum(pageNum);
            pageRequest.setPageSize(pageSize);

            if (status == null || status.isEmpty()) {
                status = "待交易";
            }

            PageResponse<Product> response = productService.searchProducts(category, productName, status, pageRequest);
            return Result.success(response);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询所有物品（管理员功能）
     */
    @GetMapping("/all")
    public Result<List<Product>> getAllProducts(HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String userType = jwtUtil.getUserTypeFromToken(token);
            if (!"admin".equals(userType)) {
                return Result.error(403, "无权限访问");
            }
            List<Product> products = productService.getAllProducts();
            return Result.success(products);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 强制下架物品（管理员功能）
     */
    @PutMapping("/force-offline/{productId}")
    public Result<Void> forceOfflineProduct(@PathVariable String productId, HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String userType = jwtUtil.getUserTypeFromToken(token);
            if (!"admin".equals(userType)) {
                return Result.error(403, "无权限访问");
            }
            productService.forceOffline(productId);
            return Result.success("下架成功", null);
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
