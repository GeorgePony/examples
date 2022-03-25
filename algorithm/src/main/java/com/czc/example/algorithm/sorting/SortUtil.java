package com.czc.example.algorithm.sorting;

/**
 * @author 金陵笑笑生
 * @description: TODO
 * @date 2022/3/25上午9:58
 */
public class SortUtil {

    public static void swap(int[] arr,int j,int i){

        int temp = arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
    }


}
