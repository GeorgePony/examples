package com.czc.example.disruptor.demo1;


import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class DisruptorTest {

    public static void main(String[] args) {

        /**
         * 这是一个线程工厂,用于Disruptor中生产，消费的时候需要的线程。
         */
        ThreadFactory factory = new ThreadFactory() {
            int i = 0;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r ,"simpleThread" + String.valueOf(i ++));
            }
        };

        /**
         *事件工厂,用于生产队列元素，当Disruptor刚初始化的时候，队列工厂会生产元素直到把RingBuffer填充满
         */
        EventFactory<Element> eventFactory = new EventFactory<Element>() {
            @Override
            public Element newInstance() {
                return new Element();
            }
        };

        EventHandler<Element> eventHandler = new EventHandler<Element>() {
            @Override
            public void onEvent(Element element, long sequence, boolean b) throws Exception {
                System.out.println("Element: " + Thread.currentThread().getName() + ": " + element.getValue() + ": " + sequence);
            }
        };

        /**
         * 等待策略，用于决定消费者在没有数据的时候，执行怎样的等待策略
         * 主要有如下几种等待策略：
         *   BlockingWaitStrategy: 通过线程阻塞的方式，等待生产者唤醒,被唤醒后，再循环等待依赖的Sequence是否已经消费
         *   BusySpinWaitStrategy: 线程一直自旋等待，可能比较耗cpu
         *   YieldingWaitStrategy: 尝试100次，然后Thread.yield()让出cpu
         *
         */
        BlockingWaitStrategy strategy = new BlockingWaitStrategy();

        int bufferSize = 8;

        Disruptor<Element> disruptor = new Disruptor<>(eventFactory,bufferSize,new ScheduledThreadPoolExecutor(8,factory), ProducerType.SINGLE,strategy);

        disruptor.handleEventsWith(eventHandler);
        disruptor.start();
        for(int  i = 0 ; i < 10 ; i ++){
            disruptor.publishEvent((element,sequence) -> {
                System.out.println("previous data:" + element.getValue() + ";current seq:" + sequence);
                element.setValue("this is " + sequence + "");

            });
        }

    }

}
