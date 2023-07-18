package com.tracejp.starnight.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tracejp.starnight.constants.CacheConstants;
import com.tracejp.starnight.dao.SubjectDao;
import com.tracejp.starnight.entity.SubjectEntity;
import com.tracejp.starnight.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
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

    @Override
    @Cacheable(value = CacheConstants.SUBJECT_LIST_CACHE_KEY, key = "'level:' + #level")
    public List<SubjectEntity> listByLevel(Integer level) {
        LambdaQueryWrapper<SubjectEntity> wrapper = Wrappers.lambdaQuery(SubjectEntity.class);
        if (level != null) {
            wrapper.eq(SubjectEntity::getLevel, level);
        }
        return list(wrapper);
    }

    @Override
    @CacheEvict(value = CacheConstants.SUBJECT_LIST_CACHE_KEY, allEntries = true)
    public boolean save(SubjectEntity entity) {
        return super.save(entity);
    }

    @Override
    @CacheEvict(value = CacheConstants.SUBJECT_LIST_CACHE_KEY, allEntries = true)
    public boolean updateById(SubjectEntity entity) {
        return super.updateById(entity);
    }

    @Override
    @CacheEvict(value = CacheConstants.SUBJECT_LIST_CACHE_KEY, allEntries = true)
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }

}