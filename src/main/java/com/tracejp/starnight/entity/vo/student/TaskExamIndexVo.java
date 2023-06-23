package com.tracejp.starnight.entity.vo.student;

import lombok.Data;

import java.util.List;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/6/11 16:02
 */
@Data
public class TaskExamIndexVo {

    private Long id;

    private String title;

    private List<TaskExamIndexItemVo> paperItems;

}
