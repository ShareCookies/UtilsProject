package com.china.hcg.AlgorithmAndStructure.structure;

import java.util.Objects;

/**
 * @autor hecaigui
 * @date 2020-8-8
 * @description
 */
public class stack {
    // 用一个数组S[1..n]来实现一个最多可容纳n个元素的栈。
    Objects[] capacity = new Objects[10];
    // 属性top，指向最新插入的元素
    int top = 0;

    // 栈上的INSERT操作称为压人(PUSH)，
    void push(Objects o){
        capacity[top] = o;
        top++;
    }
    // 而无元素参数的DELETE操作称为弹出(POP)。
    void pop(){
        top --;
    }
    // 空栈,进行查询操作STACK-EMPTY。
    // 如果试图对一个空栈执行弹出操作，则称栈下溢(underflow).代码未避免改bug
    // 如果S. top超过了n，则称栈上溢(overflow).代码未避免改bug
    //
}
