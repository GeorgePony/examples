package com.czc.example.algorithm.heap;

import com.czc.example.algorithm.sorting.SortUtil;

import java.util.Random;

/**
 * 考虑到计算方面的便利，堆中第一个元素的下标设置为1,而非0
 * @author 金陵笑笑生
 * @description: TODO
 * @date 2022/3/26下午3:04
 */
public class BinaryHeap {
    private int[] data;
    int count;

    public BinaryHeap(int capacity){
        this.data = new int[capacity + 1];
        this.count = 0;
    }

    public BinaryHeap(int[] data){
        int length = data.length;
        this.data = new int[length + 1];
        for(int i = 0 ; i < length ; i ++){
            this.data[i + 1] = data[i];
        }
        this.count = length;

        /**
         * heapify 操作: 从第一个不是叶子节点的位置开始，进行ShiftDown操作
         * heapify 算法复杂度为 O(n)
         * 而将n个元素逐个插入到一个空堆中，算法复杂度为O(nlogn)
         */
        for(int i = this.count / 2 ; i >= 1 ; i --){
            this.shiftDown(i);
        }
    }

    public void insert(int item){
        this.data[count + 1] = item;
        this.count ++;
        shiftUp(count);
    }

    public int get(int index){
        return this.data[index];
    }

    public int pop(){
        if(this.count == 0){
            throw new RuntimeException("empty array");
        }
        int ret = this.data[1];
        SortUtil.swap(this.data,1,this.count);
        this.count --;
        shiftDown(1);
        return ret;
    }

    private void shiftUp(int index){
        while (index > 1 && data[index /2 ] < data[index]){
            SortUtil.swap(data,index ,index / 2);
            index = index / 2;
        }
    }

    private void shiftDown(int index){
        while (2 * index <= this.count){
            int j = 2 * index;
            if(j + 1 <= count && this.data[j + 1] > data[j]){
                j += 1;
            }
            if(this.data[index] >= data[j]){
                break;
            }
            SortUtil.swap(data,index ,j);
            index = j;
        }

    }

    public static void main(String[] args) {
        BinaryHeap b = new BinaryHeap(2000);
        Random r = new Random();
        for(int i = 0 ; i < 100; i ++){
            b.insert(r.nextInt(100));
        }

        for(int i = 0 ; i < 100; i ++){
            System.out.print(String.format("%d  " ,b.get(i)));
        }

        System.out.println();
        int pop = b.pop();
        System.out.println(pop);


        for(int i = 0 ; i < 99; i ++){
            System.out.print(String.format("%d  " ,b.get(i)));
        }
        System.out.println();



    }

}
