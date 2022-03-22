package com.czc.example.algorithm.dynamicProgramming;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 金陵笑笑生
 * @description: 斐波拉切问题
 * @date 2022/3/22下午9:27
 */
public class Fibonacci {

    private Map<Integer,Integer> mem = new HashMap<>();

    private int addToMem(int n){
        if(n == 1 || n == 2){
            return 1;
        }
       if(mem.containsKey(n)){
           return mem.get(n);
       }
       int result = addToMem(n - 1) + addToMem(n - 2);
       mem.put(n,result);
       return result;
    }

    public static void main(String[] args) {
        Fibonacci fi = new Fibonacci();
        System.out.println(fi.addToMem(4));
        System.out.println(fi.addToMem(5));
        System.out.println(fi.addToMem(6));
        System.out.println(fi.addToMem(7));
        System.out.println(fi.addToMem(8));
        System.out.println(fi.addToMem(9));
        System.out.println(fi.addToMem(10));
    }
}
