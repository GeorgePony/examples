package com.czc.example.curator.demo;

/**
 * @author 金陵笑笑生
 * @description: Curator的测试类
 * @date 2022/2/11下午11:01
 */
public class CuratorTest {

    private static final String NODE_NAME = "/chen";

    public static void main(String[] args) throws Exception{

        CuratorHolder holder = new CuratorHolder("127.0.0.1:2181", "czc");
        holder.createNode(NODE_NAME,"c");
        System.out.println(holder.getNodeValue(NODE_NAME));
        holder.updateNode(NODE_NAME,"ad");
        System.out.println(holder.getNodeValue(NODE_NAME));
        holder.deleteNode(NODE_NAME);

        holder.mutex("/mutex",()->{
            Thread.sleep(3000);
        });


    }
}
