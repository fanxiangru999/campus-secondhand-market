package com.campus.market.dto;

import lombok.Data;
import java.util.List;

/**
 * 分页响应DTO
 */
@Data
public class PageResponse<T> {
    /**
     * 总记录数
     */
    private Long total;

    /**
     * 数据列表
     */
    private List<T> records;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer totalPages;

    public PageResponse(Long total, List<T> records, Integer pageNum, Integer pageSize) {
        this.total = total;
        this.records = records;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPages = (int) Math.ceil((double) total / pageSize);
    }
}
