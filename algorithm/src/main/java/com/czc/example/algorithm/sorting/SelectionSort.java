package com.czc.example.algorithm.sorting;

/**
 * @author 金陵笑笑生
 * @description: 选择法排序
 * @date 2022/3/21下午9:47
 */
public class SelectionSort {

    void selectionSort(int[] arr){
        int n = arr.length;
        for (int i = 0 ; i < n ; i ++){
            // 寻找[i,n)区间内的最小值
            int minIndex = i;
            for(int j = i + 1 ; j  < n ; j ++){
                if(arr[j] < arr[minIndex]){
                    minIndex = j;
                }
            }
            SortUtil.swap(arr,minIndex,i);
        }
        for(int i = 0 ; i < n ; i ++ ){
            System.out.print(String.format("%d ",arr[i]));
        }
    }

    public static void main(String[] args) {
        int[] arr = {9,2,4,6,12,3,7,2,56,7};
        new SelectionSort().selectionSort(arr);
    }


}
