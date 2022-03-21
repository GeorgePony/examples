package com.czc.example.concurrent.cdl;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 *
 * CountDownLatch与CycleBarrier
 * 1 CountDownLatch 是一个线程等待多个线程执行结束
 * 2 CycleBarrier  是多个线程等待多个线程执行好再一起执行后续操作。
 *
 *
 * @author 金陵笑笑生
 * @description: TODO
 * @date 2022/3/15下午10:46
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws Exception{
        CountDownLatch latch = new CountDownLatch(8);
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(8);
        for(int i = 0 ; i < 8 ; i ++){
            executor.execute(new WorkerRunner(latch,i));
        }
        latch.await();
        System.out.println("main finished");
    }
}
