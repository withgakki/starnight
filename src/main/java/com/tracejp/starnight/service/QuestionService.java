package com.tracejp.starnight.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tracejp.starnight.entity.QuestionEntity;
import com.tracejp.starnight.entity.vo.QuestionVo;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
public interface QuestionService extends IService<QuestionEntity> {

    /**
     * 分页
     */
    List<QuestionEntity> listPage(QuestionEntity question);

    /**
     * 通过 id 获取 vo
     */
    QuestionVo getQuestionVo(Long id);

    /**
     * 通过 entity 获取 vo
     */
    QuestionVo getQuestionVo(QuestionEntity question);

    /**
     * 通过 ids 获取 vos
     */
    List<QuestionVo> getQuestionVo(List<Long> ids);

    /**
     * 保存VO
     */
    void saveQuestionVo(QuestionVo question, Long userId);

    /**
     * 更新VO
     */
    void updateQuestionVo(QuestionVo question);

}

