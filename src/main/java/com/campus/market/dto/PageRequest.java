package com.campus.market.dto;

import lombok.Data;

/**
 * 分页查询请求DTO
 */
@Data
public class PageRequest {
    /**
     * 当前页码
     */
    private Integer pageNum = 1;

    /**
     * 每页数量
     */
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 排序方向：asc-升序，desc-降序
     */
    private String orderDirection = "desc";
}
