package com.tracejp.starnight.controller.admin;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.MessageEntity;
import com.tracejp.starnight.entity.MessageUserEntity;
import com.tracejp.starnight.entity.UserEntity;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.param.SendMessageParam;
import com.tracejp.starnight.entity.vo.MessageVo;
import com.tracejp.starnight.service.MessageService;
import com.tracejp.starnight.service.MessageUserService;
import com.tracejp.starnight.utils.ArrayStringUtils;
import com.tracejp.starnight.utils.BeanUtils;
import com.tracejp.starnight.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author traceJP
 * @since 2023-05-20 23:19:38
 */
@RestController
@RequestMapping("/admin/message")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageUserService messageUserService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MessageEntity message) {
        startPage();
        List<MessageEntity> list = messageService.listPage(message);

        // 封装 vo
        if (!CollectionUtils.isEmpty(list)) {
            List<Long> msgIds = list.stream().map(MessageEntity::getId).collect(Collectors.toList());
            List<MessageUserEntity> msgUsers = messageUserService.listByMessageIds(msgIds);
            List<MessageVo> vos = list.stream().map(item -> {
                MessageVo vo = new MessageVo();
                BeanUtils.copyProperties(item, vo);
                if (!CollectionUtils.isEmpty(msgUsers)) {
                    List<String> receiveUserNames = msgUsers.stream()
                            .filter(u -> Objects.equals(u.getMessageId(), item.getId()))
                            .map(MessageUserEntity::getReceiveUserName)
                            .collect(Collectors.toList());
                    vo.setReceives(ArrayStringUtils.contentToString(receiveUserNames));
                }
                return vo;
            }).collect(Collectors.toList());
            TableDataInfo dataTable = getDataTable(list);
            dataTable.setRows(vos);
            return dataTable;
        }

        return getDataTable(list);
    }

    /**
     * 发送消息
     */
    @PostMapping
    public AjaxResult send(@RequestBody SendMessageParam messageParam) {
        UserEntity user = SecurityUtils.getLoginUser().getUser();
        messageService.sendMessage(messageParam, user);
        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable List<Long> ids) {
        messageService.removeAllByIds(ids);
        return success();
    }

}
