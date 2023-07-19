package com.tracejp.starnight.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tracejp.starnight.entity.MessageEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 22:16:50
 */
@Mapper
public interface MessageDao extends BaseMapper<MessageEntity> {

    /**
     * 分页查询
     */
    List<MessageEntity> listPage(MessageEntity message);

    /**
     * 消息读取数 ++
     */
    void readIncrById(Long id);
}
