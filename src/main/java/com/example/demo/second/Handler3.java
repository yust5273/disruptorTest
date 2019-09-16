package com.example.demo.second;

import com.lmax.disruptor.EventHandler;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class Handler3 implements EventHandler<Trade> {

    //EventHandler
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        event.setId(UUID.randomUUID().toString());
        System.out.println("Handler3: print log  ");
        TimeUnit.SECONDS.sleep(1);
    }

}
