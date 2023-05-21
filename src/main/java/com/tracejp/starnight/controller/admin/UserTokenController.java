package com.tracejp.starnight.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.tracejp.starnight.entity.UserTokenEntity;
import com.tracejp.starnight.service.UserTokenService;
import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.base.AjaxResult;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:37
 */
@RestController
@RequestMapping("admin/usertoken")
public class UserTokenController extends BaseController {

    @Autowired
    private UserTokenService userTokenService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(UserTokenEntity userToken) {
        startPage();
        QueryWrapper<UserTokenEntity> queryWrapper = new QueryWrapper<>(userToken);
        List<UserTokenEntity> list = userTokenService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
		UserTokenEntity userToken = userTokenService.getById(id);
        return success(userToken);
    }

    /**
     * 保存
     */
    @PostMapping
    public AjaxResult save(@RequestBody UserTokenEntity userToken) {
		userTokenService.save(userToken);
        return success();
    }

    /**
     * 修改
     */
    @PutMapping
    public AjaxResult update(@RequestBody UserTokenEntity userToken) {
		userTokenService.updateById(userToken);
        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping
    public AjaxResult delete(@RequestBody List<Long> ids) {
		userTokenService.removeByIds(ids);
        return success();
    }

}
