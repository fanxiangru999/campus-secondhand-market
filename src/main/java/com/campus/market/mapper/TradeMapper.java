package com.campus.market.mapper;

import com.campus.market.entity.Trade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 交易Mapper接口
 */
@Mapper
public interface TradeMapper {

    /**
     * 根据交易编号查询
     */
    Trade selectByTradeId(@Param("tradeId") String tradeId);

    /**
     * 插入交易记录
     */
    int insert(Trade trade);

    /**
     * 更新交易状态
     */
    int updateStatus(@Param("tradeId") String tradeId, @Param("status") String status);

    /**
     * 根据学号查询交易记录（作为买家或卖家）
     */
    List<Trade> selectByStudentId(@Param("studentId") String studentId);

    /**
     * 查询所有交易记录
     */
    List<Trade> selectAll();

    /**
     * 条件查询交易记录
     */
    List<Trade> selectByCondition(Map<String, Object> params);

    /**
     * 统计条件查询结果数量
     */
    Long countByCondition(Map<String, Object> params);

    /**
     * 统计指定时间段内的交易完成数量
     */
    Long countByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 统计热门交易分类
     */
    List<Map<String, Object>> countByCategory(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
