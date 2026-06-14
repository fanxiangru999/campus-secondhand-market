package com.campus.market.controller;

import com.campus.market.dto.PageRequest;
import com.campus.market.dto.PageResponse;
import com.campus.market.dto.Result;
import com.campus.market.entity.Trade;
import com.campus.market.service.TradeService;
import com.campus.market.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 交易控制器
 */
@RestController
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 创建交易（简单版：直接完成）
     */
    @PostMapping("/create")
    public Result<Void> createTrade(@RequestBody Map<String, String> params, HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String sellerStudentId = jwtUtil.getStudentIdFromToken(token);
            String buyerStudentId = params.get("buyerStudentId");
            String productId = params.get("productId");
            String tradePrice = params.get("tradePrice");
            tradeService.createTrade(sellerStudentId, buyerStudentId, productId, tradePrice);
            return Result.success("交易完成", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 发起交易申请（拓展版）
     */
    @PostMapping("/request")
    public Result<Void> requestTrade(@RequestBody Map<String, String> params, HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String buyerStudentId = jwtUtil.getStudentIdFromToken(token);
            String productId = params.get("productId");
            String tradePrice = params.get("tradePrice");
            tradeService.initiateTradeRequest(buyerStudentId, productId, tradePrice);
            return Result.success("交易申请已发送", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 确认交易（拓展版）
     */
    @PutMapping("/confirm/{tradeId}")
    public Result<Void> confirmTrade(@PathVariable String tradeId, HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String sellerStudentId = jwtUtil.getStudentIdFromToken(token);
            tradeService.confirmTrade(tradeId, sellerStudentId);
            return Result.success("交易已确认", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 拒绝交易（拓展版）
     */
    @PutMapping("/reject/{tradeId}")
    public Result<Void> rejectTrade(@PathVariable String tradeId, HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String sellerStudentId = jwtUtil.getStudentIdFromToken(token);
            tradeService.rejectTrade(tradeId, sellerStudentId);
            return Result.success("交易已拒绝", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 完成交易
     */
    @PutMapping("/complete/{tradeId}")
    public Result<Void> completeTrade(@PathVariable String tradeId, HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String sellerStudentId = jwtUtil.getStudentIdFromToken(token);
            tradeService.completeTrade(tradeId, sellerStudentId);
            return Result.success("交易已完成", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询我的交易记录
     */
    @GetMapping("/my-trades")
    public Result<List<Trade>> getMyTrades(HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String studentId = jwtUtil.getStudentIdFromToken(token);
            List<Trade> trades = tradeService.getTradesByStudentId(studentId);
            return Result.success(trades);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询所有交易记录（管理员功能）
     */
    @GetMapping("/all")
    public Result<List<Trade>> getAllTrades(HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String userType = jwtUtil.getUserTypeFromToken(token);
            if (!"admin".equals(userType)) {
                return Result.error(403, "无权限访问");
            }
            List<Trade> trades = tradeService.getAllTrades();
            return Result.success(trades);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 条件查询交易记录（管理员功能）
     */
    @GetMapping("/search")
    public Result<PageResponse<Trade>> searchTrades(
            @RequestParam(required = false) String studentId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String tradeStatus,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {
        try {
            String token = getTokenFromRequest(request);
            String userType = jwtUtil.getUserTypeFromToken(token);
            if (!"admin".equals(userType)) {
                return Result.error(403, "无权限访问");
            }

            PageRequest pageRequest = new PageRequest();
            pageRequest.setPageNum(pageNum);
            pageRequest.setPageSize(pageSize);

            PageResponse<Trade> response = tradeService.searchTrades(studentId, startDate, endDate, tradeStatus, pageRequest);
            return Result.success(response);
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
