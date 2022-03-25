package com.czc.example.algorithm.sorting;

import java.util.Random;

/**
 * 快速排序的最初形态，每次选用数组第一个元素为基准，进行分区操作。
 * 这种排序方法的缺点是: 当数组近乎有序的情况下，快速排序的复杂度会降级为O(n * n)
 *
 * 改进方法:
 * 在partition方法中，原先是选用第一个元素为基准，改用随机一个元素作为基准。
 * @author 金陵笑笑生
 * @description: 快速法排序
 * @date 2022/3/25上午9:31
 */
public class QuickSort1 {

    /**
     * 对于arr[l...r]部分进行partition操作
     * @param arr
     * @param l
     * @param r
     * @return 返回p,使得arr[l...p-1] < arr[p] ;arr[p+1,r] > arr[p]
     */
    int partition(int[] arr,int l, int r){
        SortUtil.swap(arr ,l , l +new Random().nextInt(r - l) );
       int v = arr[l];
       int j = l;
       for(int i = l + 1; i <= r ; i ++){
           if(arr[i] < v){
               SortUtil.swap(arr,j+1,i);
               j = j + 1;
           }
       }
       SortUtil.swap(arr,l,j);
       return j;
    }


    void quickSort(int[] arr,int l,int r){
        /**
         * 高级的排序算法在最小处都可以用插入法排序优化
         */
        if(r - l <= 15){
            new InsertionSort().insertionSort(arr ,l,r);
            return;
        }

        int p = partition(arr,l,r);
        quickSort(arr,l ,p-1);
        quickSort(arr,p+1 , r);
    }

    public static void main(String[] args) {
        int size = 1000;
        int[] arr = new int[size];
        Random r = new Random();
        for(int i = 0 ;i < size ; i ++){
            arr[i] = r.nextInt(size);
        }
        new QuickSort1().quickSort(arr ,0, arr.length - 1);
        for (int n : arr) {
            System.out.println(n);
        }
    }
}
