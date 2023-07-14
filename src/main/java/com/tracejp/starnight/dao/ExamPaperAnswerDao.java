package com.tracejp.starnight.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tracejp.starnight.entity.ExamPaperAnswerEntity;
import com.tracejp.starnight.entity.vo.ExamPaperAnswerVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 22:16:50
 */
@Mapper
public interface ExamPaperAnswerDao extends BaseMapper<ExamPaperAnswerEntity> {

    List<ExamPaperAnswerEntity> listPage(ExamPaperAnswerEntity examPaperAnswer);

    List<ExamPaperAnswerVo> listPageVo(ExamPaperAnswerEntity examPaperAnswer);

}
