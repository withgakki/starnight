package com.tracejp.starnight.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tracejp.starnight.entity.SubjectEntity;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
public interface SubjectService extends IService<SubjectEntity> {

    /**
     * 分页
     */
    List<SubjectEntity> listPage(SubjectEntity subject);

    /**
     * 根据 id 获取年级
     */
    Integer getLevelById(Long subjectId);

}

