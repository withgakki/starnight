package com.tracejp.starnight.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tracejp.starnight.entity.ExamPaperQuestionAnswerEntity;
import com.tracejp.starnight.entity.vo.student.QuestionAnswerErrorVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 22:16:50
 */
@Mapper
public interface ExamPaperQuestionAnswerDao extends BaseMapper<ExamPaperQuestionAnswerEntity> {

    /**
     * 分页 QuestionAnswerErrorVo
     */
    List<QuestionAnswerErrorVo> listQuestionAnswerErrorVo(@Param("userId") Long userId);

}
