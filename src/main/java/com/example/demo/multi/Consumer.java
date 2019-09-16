package com.example.demo.multi;

import com.lmax.disruptor.WorkHandler;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements WorkHandler<Order> {
    private String consumerId;
    private static AtomicInteger count =new AtomicInteger(0);

    private Random random =new Random();
    public Consumer(String consumerId) {
        this.consumerId = consumerId;
    }


    @Override
    public void onEvent(Order event) throws Exception {
        TimeUnit.SECONDS.sleep(random.nextInt(5));
        System.out.println("当前消费者ID:"+consumerId+", 消费内容ID:"+event.getId());
        count.incrementAndGet();
    }

    public static AtomicInteger getCount() {
        return count;
    }

}
