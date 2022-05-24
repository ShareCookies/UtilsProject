package com.china.hcg.AlgorithmAndStructure.sort;

/**
 * @autor hecaigui
 * @date 2020-8-6
 * @description
 */
public class QuickSort {
    public static void intArrayQuickSort(int[] integers,int start,int end){
        // 定义个比较标准值
        int standardValue = integers[start];
        // 这两个变量是为了 划分出 下一轮的 数组的排序范围（迭代范围），避免把已经排好的部分又拿进来排
        int initStart = start;
        int iniEnd = end;
        while (end > start){

            // (通过与标准值比较，如果大于则不动)先后往前移
            while (end > start && integers[end] >= standardValue){
                end--;
            }
            // 标准值所在位(start位)与小于标准值的那个换位
            if (end > start && integers[end] <= standardValue){
                int  tmp = integers[end];
                integers[end] = integers[start];
                integers[start] = tmp;
            }

            // (通过与标准值比较，如果小于于则不动)前往后移移
            while (end > start && integers[start] <= standardValue){
                start++;
            }
            // 标准值所在位(end位)与大于标准值的那个换位
            if (end > start && integers[start] >= standardValue){
                int  tmp = integers[start];
                integers[start] = integers[end];
                integers[end] = tmp;
            }
        }
        // 现在标准值左边的都比右边的小，然后左右在用来迭代该方法，达到快速排序的效果（因为移动的少）。
            //附1：最后 end ， start 一样，他们所在位置为标准值位置。
                //        因为：
                //        标准值坐标是不断被移动的，移动end时 标准值坐标为start坐标 ，移动start时 标准值坐标为end坐标
                //        最后要么是end 与 start 一样
                //        标准值位置（start的位置）右边的都比标准值大，end一直移动到标准值位置（start的位置）
                //        要么start 与 end 一样
                //        标准值位置（end的位置）左边的都比标准值小，start一直移动到标准值位置（end的位置）
            //附2：移动过程
                //        2, 4,1,5
                //        while第一次
                //        14 2 5 s0 e2
                //        1 2 45 s1 e2
                //
                //        while第二次
                //        1 2 45 s1 e1

        // if (initStart < start-1) quickSort(integers,initStart,start-1);
        // 这一步与上面一步效果一样，只是多了把标准值的位置，划分给左边，让左边多了一次比较。但其实标准值位置可以不用比较的，因为它的左边都比它小（或等）右边都比它大
        if (initStart < start) intArrayQuickSort(integers,initStart,start);
        if (end+1 < iniEnd) intArrayQuickSort(integers,end+1,iniEnd);
    }

    public static void main(String[] args) {
        //int intArray[] = {1,4,2,5};
        int intArray[] = {2,4,1,5};
        //int intArray[] = {1,2,3,5};
        //int intArray[] = {1,2,3,5,3,1,4,2,7,8,0,4,6};
        intArrayQuickSort(intArray,0,intArray.length-1);
        for (int i : intArray){
            System.out.println(i);
        }
    }
}
