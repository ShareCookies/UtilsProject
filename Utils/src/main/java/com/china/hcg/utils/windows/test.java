package com.china.hcg.utils.windows;

/**
 * @autor hecaigui
 * @date 2022-10-18
 * @description
 */
public class test {

    private final static String TITLE="弹窗";
    public static void main(String[] args) {
        InfoUtil test = new InfoUtil();
        test.show(TITLE, "这是一个弹窗测试！");
    }
}
