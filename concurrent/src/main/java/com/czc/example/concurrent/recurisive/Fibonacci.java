package com.czc.example.concurrent.recurisive;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author 金陵笑笑生
 * @description: ForkJoinTask使用了分治思想,通过fork异步执行一个子任务,通过join阻塞当前线程等待子任务执行完毕
 * @date 2022/2/20下午10:48
 */
public class Fibonacci extends RecursiveTask<Integer> {
    private Integer n ;

    public Fibonacci(Integer n){
        this.n = n;
    }

    @Override
    protected Integer compute() {

        if(n <= 1){
            return n;
        }
        Fibonacci f1 = new Fibonacci(n - 1);
        f1.fork();
        Fibonacci f2 = new Fibonacci(n -2);
        return f2.compute() + f1.join();
    }


    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        Fibonacci fibonacci = new Fibonacci(8);
        System.out.println(forkJoinPool.invoke(fibonacci));
    }
}
