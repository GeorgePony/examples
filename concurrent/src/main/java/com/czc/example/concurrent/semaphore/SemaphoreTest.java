package com.czc.example.concurrent.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author 金陵笑笑生
 * @description: TODO
 * @date 2022/2/20下午10:57
 */
public class SemaphoreTest {

    /**
     *
     * Semaphore也是一个线程同步的辅助类，可以维护当前访问自身的线程个数，并提供了同步机制。
     * 使用Semaphore可以控制同时访问资源的线程个数，例如，实现一个文件允许的并发访问数。
     * Semaphore非常适用于高并发，Semaphore很适用于限流。
     *
     *
     * * void acquire():从此信号量获取一个许可，在提供一个许可前一直将线程阻塞，否则线程被中断。
     * * void release():释放一个许可，将其返回给信号量。
     * * int availablePermits():返回此信号量中当前可用的许可数。
     * * boolean hasQueuedThreads():查询是否有线程正在等待获取。
     *
     * @param args
     */
    public static void main(String[] args) {

        final Semaphore semaphore = new Semaphore(5);
        ExecutorService service = Executors.newCachedThreadPool();
        for(int i = 0 ; i < 21 ; i ++){
            final String name = "冬天" + i;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(name + "进来找座位");
                        semaphore.acquire();
                        System.out.println(name + "找到了座位");
                        Thread.sleep(3000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        System.out.println(name + "吃完了离开");
                        semaphore.release();
                    }
                }
            });
        }
    }

}
