package com.example.demo.second;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.concurrent.TimeUnit;


public class Handler1 implements EventHandler<Trade>, WorkHandler<Trade> {

    //EventHandler
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        this.onEvent(event);
    }

    //WorkHandler
    @Override
    public void onEvent(Trade event) throws Exception {
        event.setName("aaa");
        System.out.println("Handler1: set name ");
        TimeUnit.SECONDS.sleep(1);
    }
}
