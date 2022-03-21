package com.czc.example.algorithm.sorting;

/**
 * @author 金陵笑笑生
 * @description: TODO
 * @date 2022/3/21下午10:31
 */
public class InsertionSort {
    void insertionSort(int[] arr){
        for(int i = 1 ; i < arr.length ; i ++){
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
        for(int i = 0 ; i < arr.length ; i ++ ){
            System.out.print(String.format("%d ",arr[i]));
        }
    }

    public static void main(String[] args) {
        int[] arr = {9,2,4,6,12,3,7,2,56,7};
        new InsertionSort().insertionSort(arr);

    }
}
