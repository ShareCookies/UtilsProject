package com.china.hcg.AlgorithmAndStructure.sort;


import java.util.Arrays;
/**
 * @description
归并排序过程分析：
    左分解0-1
    左分解0-0
    右分解1-1
    合并,left:0 mid:0 right:1（得到了最小的0单元 和 1单元，2单元合并）
    开始合并
    结束合并
    右分解2-2（单元1：0-1 。单元2：2）
    合并,left:0 mid:1 right:2 （2单元合并）
    开始合并
    结束合并
    [0, 1, 2]（中间数为1，中间数左边单元 已和 右边单元合并，所以归并排序完成）
待？多个的案例来个
    （既然不是提前算好的，那么为什么右分解 不会继续0-1了？）
    与左分解0-4对应
    寻找此范围内最小单元
 */
public class MergeSort {
    public static void main(String []args){
//        int []arr = {9,8,7,6,5,4,3,2,1};
        int []arr = {0,1,2};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
    public static void sort(int []arr){
        int []temp = new int[arr.length];//在排序前，先建好一个长度等于原数组长度的临时数组，避免递归中频繁开辟空间
        sort(arr,0,arr.length-1,temp);
    }
    private static void sort(int[] arr,int left,int right,int []temp){
        // 0 1 2
            // 0 1 2 2
                // 0 0 1 1 //右停
                // 合并0 0 1（合并0，1） //
            //合并0 1 2（合并0-1，2）
        if(left<right){
            int mid = (left+right)/2;
            System.err.println("左分解"+left+"-"+mid);
            sort(arr,left,mid,temp);//左边归并排序，使得左子序列有序
            System.err.println("右分解"+(mid+1)+"-"+right);
            sort(arr,mid+1,right,temp);//右边归并排序，使得右子序列有序
            System.err.println("合并,left:"+left+" mid:"+mid+" right:"+right);
            merge(arr,left,mid,right,temp);//将两个有序子数组合并操作
        }
    }
    private static void merge(int[] arr,int left,int mid,int right,int[] temp){
        System.err.println("开始合并");
        int i = left;//左序列指针
        int j = mid+1;//右序列指针
        int t = 0;//临时数组指针

        //归并操作，左右对比，小的插入到临时数组中。
        while (i<=mid && j<=right){ //确保要对比的元素为此次范围内
            // 找出小的填充到临时数组
            if(arr[i]<=arr[j]){
                temp[t++] = arr[i++];
            }else {
                temp[t++] = arr[j++];
            }
        }
        //将左边剩余元素填充进temp中
        while(i<=mid){
            temp[t++] = arr[i++];
        }
        //将右序列剩余元素填充进temp中
        while(j<=right){
            temp[t++] = arr[j++];
        }
        t = 0;
        //将temp中的元素全部拷贝到原数组中
        while(left <= right){
            arr[left++] = temp[t++];
        }
        System.err.println("结束合并");
    }
}
