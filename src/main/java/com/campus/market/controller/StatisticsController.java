package com.campus.market.controller;

import com.campus.market.dto.Result;
import com.campus.market.service.ProductService;
import com.campus.market.service.TradeService;
import com.campus.market.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计分析控制器
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private ProductService productService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 统计指定时间段内的数据（管理员功能）
     */
    @GetMapping("/summary")
    public Result<Map<String, Object>> getStatisticsSummary(
            @RequestParam String startDate,
            @RequestParam String endDate,
            HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String userType = jwtUtil.getUserTypeFromToken(token);
            if (!"admin".equals(userType)) {
                return Result.error(403, "无权限访问");
            }

            Map<String, Object> summary = new HashMap<>();

            Long productCount = productService.countByDateRange(startDate, endDate);
            summary.put("productCount", productCount);

            Long tradeCount = tradeService.countByDateRange(startDate, endDate);
            summary.put("tradeCount", tradeCount);

            List<Map<String, Object>> categoryStats = tradeService.countByCategory(startDate, endDate);
            summary.put("categoryStats", categoryStats);

            return Result.success(summary);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 统计热门交易分类（管理员功能）
     */
    @GetMapping("/hot-categories")
    public Result<List<Map<String, Object>>> getHotCategories(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String userType = jwtUtil.getUserTypeFromToken(token);
            if (!"admin".equals(userType)) {
                return Result.error(403, "无权限访问");
            }

            List<Map<String, Object>> categoryStats = tradeService.countByCategory(startDate, endDate);
            return Result.success(categoryStats);
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
