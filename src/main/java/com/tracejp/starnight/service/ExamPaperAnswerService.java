package com.tracejp.starnight.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tracejp.starnight.entity.ExamPaperAnswerEntity;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.bo.ExamPaperAnswerBo;
import com.tracejp.starnight.entity.vo.ExamPaperAnswerSubmitVo;
import com.tracejp.starnight.entity.vo.ExamPaperAnswerVo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
public interface ExamPaperAnswerService extends IService<ExamPaperAnswerEntity> {

    /**
     * 列表
     */
    List<ExamPaperAnswerEntity> listPage(ExamPaperAnswerEntity examPaperAnswer);

    /**
     * 列表 vo
     */
    List<ExamPaperAnswerVo> listPageVo(ExamPaperAnswerEntity examPaperAnswer);

    /**
     * 构造 ExamPaperAnswerBo 业务模型
     */
    ExamPaperAnswerBo buildExamPaperBo(ExamPaperAnswerSubmitVo answerVo, UserEntity user);

    /**
     * 是否存在答案 通过 试卷id 用户id 查找
     */
    boolean hasAnswerByPaperIdUserId(Long paperId, Long userId);

    /**
     * 保存答案
     */
    CompletableFuture<Void> saveAnswerBoAsync(ExamPaperAnswerBo examPaperAnswerBo);

    /**
     * 通过答卷id获取答卷信息
     */
    ExamPaperAnswerSubmitVo getAnswerSubmitVoById(Long id);

    /**
     * 批改试卷
     * @return 分数
     */
    Integer judge(ExamPaperAnswerSubmitVo submitVo);

    /**
     * 级联删除答卷
     */
    void removeAllByIds(List<Long> idList);

}

