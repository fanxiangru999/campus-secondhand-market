package com.campus.market.service.impl;

import com.campus.market.dto.PageRequest;
import com.campus.market.dto.PageResponse;
import com.campus.market.entity.Product;
import com.campus.market.entity.Trade;
import com.campus.market.mapper.ProductMapper;
import com.campus.market.mapper.TradeMapper;
import com.campus.market.service.TradeService;
import com.campus.market.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 交易服务实现类
 */
@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeMapper tradeMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    @Transactional
    public void createTrade(String sellerStudentId, String buyerStudentId, String productId, String tradePrice) {
        Product product = productMapper.selectByProductId(productId);
        if (product == null) {
            throw new RuntimeException("物品不存在");
        }
        if (!"待交易".equals(product.getStatus())) {
            throw new RuntimeException("物品状态不可交易");
        }
        if (!product.getSellerStudentId().equals(sellerStudentId)) {
            throw new RuntimeException("卖家信息不匹配");
        }

        Trade trade = new Trade();
        trade.setTradeId(IdGenerator.generateTradeId());
        trade.setSellerStudentId(sellerStudentId);
        trade.setBuyerStudentId(buyerStudentId);
        trade.setProductId(productId);
        trade.setTradePrice(new BigDecimal(tradePrice));
        trade.setTradeStatus("已完成");

        tradeMapper.insert(trade);
        productMapper.updateStatus(productId, "已售出");
    }

    @Override
    @Transactional
    public void initiateTradeRequest(String buyerStudentId, String productId, String tradePrice) {
        Product product = productMapper.selectByProductId(productId);
        if (product == null) {
            throw new RuntimeException("物品不存在");
        }
        if (!"待交易".equals(product.getStatus())) {
            throw new RuntimeException("物品状态不可交易");
        }

        Trade trade = new Trade();
        trade.setTradeId(IdGenerator.generateTradeId());
        trade.setSellerStudentId(product.getSellerStudentId());
        trade.setBuyerStudentId(buyerStudentId);
        trade.setProductId(productId);
        trade.setTradePrice(new BigDecimal(tradePrice));
        trade.setTradeStatus("待确认");

        tradeMapper.insert(trade);
    }

    @Override
    @Transactional
    public void confirmTrade(String tradeId, String sellerStudentId) {
        Trade trade = tradeMapper.selectByTradeId(tradeId);
        if (trade == null) {
            throw new RuntimeException("交易不存在");
        }
        if (!trade.getSellerStudentId().equals(sellerStudentId)) {
            throw new RuntimeException("无权操作该交易");
        }
        if (!"待确认".equals(trade.getTradeStatus())) {
            throw new RuntimeException("交易状态不正确");
        }

        tradeMapper.updateStatus(tradeId, "待完成");
    }

    @Override
    @Transactional
    public void rejectTrade(String tradeId, String sellerStudentId) {
        Trade trade = tradeMapper.selectByTradeId(tradeId);
        if (trade == null) {
            throw new RuntimeException("交易不存在");
        }
        if (!trade.getSellerStudentId().equals(sellerStudentId)) {
            throw new RuntimeException("无权操作该交易");
        }
        if (!"待确认".equals(trade.getTradeStatus())) {
            throw new RuntimeException("交易状态不正确");
        }

        tradeMapper.updateStatus(tradeId, "已拒绝");
    }

    @Override
    @Transactional
    public void completeTrade(String tradeId, String sellerStudentId) {
        Trade trade = tradeMapper.selectByTradeId(tradeId);
        if (trade == null) {
            throw new RuntimeException("交易不存在");
        }
        if (!trade.getSellerStudentId().equals(sellerStudentId)) {
            throw new RuntimeException("无权操作该交易");
        }
        if ("已完成".equals(trade.getTradeStatus())) {
            throw new RuntimeException("交易已完成");
        }

        tradeMapper.updateStatus(tradeId, "已完成");
        productMapper.updateStatus(trade.getProductId(), "已售出");
    }

    @Override
    public List<Trade> getTradesByStudentId(String studentId) {
        return tradeMapper.selectByStudentId(studentId);
    }

    @Override
    public List<Trade> getAllTrades() {
        return tradeMapper.selectAll();
    }

    @Override
    public PageResponse<Trade> searchTrades(String studentId, String startDate, String endDate, String tradeStatus, PageRequest pageRequest) {
        Map<String, Object> params = new HashMap<>();
        params.put("studentId", studentId);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("tradeStatus", tradeStatus);

        Long total = tradeMapper.countByCondition(params);

        int offset = (pageRequest.getPageNum() - 1) * pageRequest.getPageSize();
        params.put("offset", offset);
        params.put("pageSize", pageRequest.getPageSize());

        List<Trade> trades = tradeMapper.selectByCondition(params);

        return new PageResponse<>(total, trades, pageRequest.getPageNum(), pageRequest.getPageSize());
    }

    @Override
    public Long countByDateRange(String startDate, String endDate) {
        return tradeMapper.countByDateRange(startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> countByCategory(String startDate, String endDate) {
        return tradeMapper.countByCategory(startDate, endDate);
    }
}
