package com.czc.example.curator.demo;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author 金陵笑笑生
 * @description: Curator的单例模式
 * @date 2022/2/11下午11:01
 */
public class CuratorHolder {

    private CuratorFramework client;

    public CuratorHolder(String host,String namespace){
        this.client = CuratorFrameworkFactory.newClient(host,new ExponentialBackoffRetry(1000,3));
        this.client.start();
        if(!StringUtils.isBlank(namespace)){
            this.client.usingNamespace(namespace);
        }
    }

    public boolean createNode(String nodeName,String value)throws Exception{
        boolean flag = false;
        Stat stat = this.client.checkExists().forPath(nodeName);
        if(stat == null){
            String result = null;
            if(Strings.isNullOrEmpty(value)){
                result = this.client.create().creatingParentsIfNeeded().forPath(nodeName);
            }else{
                result = this.client.create().creatingParentsIfNeeded().forPath(nodeName,value.getBytes(StandardCharsets.UTF_8));
            }
            flag = Objects.equals(nodeName,result);
        }
        return flag;
    }

    public boolean updateNode(String nodeName,String value)throws Exception{
        boolean flag = false;
        Stat stat = this.client.checkExists().forPath(nodeName);
        if(stat != null){
            Stat result = this.client.setData().forPath(nodeName, value.getBytes(StandardCharsets.UTF_8));
            flag = result != null;
        }
        return flag;
    }

    public void deleteNode(String nodeName)throws Exception{
        this.client.delete().deletingChildrenIfNeeded().forPath(nodeName);
    }

    public String getNodeValue(String nodeName)throws Exception{
        return new String(this.client.getData().forPath(nodeName),StandardCharsets.UTF_8);
    }

    public void mutex(String mutex,MutexAction action)throws Exception{
        InterProcessMutex interprocessMutex = new InterProcessMutex(this.client, mutex);
        interprocessMutex.acquire();
        System.out.println("已经获取锁");
        action.doAction();
        interprocessMutex.release();
        System.out.println("锁解除");

    }

}
