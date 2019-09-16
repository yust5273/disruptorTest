package com.example.demo.test1;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;

public class Main {

    public static void main(String[] args) {
        //0.参数准备
        OrderEventFactory eventFactory =new OrderEventFactory();
        int ringBufferSize = 1024*1024;
        //这里就随便用了一个executor，不必深究，就是随便用了一个，本节课的重点不是这个，所以这个就没认真写
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ProducerType producerType = ProducerType.SINGLE; //创建单生产者
        ProducerType multi = ProducerType.MULTI; //创建多生产者
        WaitStrategy waitStrategy = new BlockingWaitStrategy();//这里就先使用最简单的等待策略


        //1.实例化 disruptor 对象
        /**
         * 1.eventFactory: 消息（Event）工厂对象
         * 2.ringBufferSize: 容器的长度
         * 3.executor: 线程池（建议使用自定义线程池，，阿里巴巴Java开发手册，强制必须使用自定义线程池）    RejectedExecutionHandler
         * 4.producerType: 生产类型，单生产者、多生产者
         * 5.waitStrategy：等待策略
         */
        Disruptor<OrderEvent> disruptor =new Disruptor<OrderEvent>(
                eventFactory,
                ringBufferSize,
                executor,
                producerType,
                waitStrategy);

        //2.添加消费者的监听  指定disruptor的 消费者
        disruptor.handleEventsWith(new OrderEventHandler());

        //3.启动disruptor
        disruptor.start();

        //4.获取实际存储数据的容器 ,  实际负责数据存储的是 ringBuffer
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();

        //5.
        OrderEventProducer producer =new OrderEventProducer(ringBuffer);

        for(int i = 0; i<10000;i++){
            producer.sendData(i);
        }




        disruptor.shutdown();
        executor.shutdown();

    }


//    WaitStrategy
//            waitFor 等待
//            signalAllWhenBlocking 唤醒
//
}
