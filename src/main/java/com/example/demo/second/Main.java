package com.example.demo.second;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        //构建一个线程池用于提交任务
        ExecutorService executorService = Executors.newFixedThreadPool(4);
//        ExecutorService executorService2 = Executors.newFixedThreadPool(4);
        ExecutorService executorService2 = Executors.newFixedThreadPool(5);//几个handler监听，就几个，，单消费者模式就会这样，用多消费者模式就不会这样了
        //1.实例化 disruptor 对象
        Disruptor<Trade> disruptor = new Disruptor<Trade>(
                new EventFactory<Trade>() {
                    @Override
                    public Trade newInstance() {
                            return new Trade();
                    }
                },
                1024*1024,   //建议为2的n次方， 有利于运算
                executorService2,
                ProducerType.SINGLE,
                new BusySpinWaitStrategy());

        //2.添加消费者的监听  指定disruptor的 消费者

        //串行写法
         disruptor
                .handleEventsWith(new Handler1())
                .handleEventsWith(new Handler2())
                .handleEventsWith(new Handler3());

         //并行写法1
        disruptor.handleEventsWith(new Handler1());
        disruptor.handleEventsWith(new Handler2());
        disruptor.handleEventsWith(new Handler3());
        //并行写法2
        disruptor.handleEventsWith(new Handler1(),new Handler2(),new Handler3());

        //菱形操作 Handler1,Handler2 并行，俩个都执行完之后 执行Handler3

//        方法1：
//        disruptor.handleEventsWith(new Handler1(),new Handler2())
//                .handleEventsWith(new Handler3());
//        方法2：
//        EventHandlerGroup<Trade> eventHandlerGroup = disruptor.handleEventsWith(new Handler1(), new Handler2());
//        eventHandlerGroup.then(new Handler3());


        //多边形操作
//                 -> 1  ->  2
//            s ->|           | -> 3
//                 -> 4  ->  5

        Handler1 h1 =new Handler1();
        Handler2 h2 =new Handler2();
        Handler3 h3 =new Handler3();
        Handler4 h4 =new Handler4();
        Handler5 h5 =new Handler5();

        disruptor.handleEventsWith(h1,h4);
        disruptor.after(h1).handleEventsWith(h2);
        disruptor.after(h4).handleEventsWith(h5);
        disruptor.after(h2,h5).handleEventsWith(h3);






        //3.启动disruptor
        RingBuffer<Trade> start = disruptor.start();


        CountDownLatch countDownLatch =new CountDownLatch(1);

        executorService.submit(new TradePushlister(countDownLatch,disruptor));

        countDownLatch.await();

        disruptor.shutdown();
        executorService.shutdown();
        executorService2.shutdown();

    }
}
