package com.tracejp.starnight.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tracejp.starnight.dao.SubjectDao;
import com.tracejp.starnight.entity.SubjectEntity;
import com.tracejp.starnight.service.SubjectService;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("subjectService")
public class SubjectServiceImpl extends ServiceImpl<SubjectDao, SubjectEntity> implements SubjectService {

}