package com.zjy.algorithm;

import java.util.List;

/**
 * @author zjy
 * @des
 * @date 2019/6/4
 */
public class QuickSort2 {


    public static void main(String[] args) {

        int[] arr = {10,7,2,4,7,62,3,4,2,1,8,9,19};
        for (int t:arr) {
            System.out.print(t+" ");
        }
        System.out.println();
        QS(arr,0,arr.length-1);

        System.out.println("last");
        for (int t:arr) {
            System.out.print(t+" ");
        }
        System.out.println();
    }

    // int[] arr = {10,7,2,4,7,62,3,4,2,1,8,9,19};
    // int[] arr = {9,7,2,4,7,62,3,4,2,1,8,9,19};
    // int[] arr = {62,7,2,4,7,9,3,4,2,1,8,9,19};
    public static void  QS(int[] arry,int low,int high){

        if(low>=high){
            return;
        }
        // 每一次调用，比中位数小的移动到左边，比中位数大的移动到右边
        int i,j,temp;
        i=low;
        j=high;
        temp=arry[i];
        while (i<j){
            // 比基数小的放基数左边，比基数大的放基数右边
            // 从右边往左边找，找到比基准小的，交换
            while (temp<=arry[j] && i<j){
                j--;
            }
            if(i<j){
                //说明a[j]>a[i]的 交换位置
                arry[i]=arry[j];
            }else{
                //找完没，没有找到比基准值小的，不用交换位置
            }
            for (int t:arry) {
                System.out.print(t+" ");
            }
            System.out.print("  i="+i+" j="+j);
            System.out.println();

            //从左边往右边找，找到比基数大的,交换
            while (temp>=arry[i] && i<j){
                i++;
            }
            if(i<j){
                arry[j]=arry[i];
            }
            for (int t:arry) {
                System.out.print(t+" ");
            }
            System.out.print("  i="+i+" j="+j);
            System.out.println();
        }
        arry[i]=temp;
        for (int t:arry) {
            System.out.print(t+" ");
        }
        System.out.print("  i="+i+" j="+j);
        System.out.println();

        QS(arry,low,i-1);// 按上述方法找左边的
        QS(arry,i+1,high);// 按上述方法找右边的边的

    }
}
