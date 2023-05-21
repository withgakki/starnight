package com.tracejp.starnight.entity.base;

import com.tracejp.starnight.utils.BeanUtils;
import com.tracejp.starnight.utils.ReflectUtils;

/**
 * <p> 转换抽象基类 输入dto（Param） => domain <p/>
 *
 * @author traceJP
 * @since 2023/4/30 14:52
 */
public interface IInputConverter<D> {

    /**
     * 转换为 domain
     */
    default D convertTo() {
        Class<D> domainClass = ReflectUtils.getClassGenricType(this.getClass());
        return BeanUtils.transformFrom(this, domainClass);
    }

    /**
     * 通过 domain 更新 dto
     */
    default void update(D domain) {
        BeanUtils.updateProperties(this, domain);
    }

}
