package com.czc.example.algorithm.dynamicProgramming;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 金陵笑笑生
 * @description: 问题描述:给你k种面值的硬币，面值分别为c1,c2,...,ck,每种硬币数量无限，再给一个总金额，问你最少需要多少枚硬币才能凑出此金额。
 * @date 2022/3/22下午9:11
 */
public class CoinProblem {

    private Map<Integer,Integer> mem = new HashMap<>();
    private int[] coins;
    public CoinProblem(int[] coins){
        this.coins = coins;
    }
    private int addToMem(int n){
        if(mem.containsKey(n)){
            return mem.get(n);
        }
        if(n == 0){
            return 0;
        }
        if(n < 0){
            return -1;
        }
        int amount = Integer.MAX_VALUE;
        for (int coin : this.coins) {
            int subProblem = addToMem(n - coin);
            if(subProblem == -1){
                continue;
            }
            amount = Math.min(amount,1 + subProblem);
        }
        if(amount != Integer.MAX_VALUE){
            mem.put(n,amount);
        }
        return amount;
    }

    public static void main(String[] args) {
        int[] coins = {4,5,1};
        CoinProblem pro = new CoinProblem(coins);
        System.out.println(pro.addToMem(17));
        System.out.println(pro.addToMem(18));
        System.out.println(pro.addToMem(19));
        System.out.println(pro.addToMem(20));
        System.out.println(pro.addToMem(21));

    }
}
