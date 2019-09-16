package com.example.demo.second;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class TradePushlister implements Runnable {
    private Disruptor<Trade> disruptor;
    private CountDownLatch countDownLatch;
    public TradePushlister(CountDownLatch countDownLatch, Disruptor<Trade> disruptor) {
        this.disruptor=disruptor;
        this.countDownLatch=countDownLatch;
    }

    @Override
    public void run() {
        TradeEventTranslator tradeEventTranslator = new TradeEventTranslator();
        for(int i=0;i<10;i++){
            //新的提交任务的方式
            disruptor.publishEvent(tradeEventTranslator);
        }
        countDownLatch.countDown();
    }

    class TradeEventTranslator implements EventTranslator<Trade>{
        @Override
        public void translateTo(Trade event, long sequence) {
            this.generateTrade(event);
        }

        private void generateTrade(Trade event) {
            event.setPrice(new Random().nextDouble()*9999);
        }
    }
}
