package com.czc.example.curator.demo;

/**
 * @author 金陵笑笑生
 * @description: 用于zk分布式锁的业务操作
 * @date 2022/2/11下午11:01
 */
@FunctionalInterface
public interface MutexAction {

    /**
     * 用于分布式锁的业务操作
     * @throws Exception
     */
    void doAction() throws Exception;

}
