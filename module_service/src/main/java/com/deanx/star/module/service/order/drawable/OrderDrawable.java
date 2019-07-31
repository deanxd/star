package com.deanx.star.module.service.order.drawable;

import com.deanx.brouter.api.core.Call;

/**
 * 订单模块对外暴露接口，其他模块可以获取返回res资源
 */
public interface OrderDrawable extends Call {

    int getDrawable();
}
