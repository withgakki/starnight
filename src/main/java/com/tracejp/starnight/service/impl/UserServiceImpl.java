package com.tracejp.starnight.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tracejp.starnight.constants.ElasticSearchConstants;
import com.tracejp.starnight.dao.UserDao;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.dto.UserSearchEsDto;
import com.tracejp.starnight.exception.ServiceException;
import com.tracejp.starnight.service.UserService;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RestHighLevelClient esClient;

    @Override
    public List<UserEntity> listPage(UserEntity user) {
        return userDao.listPage(user);
    }

    @Override
    public UserEntity getByUserName(String username) {
        LambdaQueryWrapper<UserEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserEntity::getUserName, username);
        return getOne(wrapper);
    }

    @Override
    public void changeStatus(Long id) {
        userDao.changeStatus(id);
    }

    @Transactional
    @Override
    public void saveToAll(UserEntity user) {
        super.save(user);

        // 保存至 elastic search
        UserSearchEsDto dto = new UserSearchEsDto().convertFrom(user);
        String dtoStr = JSON.toJSONString(dto);
        IndexRequest request = new IndexRequest()
                .index(ElasticSearchConstants.USER_INDEX)
                .id(user.getId().toString())
                .source(dtoStr, XContentType.JSON);
        try {
            IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);
            if (response.getResult() != DocWriteResponse.Result.CREATED) {
                throw new ServiceException("保存用户信息至 elastic search 失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException("保存用户信息至 elastic search 失败: " + e.getMessage());
        }
    }

}