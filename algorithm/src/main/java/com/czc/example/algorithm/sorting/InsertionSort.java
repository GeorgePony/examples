package com.czc.example.algorithm.sorting;

import java.util.Random;

/**
 * @author 金陵笑笑生
 * @description: 插入法排序
 * @date 2022/3/21下午10:31
 */
public class InsertionSort {
    void insertionSort(int[] arr,int l, int r){
        for(int i = l + 1 ; i <= r ; i ++){
            int idx = -1 ;
            for(int j = 0 ; j <= i - 1 ; j ++){
                if(arr[j] > arr[i]){
                    idx = j ;
                    break;
                }
            }
            if(idx != -1){
                int temp = arr[i];
                for(int k = i ;k > idx ; k --){
                    arr[k] = arr[k - 1];
                }
                arr[idx] = temp;
            }
        }
    }

    public static void main(String[] args) {

        int size = 1000;
        int[] arr = new int[size];
        Random r = new Random();
        for(int i = 0 ;i < size ; i ++){
            arr[i] = r.nextInt(size);
        }

        new InsertionSort().insertionSort(arr,0, arr.length - 1);

        for(int i = 0 ; i < arr.length ; i ++ ){
            System.out.print(String.format("%d ",arr[i]));
        }
    }
}
