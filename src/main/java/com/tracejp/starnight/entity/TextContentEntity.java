package com.tracejp.starnight.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:37:40
 */
@Data
@TableName("t_text_content")
public class TextContentEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId
    private Long id;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 文本内容
     */
    private String content;

    public <T> void setContent(T content) {
        this.content = JSON.toJSONString(content);
    }

    public <T> T getContent(Class<T> clazz) {
        return JSON.parseObject(JSON.parse(content).toString(), clazz);
    }

    public <T> List<T> getContentArray(Class<T> clazz) {
        return JSON.parseArray(JSON.parse(content).toString(), clazz);
    }

}
