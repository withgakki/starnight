package com.tracejp.starnight.entity.base;

import com.tracejp.starnight.utils.BeanUtils;
import com.tracejp.starnight.utils.ReflectUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

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
    @SuppressWarnings("unchecked")
    default D convertTo() {
        ParameterizedType currentType = ReflectUtils.getParameterizedType(IInputConverter.class, this.getClass());
        Objects.requireNonNull(currentType, "无法获取实际类型，因为参数化类型为null");
        Class<D> domainClass = (Class<D>) currentType.getActualTypeArguments()[0];
        return BeanUtils.transformFrom(this, domainClass);
    }

    /**
     * 通过 domain 更新 dto
     */
    default void update(D domain) {
        BeanUtils.updateProperties(this, domain);
    }

}
