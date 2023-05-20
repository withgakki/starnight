package com.tracejp.starnight.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tracejp.starnight.dao.TextContentDao;
import com.tracejp.starnight.entity.TextContentEntity;
import com.tracejp.starnight.service.TextContentService;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("textContentService")
public class TextContentServiceImpl extends ServiceImpl<TextContentDao, TextContentEntity> implements TextContentService {

}