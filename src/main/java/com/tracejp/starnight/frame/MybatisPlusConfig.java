package com.tracejp.starnight.frame;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * <p> mybatis-plus 配置 <p/>
 *
 * @author traceJP
 * @since 2023/6/4 17:17
 */
@Configuration
public class MybatisPlusConfig {

    private static final String CREATE_TIME_PROPERTY = "createTime";

    private static final String UPDATE_TIME_PROPERTY = "updateTime";


    /**
     * 自动填充
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {

            @Override
            public void insertFill(MetaObject metaObject) {
                final Date NOW = new Date();
                // 创建时间
                Object createTimeAt = this.getFieldValByName(CREATE_TIME_PROPERTY, metaObject);
                if (createTimeAt == null) {
                    this.setFieldValByName(CREATE_TIME_PROPERTY, NOW, metaObject);
                }
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                final Date NOW = new Date();
                // 修改时间
                Object updatedAt = this.getFieldValByName(UPDATE_TIME_PROPERTY, metaObject);
                if (null == updatedAt) {
                    this.setFieldValByName(UPDATE_TIME_PROPERTY, NOW, metaObject);
                }
            }

        };
    }

}
