package com.tracejp.starnight.controller.admin;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.dto.UserDto;
import com.tracejp.starnight.entity.dto.UserSearchEsDto;
import com.tracejp.starnight.entity.enums.UserStatusEnum;
import com.tracejp.starnight.entity.param.UserEditParam;
import com.tracejp.starnight.service.UserService;
import com.tracejp.starnight.utils.SecurityUtils;
import com.tracejp.starnight.utils.StringUtils;
import com.tracejp.starnight.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("/admin/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(UserEntity user) {
        startPage();
        List<UserEntity> list = userService.listPage(user);
        List<UserDto> collect = list.stream()
                .map(item -> (UserDto) new UserDto().convertFrom(item))
                .collect(Collectors.toList());
        TableDataInfo dataTable = getDataTable(list);
        dataTable.setRows(collect);
        return dataTable;
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
        UserEntity user = userService.getById(id);
        return success(new UserDto().convertFrom(user));
    }

    /**
     * 搜索用户
     */
    @GetMapping("/search")
    public AjaxResult search(String keyword) {
        List<UserSearchEsDto> list = userService.searchDtoByKeyword(keyword);
        return success(list);
    }

    /**
     * 保存
     */
    @PostMapping
    public AjaxResult save(@RequestBody UserEditParam user) {
        UserEntity userEntity = user.convertTo();
        userEntity.setUserUuid(UUIDUtils.fastUUID().toString());
        userEntity.setStatus(UserStatusEnum.Enable.getCode());
        if (StringUtils.isNotEmpty(user.getPassword())) {
            userEntity.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        }
        userService.saveToAll(userEntity);
        return success();
    }

    /**
     * 修改
     */
    @PutMapping
    public AjaxResult update(@RequestBody UserEditParam user) {
        UserEntity userEntity = user.convertTo();
        if (StringUtils.isNotEmpty(user.getPassword())) {
            userEntity.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        }
        userService.updateToAll(userEntity);
        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable List<Long> ids) {
        userService.removeToAllByIds(ids);
        return success();
    }

    /**
     * 改变用户状态
     */
    @PutMapping("/status/{id}")
    public AjaxResult changeStatus(@PathVariable Long id) {
        userService.changeStatus(id);
        return success();
    }

}
