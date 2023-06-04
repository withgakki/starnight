package com.tracejp.starnight.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tracejp.starnight.dao.SubjectDao;
import com.tracejp.starnight.entity.SubjectEntity;
import com.tracejp.starnight.service.SubjectService;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("subjectService")
public class SubjectServiceImpl extends ServiceImpl<SubjectDao, SubjectEntity> implements SubjectService {

    @Autowired
    private SubjectDao subjectDao;


    @Override
    public List<SubjectEntity> listPage(SubjectEntity subject) {
        return subjectDao.listPage(subject);
    }

    @Override
    public Integer getLevelById(Long subjectId) {
        LambdaQueryWrapper<SubjectEntity> wrapper = Wrappers.lambdaQuery(SubjectEntity.class)
                .eq(SubjectEntity::getId, subjectId)
                .select(SubjectEntity::getLevel);
        return getOne(wrapper).getLevel();
    }

}