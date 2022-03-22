package com.czc.example.algorithm.sorting;

/**
 * @author 金陵笑笑生
 * @description: 归并法排序
 * @date 2022/3/22下午10:31
 */
public class MergeSort {

    void merge(int[] arr,int left,int mid,int right){
        int[] temp = new int[right - left + 1];
        for(int i = left ; i <= right ; i ++){
            temp[i - left ] = arr[i];
        }
        int i = left;
        int j = mid + 1;
        for(int k = left; k <= right ; k ++){
            if(i > mid){
                arr[k] = temp[j - left];
                j ++;
            }else if(j > right){
                arr[k] = temp[i - left ];
                i ++;
            }else if(temp[i - left] < temp[ j - left]){
                arr[k] = temp[ i - left ];
                i ++;
            }else{
                arr[k] = temp[j - left];
                j ++;
            }
        }
    }

    void mergeSort(int[] arr,int left,int right){
        if(left >= right){
            return;
        }
        int mid = (left + right) / 2;
        mergeSort(arr,left,mid);
        mergeSort(arr,mid + 1,right);
        merge(arr ,left,mid,right);
    }

    void mergeSort(int[] arr){
        mergeSort(arr ,0, arr.length - 1);
    }


    public static void main(String[] args) {
       int[] arr = {23,5,2,5,2,6,2,6,2,667,8,32,54,7,324,6,433,44,61,36};
       new MergeSort().mergeSort(arr);
        for (int n : arr) {
            System.out.println(n);
        }
    }
}
