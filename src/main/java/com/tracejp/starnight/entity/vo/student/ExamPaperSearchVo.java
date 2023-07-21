package com.tracejp.starnight.entity.vo.student;

import com.tracejp.starnight.entity.dto.SearchExamPaperDto;
import lombok.Data;

import java.util.List;

/**
 * <p> 试卷 搜索 分页 vo <p/>
 *
 * @author traceJP
 * @since 2023/7/21 18:08
 */
@Data
public class ExamPaperSearchVo {

    private List<SearchExamPaperDto> rows;

    private Long total;

}
