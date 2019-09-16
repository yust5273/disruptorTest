package com.example.demo.test1;

import com.lmax.disruptor.EventFactory;

/**
 * 工厂Event类，用于创建Event类实例对象
 */
public class OrderEventFactory implements EventFactory<OrderEvent> {

    @Override
    public OrderEvent newInstance() {
        return new OrderEvent();
        //注意，这里new的是一个 "内容为空"(属性值都为null或者默认值) 的OrderEvent对象
        //专业术语叫做Event
    }
}
