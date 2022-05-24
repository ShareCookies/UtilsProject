package com.china.hcg.test;

import java.util.concurrent.SynchronousQueue;

/**
 * @autor hecaigui
 * @date 2021-3-3
 * @description

Exception in thread "main" java.lang.NullPointerException
    at com.china.hcg.test.errorPositionTest.mathc(errorPositionTest.java:24) //1. 最后一个抛出错误的地方
    at com.china.hcg.test.errorPositionTest.main(errorPositionTest.java:19) //2. 行数就是指源码行数，不是反编译后的。（空行这些也不会移除，就是源码抛出错误的地方）
 */
public class errorPositionTest {

    public static void main(String[] args) throws Exception{


        mathc();
    }
    static void mathc(){
        Integer i = null;

        System.err.println(i.doubleValue());
    }
}
