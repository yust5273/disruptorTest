package com.example.demo.test1;

import com.lmax.disruptor.RingBuffer;

public class OrderEventProducer {
    private RingBuffer<OrderEvent> ringBuffer;

    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void sendData(int data){
        //1.首先从ringBuffer中获取一个可用的序号
        long sequence = ringBuffer.next();
        try {

            //2.根据这个序号，找到具体的"OrderEvent"元素。此时"orderEvent"是一个没有用无参构造函数new出来的，对象中的属性没有被赋值的“空对象”
            OrderEvent event = ringBuffer.get(sequence);
            //3.进行赋值
            event.setValue(data);

        }finally {
            //4.提交操作
            ringBuffer.publish(sequence);
        }


    }
}
