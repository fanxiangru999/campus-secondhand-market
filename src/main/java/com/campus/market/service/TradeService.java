package com.campus.market.service;

import com.campus.market.dto.PageRequest;
import com.campus.market.dto.PageResponse;
import com.campus.market.entity.Trade;
import java.util.List;
import java.util.Map;

/**
 * 交易服务接口
 */
public interface TradeService {

    /**
     * 创建交易（简单版：直接完成）
     */
    void createTrade(String sellerStudentId, String buyerStudentId, String productId, String tradePrice);

    /**
     * 发起交易申请（拓展版）
     */
    void initiateTradeRequest(String buyerStudentId, String productId, String tradePrice);

    /**
     * 确认交易（拓展版）
     */
    void confirmTrade(String tradeId, String sellerStudentId);

    /**
     * 拒绝交易（拓展版）
     */
    void rejectTrade(String tradeId, String sellerStudentId);

    /**
     * 完成交易
     */
    void completeTrade(String tradeId, String sellerStudentId);

    /**
     * 根据学号查询交易记录
     */
    List<Trade> getTradesByStudentId(String studentId);

    /**
     * 查询所有交易记录
     */
    List<Trade> getAllTrades();

    /**
     * 条件查询交易记录
     */
    PageResponse<Trade> searchTrades(String studentId, String startDate, String endDate, String tradeStatus, PageRequest pageRequest);

    /**
     * 统计指定时间段内的交易完成数量
     */
    Long countByDateRange(String startDate, String endDate);

    /**
     * 统计热门交易分类
     */
    List<Map<String, Object>> countByCategory(String startDate, String endDate);
}
