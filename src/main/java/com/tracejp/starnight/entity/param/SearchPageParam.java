package com.tracejp.starnight.entity.param;

import lombok.Data;

/**
 * <p> 搜索分页参数 <p/>
 *
 * @author traceJP
 * @since 2023/7/21 11:44
 */
@Data
public class SearchPageParam {

    /**
     * 当前记录起始索引
     */
    private Integer pageNum = 1;

    /**
     * 每页显示记录数
     */
    private Integer pageSize = 10;

    /**
     * 查询关键字
     */
    private String keyword;

}
