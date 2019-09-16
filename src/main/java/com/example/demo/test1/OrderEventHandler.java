package com.example.demo.test1;

import com.lmax.disruptor.EventHandler;

/**
 * 监听事件类，用于处理数据
 * 其实就是具体的消费者
 */
public class OrderEventHandler implements EventHandler<OrderEvent> {

    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("消费者:   "+event.getValue());
    }
}
