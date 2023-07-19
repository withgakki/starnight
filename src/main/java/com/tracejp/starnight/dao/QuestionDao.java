package com.tracejp.starnight.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tracejp.starnight.entity.QuestionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 22:16:50
 */
@Mapper
public interface QuestionDao extends BaseMapper<QuestionEntity> {

    List<QuestionEntity> listPage(QuestionEntity question);

    List<QuestionEntity> randomExtractQuestion(@Param("type") Integer type,
                                               @Param("number") Integer number,
                                               @Param("subjectId") Long subjectId,
                                               @Param("difficult") Integer difficult);

}
