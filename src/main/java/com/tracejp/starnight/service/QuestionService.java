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
     * 随机抽取题目 vo
     */
    List<QuestionVo> randomExtractQuestionVos(Integer type, Integer number, Long subjectId, Integer difficult);

    /**
     * 保存VO
     */
    void saveQuestionVo(QuestionVo question, Long userId);

    /**
     * 更新VO
     */
    void updateQuestionVo(QuestionVo question);

    /**
     * 通过题目 id 使用 gpt 进行解答
     */
    String gptQuestionAnalyze(Long id);

}

