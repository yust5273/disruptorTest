package com.example.demo.second;

import com.lmax.disruptor.EventHandler;

import java.util.concurrent.TimeUnit;


public class Handler5 implements EventHandler<Trade> {

    //EventHandler
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {

        System.out.println("Handler5: get price  ");
        TimeUnit.SECONDS.sleep(1);
        double price = event.getPrice();
        System.out.println(price);
    }

}
