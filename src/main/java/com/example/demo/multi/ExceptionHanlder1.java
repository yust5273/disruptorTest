package com.example.demo.multi;

import com.lmax.disruptor.ExceptionHandler;

public class ExceptionHanlder1 implements ExceptionHandler<Order> {

    @Override
    public void handleEventException(Throwable ex, long sequence, Order event) {

    }

    @Override
    public void handleOnStartException(Throwable ex) {

    }

    @Override
    public void handleOnShutdownException(Throwable ex) {

    }
}
