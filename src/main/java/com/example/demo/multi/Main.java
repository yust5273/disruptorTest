package com.example.demo.multi;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //1.创建ringbuffer
        RingBuffer<Order> ringBuffer = RingBuffer.create(
                ProducerType.MULTI,
                Order::new,
                1024*1024,
                new YieldingWaitStrategy());

        //2.通过ringbuffer创建一个屏障
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
        //3.构建多消费者
        Consumer[] consumers =new Consumer[10];
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer("C"+i);
        }
        //4.构建多消费者工作池
        WorkerPool<Order> workerPool =new WorkerPool<Order>(ringBuffer,sequenceBarrier,new ExceptionHanlder1(),consumers);

        //5.设置多个消费者的sequence序号，用于单独统计消费进度
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
        //6.启动
        workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));


        CountDownLatch countDownLatch =new CountDownLatch(1);

        for (int i = 0; i < 100; i++) {
            Producer producer =new Producer(ringBuffer);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    for (int j =  0; j < 100 ; j++) {
                        producer.sendData(UUID.randomUUID().toString());
                    }
                }
            }).start();

        }
        TimeUnit.SECONDS.sleep(3);
        System.out.println("线程创建完毕，开始生产数据");
        countDownLatch.countDown();
    }
}
