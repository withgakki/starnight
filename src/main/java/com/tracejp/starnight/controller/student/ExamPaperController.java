package com.tracejp.starnight.controller.student;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.ExamPaperEntity;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.enums.ExamPaperTypeEnum;
import com.tracejp.starnight.entity.param.SearchPageParam;
import com.tracejp.starnight.entity.vo.ExamPaperVo;
import com.tracejp.starnight.entity.vo.student.ExamPaperIndexVo;
import com.tracejp.starnight.entity.vo.student.ExamPaperSearchVo;
import com.tracejp.starnight.service.ExamPaperService;
import com.tracejp.starnight.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/6/11 10:36
 */
@RestController("studentExamPaperController")
@RequestMapping("/student/exampaper")
public class ExamPaperController extends BaseController {

    @Autowired
    private ExamPaperService examPaperService;

    /**
     * 首页试卷列表
     */
    @RequestMapping("/list/index")
    public AjaxResult listIndex() {
        Integer level = SecurityUtils.getLoginUser().getUser().getUserLevel();
        List<ExamPaperEntity> fixedList = examPaperService.listStudentIndexPage(level, ExamPaperTypeEnum.FIXED.getCode());
        List<ExamPaperEntity> timeLimitList = examPaperService.listStudentIndexPage(level, ExamPaperTypeEnum.TIME_LIMIT.getCode());
        ExamPaperIndexVo vo = new ExamPaperIndexVo();
        vo.setFixedPaper(fixedList);
        vo.setTimeLimitPaper(timeLimitList);
        return success(vo);
    }

    /**
     * 试卷列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ExamPaperEntity examPaper) {
        startPage();
        List<ExamPaperEntity> list = examPaperService.listPage(examPaper);
        return getDataTable(list);
    }

    /**
     * 搜索试卷
     */
    @GetMapping("/search")
    public AjaxResult search(SearchPageParam searchParam) {
        ExamPaperSearchVo list = examPaperService.searchDtoByKeyword(searchParam);
        return success(list);
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
        ExamPaperVo examPaper = examPaperService.getExamPaperVo(id);
        return success(examPaper);
    }

}
