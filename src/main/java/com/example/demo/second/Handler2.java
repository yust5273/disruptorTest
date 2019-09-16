package com.example.demo.second;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class Handler2 implements EventHandler<Trade> {

    //EventHandler
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        event.setId(UUID.randomUUID().toString());
        System.out.println("Handler2:set id  ");
        TimeUnit.SECONDS.sleep(1);
    }

}
