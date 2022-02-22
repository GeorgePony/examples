package com.czc.example.disruptor.demo1;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
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
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(8, factory);

        /**
         *事件工厂,用于生产队列元素，当Disruptor刚初始化的时候，队列工厂会生产元素直到把RingBuffer填充满
         */
        EventFactory<Element> eventFactory = Element::new;

        /**
         * Disruptor主动将事件推送给消费者，而不是消费者轮询来获取事件
         * 这里的Handler就是主动推送的功能实体。
         */
        EventHandler<Element> eventHandler = (element, sequence, b)
                ->
                System.out.println("Element: " + Thread.currentThread().getName() + ": "
                        + element.getValue() + ": " + sequence);

        /**
         * 等待策略，用于决定消费者在没有数据的时候，执行怎样的等待策略
         * 主要有如下几种等待策略：
         *   BlockingWaitStrategy: 通过线程阻塞的方式，等待生产者唤醒,被唤醒后，再循环等待依赖的Sequence是否已经消费
         *   BusySpinWaitStrategy: 线程一直自旋等待，可能比较耗cpu
         *   YieldingWaitStrategy: 尝试100次，然后Thread.yield()让出cpu
         *
         */
        BlockingWaitStrategy strategy = new BlockingWaitStrategy();

        /**
         * 这个缓冲池大小一定要为2的n次方
         */
        int bufferSize = 8;

        /**
         * 第一个参数是工厂类对象
         * 第二个参数是缓冲区大小
         * 第三个参数是线程池 进行Disruptor内部的数据接收处理调度
         * 第四个参数指定生产者有一个还是多个
         * 第五个参数是一种策略:
         *     BLOCKING_WAIT : 最低效的策略，但是其对CPU的消耗最小并且在各种不同的部署环境中能够提供更加一致的性能表现
         *     SLEEPING_WAIT : 和BLOCKING_WAIT差不多，对CPU的消耗也小，但是其对生产者的线程影响最小，适合于异步日志类
         *     YIELDING_WAIT : 性能是最好的，适用于低延时的系统，在要求极高的性能且事件处理线程小于CPU核心数目的场景下，推荐使用此策略。
         *
         */
        Disruptor<Element> disruptor =
                new Disruptor<>(eventFactory,bufferSize,
                        executor,
                        ProducerType.SINGLE,strategy);

        /**
         * 连接消费事件方法
         * Handler可以理解为将Event推送给消费者
         */
        disruptor.handleEventsWith(eventHandler);
        /**
         * 启动
         */
        disruptor.start();

        /**
         * Disruptor发布事件是一个两阶段提交的过程
         *
         * 使用该方法获取具体存放数据的容器(RingBuffer)
         */
        RingBuffer<Element> ringBuffer = disruptor.getRingBuffer();

        ElementProducer producer = new ElementProducer(ringBuffer);
        for(long l = 0 ; l < 100 ; l ++){
            producer.onData("dis" + l);
        }

        /**
         * 也可以这么做
         */
//        for(int  i = 0 ; i < 10 ; i ++){
//            disruptor.publishEvent((element,sequence) -> {
//                System.out.println("previous data:" + element.getValue() + ";current seq:" + sequence);
//                element.setValue("this is " + sequence + "");
//
//            });
//        }
        disruptor.shutdown();
        executor.shutdown();


    }

}
