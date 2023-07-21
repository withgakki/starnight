package com.tracejp.starnight.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tracejp.starnight.constants.ElasticSearchConstants;
import com.tracejp.starnight.dao.UserDao;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.dto.SearchUserDto;
import com.tracejp.starnight.exception.ServiceException;
import com.tracejp.starnight.service.UserService;
import com.tracejp.starnight.utils.ElasticSearchUtils;
import com.tracejp.starnight.utils.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private ElasticSearchUtils esUtils;

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
    public List<SearchUserDto> searchDtoByKeyword(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return new ArrayList<>();
        }

        // 构建查询条件
        final String USER_NAME = "userName";
        final String REAL_NAME = "realName";
        final String PHONE = "phone";
        SearchSourceBuilder queryParams = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.fuzzyQuery(USER_NAME, keyword))
                .should(QueryBuilders.fuzzyQuery(REAL_NAME, keyword))
                .should(QueryBuilders.fuzzyQuery(PHONE, keyword));
        queryParams.query(boolQueryBuilder);
        queryParams.size(10);

        return esUtils.listDocument(ElasticSearchConstants.USER_INDEX, queryParams, SearchUserDto.class);
    }

    @Override
    public void changeStatus(Long id) {
        userDao.changeStatus(id);
    }

    @Transactional
    @Override
    public void saveToAll(UserEntity user) {
        this.save(user);

        // 保存 es
        SearchUserDto dto = new SearchUserDto().convertFrom(user);
        boolean success = esUtils.createDocument(ElasticSearchConstants.USER_INDEX, user.getId(), dto);
        if (!success) {
            throw new ServiceException("用户信息保存至 elastic search 失败");
        }
    }

    @Transactional
    @Override
    public boolean updateToAll(UserEntity user) {
        // 更新 es
        SearchUserDto dto = new SearchUserDto().convertFrom(user);
        boolean success = esUtils.updateDocument(ElasticSearchConstants.USER_INDEX, user.getId(), dto);
        if (!success) {
            throw new ServiceException("用户信息更新至 elastic search 失败");
        }

        return this.updateById(user);
    }

    @Override
    public boolean removeToAllByIds(List<Long> ids) {
        // 删除 es
        boolean success = esUtils.deleteBatchDocument(ElasticSearchConstants.USER_INDEX, ids);
        if (!success) {
            throw new ServiceException("用户信息从 elastic search 删除失败");
        }
        return this.removeByIds(ids);
    }

}