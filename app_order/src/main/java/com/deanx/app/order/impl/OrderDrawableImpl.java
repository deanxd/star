package com.deanx.app.order.impl;

import com.deanx.app.order.R;
import com.deanx.brouter.annotation.BRouter;
import com.deanx.star.module.service.order.drawable.OrderDrawable;

/**
 * 订单模块对外暴露接口实现类，其他模块可以获取返回res资源
 */
@BRouter(path = "/app_order/getDrawable")
public class OrderDrawableImpl implements OrderDrawable {

    @Override
    public int getDrawable() {
        return R.drawable.ic_accessibility_black_24dp;
    }
}
