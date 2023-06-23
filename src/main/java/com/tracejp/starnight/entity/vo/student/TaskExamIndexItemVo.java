package com.tracejp.starnight.entity.vo.student;

import lombok.Data;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/6/11 16:06
 */
@Data
public class TaskExamIndexItemVo {

    private Long examPaperId;

    private String examPaperName;

    private Long examPaperAnswerId;

    /**
     * 答卷状态
     */
    private Integer status;

}
