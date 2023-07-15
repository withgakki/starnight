package com.tracejp.starnight.entity.vo;

import com.tracejp.starnight.entity.dto.UserDto;
import lombok.Data;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/7/14 15:01
 */
@Data
public class ExamPaperAndAnswerVo {

    private ExamPaperVo paper;

    private ExamPaperAnswerSubmitVo answer;

    private UserDto user;

}
