package com.tracejp.starnight.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tracejp.starnight.dao.ExamPaperDao;
import com.tracejp.starnight.entity.ExamPaperEntity;
import com.tracejp.starnight.service.ExamPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("examPaperService")
public class ExamPaperServiceImpl extends ServiceImpl<ExamPaperDao, ExamPaperEntity> implements ExamPaperService {

    @Autowired
    private ExamPaperDao examPaperDao;


    @Override
    public List<ExamPaperEntity> listPage(ExamPaperEntity examPaper) {
        return examPaperDao.listPage(examPaper);
    }

}