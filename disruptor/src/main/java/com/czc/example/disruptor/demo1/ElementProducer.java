package com.czc.example.disruptor.demo1;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @author 金陵笑笑生
 * @description: TODO
 * @date 2022/2/22下午10:38
 */
public class ElementProducer {
    private final RingBuffer<Element> ringBuffer;


    public ElementProducer(RingBuffer<Element> ringBuffer){
        this.ringBuffer = ringBuffer;
    }

    public void onData(String value){
        // 1. 可以把RingBuffer看成是一个事件队列，那么next就是得到下面一个事件槽
        long sequence = ringBuffer.next();
        try{
            // 2. 用第一步得到的索引取出一个空的事件用于填充(获取该)序号对应的事件对象
            Element element = ringBuffer.get(sequence);
            System.out.println("previous value :" + element.getValue() + " ; new value :" + value);
            // 3. 获取要通过事件传递的业务数据
            element.setValue(value);
        }finally {
            // 4. 发布事件
            // 注意，这里放在finally中以确保必须得到调用
            ringBuffer.publish(sequence);
        }
    }
}
