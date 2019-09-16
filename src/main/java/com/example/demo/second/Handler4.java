package com.example.demo.second;

import com.lmax.disruptor.EventHandler;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class Handler4 implements EventHandler<Trade> {

    //EventHandler
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        event.setPrice(111.11);
        System.out.println("Handler4: set price  ");
        TimeUnit.SECONDS.sleep(1);
    }

}
