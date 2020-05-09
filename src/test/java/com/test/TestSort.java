package com.test;
import	java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TestSort {
    public static void main(String[] args) {
        int []a=suiji();
        List<Integer> list = new ArrayList<> ();
        System.out.println(Arrays.toString(a));
        System.out.println("----------------");

        sort(a);//对a数组排序

        System.out.println("-------------");
        System.out.println(Arrays.toString(a));
    }


    private static int[] suiji() {
        int n = 5 + new Random().nextInt(6);
        int []a = new int [n];
        for (int i = 0; i < a.length; i++) {
            a[i]= new Random().nextInt(100);

        }
        return a;
    }

    private static void sort(int[] a) {
        //i循环从头遍历
        for (int i = 0; i < a.length; i++) {
            //j循环从a.length-1到i位置
            boolean flag = false;
            for (int j =a.length-1; j > i; j--) {
                //j和j-1位置的值比较大小
                //如果j位置小和j-1位置交换
                if(a[j]<a[j-1]){
                    int t= a[j];
                    a[j]=a[j-1];
                    a[j-1]=t;
                    flag = true;
                }
            }
            if(!flag){//flag是false，j递减过程没有交换数据
                break;
            }
            System.out.println(Arrays.toString(a));
        }
    }
}
