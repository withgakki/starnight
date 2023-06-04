package com.tracejp.starnight.entity.vo;

import com.tracejp.starnight.entity.enums.QuestionTypeEnum;
import com.tracejp.starnight.exception.ServiceException;
import com.tracejp.starnight.utils.ScoreUtils;
import com.tracejp.starnight.utils.StringUtils;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/6/1 21:21
 */
@Data
public class QuestionVo {

    /**
     * 题目id
     */
    private Long id;

    /**
     * 题目类型
     */
    private Integer questionType;

    /**
     * 学科
     */
    private Long subjectId;

    /**
     * 题干
     */
    private String title;

    /**
     * 年级
     */
    private Integer gradeLevel;

    /**
     * 题目选项
     */
    private List<QuestionItemVo> items;

    /**
     * 题目解析
     */
    private String analyze;

    /**
     * 正确答案数组 【多选题】
     * 注意：填空题无此字段 填空题答案在items.content 中
     */
    private List<String> correctArray;

    /**
     * 正确答案 【单选题、判断题、简答题】
     */
    private String correct;

    /**
     * 分数
     */
    private String score;

    /**
     * 难度
     */
    private Integer difficult;

    /**
     * 选项排序
     */
    private Integer itemOrder;

    public void validQuestionVo() {
        int qType = this.getQuestionType();
        boolean requireCorrect = qType == QuestionTypeEnum.SingleChoice.getCode() ||
                qType == QuestionTypeEnum.TrueFalse.getCode() ||
                qType == QuestionTypeEnum.ShortAnswer.getCode();
        if (requireCorrect) {
            if (StringUtils.isEmpty(this.getCorrect())) {
                throw new ServiceException("请填写正确答案");
            }
        }
        if (qType == QuestionTypeEnum.MultipleChoice.getCode()) {
            if (CollectionUtils.isEmpty(this.getCorrectArray())) {
                throw new ServiceException("请填写正确答案");
            }
        }
        if (qType == QuestionTypeEnum.GapFilling.getCode()) {
            Integer fillSumScore = getItems().stream()
                    .mapToInt(d -> ScoreUtils.scoreFromVM(d.getScore()))
                    .sum();
            Integer questionScore = ScoreUtils.scoreFromVM(getScore());
            if (!Objects.equals(fillSumScore, questionScore)) {
                throw new ServiceException("总分与空分数和不相等");
            }
        }
    }

}
