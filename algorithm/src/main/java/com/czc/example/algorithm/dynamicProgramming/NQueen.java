package com.czc.example.algorithm.dynamicProgramming;

import com.sun.security.jgss.GSSUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 金陵笑笑生
 * @description: N 皇后问题
 * @date 2022/3/31下午9:18
 */
public class NQueen {

    private int n;

    private List<int[][]> solutions = new ArrayList<>();
    public NQueen(int n){
        this.n = n;
    }



    public void printArr(boolean[][] arr){
        for(int i = 0 ; i < n ; i ++){
            for(int j = 0 ; j < n ; j ++){
                System.out.printf("%d ",arr[i][j]?1:0);
            }
            System.out.println();
        }
    }


    public boolean[][] makeArr(){
        boolean[][] arr = new boolean[n][n];
        for(int i = 0 ; i < n ; i ++){
            for(int j = 0 ; j < n ; j ++){
                arr[i][j] = false;
            }
        }
        return arr;
    }

    public boolean[][] copyArr(boolean[][] oldArr) {
        boolean[][] arr = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = oldArr[i][j];
            }
        }
        return arr;
    }

    public int[][] copySchema(int[][] oldArr){
        if(oldArr.length  == 0){
           return new int[0][2];
        }
        int[][] arr = new int[oldArr.length][2];
        for(int i = 0 ; i < oldArr.length ; i ++){
            for(int j = 0 ; j< 2 ; j ++){
                arr[i][j] = oldArr[i][j];
            }
        }
        return arr;
    }

    public boolean check(int x,int y ,int oldX ,int oldY){
        if(x < oldX){
            return true;
        }
        if(x > oldX){
            return false;
        }
        return y > oldY;
    }

    public int[][] addSchema(int[][] oldArr ,int x , int y){
        int[][] arr = new int[oldArr.length + 1][2];
        for(int i = 0 ; i < oldArr.length ; i ++){
            int oldX = oldArr[i][0];
            int oldY = oldArr[i][1];
            arr[i ][0] = oldX;
            arr[i ][1] = oldY;


        }
            arr[oldArr.length][0] = x;
            arr[oldArr.length][1] = y;
        return arr;
    }


    public boolean[][] fillArr(boolean[][] oldArr ,int i ,int j){
        boolean[][] arr = copyArr(oldArr);
        for(int k = 0 ; k < n ; k ++){
            arr[i][k] = true;
        }
        for(int k = 0 ; k <n ; k ++){
            arr[k][j] = true;
        }
        int s = i + j;
        for(int k = 0 ; k <= s && k < n ; k ++){
            int k1 = s - k;
            if(k1 < n){
                arr[k][k1] = true;
            }
        }
        int sub = i - j;
        int start = Math.max(sub, 0);
        for(int k = start ; k < n  ; k ++){
           int k1 = k - sub;
           if(k1 >= 0 && k1 < n){
               arr[k][k1] = true;
           }
        }
        return arr;
    }


    /**
     *
     * @param arr 可能放置了[0,n-1]个皇后
     * @param num
     * @return
     */
    public int putSchema(boolean[][] arr, int num, int[][] schema){
        if(num == 0){
            boolean found =false;
            for (int[][] solution : this.solutions) {
                int stepsSame = 0;
                for (int[] step : solution) {
                    for (int[] s : schema) {
                        if(step[0] == s[0] && step [1] == s[1]){
                            stepsSame ++;
                            break;
                        }
                    }
                }
                if(stepsSame == solution.length){
                    found = true;
                    break;
                }
            }
            if(!found){
                this.solutions.add(schema);
                return 1;
            }else{
                return 0;
            }
        }
        int count = 0;
        for(int i = 0 ; i < n ; i ++){
            for(int j = 0 ; j < n ; j ++){
                if(!arr[i][j]){
                    int[][] newSchema = copySchema(schema);
                    newSchema = addSchema(newSchema,i,j);
                    count = count + putSchema(fillArr(arr,i ,j) , num - 1,newSchema);
                }
            }
        }
        return count;

    }


    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        int n = 8;
        NQueen q = new NQueen(n);
        boolean[][] arr = q.makeArr();

        System.out.println(q.putSchema(arr ,n,new int[0][0]));
        System.out.println("cost : " + String.valueOf(System.currentTimeMillis() - time));

    }
}
