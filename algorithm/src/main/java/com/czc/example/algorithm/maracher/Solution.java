package com.czc.example.algorithm.maracher;

/**
 * @author 金陵笑笑生
 * @description: 曼彻斯特算法
 * @date 2022/8/17下午9:32
 */
public class Solution {


    public String getPalindrome(String s){
        StringBuffer sb = new StringBuffer();
        sb.append('$');
        for(int i = 0 ; i < s.length() ; i ++){
            sb.append('#');
            sb.append(s.charAt(i));
        }
        sb.append('#');
        sb.append('%');
        int idx = 0,mx = 0,maxLength = 0 ,id = 0;
        String s1 = sb.toString();
        int[] arr = new int[s1.length()];
        for(int i = 1 ; i < s1.length() - 1 ; i ++){
            arr[i] = mx>i?Math.min(arr[2*id - i],mx - i): 1;
            while (i+arr[i] < s1.length() && i - arr[i] >= 0 && s1.charAt(i+arr[i]) == s1.charAt(i - arr[i])){
                arr[i]++;
            }
            if(i + arr[i] > mx){
                mx = i + arr[i];
                id = i;
            }
            int len = arr[i] - 1;
            if(len > maxLength){
                maxLength = len;
                idx = i ;
            }
        }
        int start = (idx - maxLength) / 2;
        return s.substring(start,start + maxLength);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.getPalindrome("ayuiiuyds"));
        System.out.println(s.getPalindrome("yuyiodhkahfkdhfkds"));
    }


}
