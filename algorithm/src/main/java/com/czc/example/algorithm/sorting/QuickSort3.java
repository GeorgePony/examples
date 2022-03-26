package com.czc.example.algorithm.sorting;

import java.util.Random;

/**
 * @author 金陵笑笑生
 * @description: TODO
 * @date 2022/3/26上午8:19
 */
public class QuickSort3 {

    private int partition(int[] arr, int l, int r) {
        SortUtil.swap(arr ,l , l +new Random().nextInt(r - l) );
        int v = arr[l];
        // arr[l+1 ... lt] < v
        int lt = l;
        // arr[gt ... r] > v
        int gt = r + 1;
        int i = l + 1;
        while (i < gt){
            if(arr[i] < v){
                SortUtil.swap(arr,i,lt + 1);
                lt ++;
                i ++;
            }else if(arr[i] > v){
                SortUtil.swap(arr ,i , gt - 1);
                gt --;
            }else{
                i ++;
            }
        }
        SortUtil.swap(arr,l,lt);

        return i;
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
        new QuickSort3().quickSort(arr ,0, arr.length - 1);
        for (int n : arr) {
            System.out.println(n);
        }
    }

}
