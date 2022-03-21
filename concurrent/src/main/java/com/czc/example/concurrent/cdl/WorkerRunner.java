package com.czc.example.concurrent.cdl;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author 金陵笑笑生
 * @description: TODO
 * @date 2022/3/15下午10:46
 */
public class WorkerRunner implements Runnable{

    private final CountDownLatch latch ;
    private final int i ;

    public WorkerRunner(CountDownLatch latch, int i) {
        this.latch = latch;
        this.i = i;
    }

    @Override
    public void run() {
        try{
            doWork();
            this.latch.countDown();
            System.out.println("thread " + i + " finished");
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }
    private void doWork() throws Exception{
        Thread.sleep(1000 + new Random().nextInt(1000));
    }
}
