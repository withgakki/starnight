package com.tracejp.starnight.controller.student;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.MessageEntity;
import com.tracejp.starnight.entity.MessageUserEntity;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.entity.base.TableDataInfo;
import com.tracejp.starnight.entity.vo.student.MessageUserVo;
import com.tracejp.starnight.service.MessageService;
import com.tracejp.starnight.service.MessageUserService;
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
@RestController("studentMessageUserController")
@RequestMapping("/student/messageuser")
public class MessageUserController extends BaseController {

    @Autowired
    private MessageUserService messageUserService;

    @Autowired
    private MessageService messageService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list() {
        Long userId = SecurityUtils.getUserId();
        startPage();
        List<MessageUserEntity> list = messageUserService.listPage(userId);

        // 封装 vo
        if (!CollectionUtils.isEmpty(list)) {
            List<Long> msgIds = list.stream().map(MessageUserEntity::getMessageId).collect(Collectors.toList());
            List<MessageEntity> messages = messageService.listByIds(msgIds);
            List<MessageUserVo> vos = list.stream().map(item -> {
                MessageUserVo vo = new MessageUserVo();
                BeanUtils.copyProperties(item, vo);
                for (MessageEntity message : messages) {
                    if (Objects.equals(message.getId(), item.getMessageId())) {
                        vo.setSendUserName(message.getSendUserName());
                        vo.setTitle(message.getTitle());
                        vo.setContent(message.getContent());
                        break;
                    }
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
     * 获取未读消息数量
     */
    @GetMapping("/unread/count")
    public AjaxResult unReadCount() {
        Long userId = SecurityUtils.getUserId();
        Integer count = messageUserService.getUnReadCount(userId);
        return success(count);
    }

    /**
     * 读取一条消息
     */
    @PostMapping("/read/{id}")
    public AjaxResult read(@PathVariable Long id) {
        messageUserService.readOne(id);
        return success();
    }

}
