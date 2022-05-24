package com.china.hcg.io.file;

/**
 * @autor hecaigui
 * @date 2021-11-16
 * @description
 */
public class TEST {
    static void tempFileTest2()throws Exception{
        String defaultBaseDir = System.getProperty("java.io.tmpdir");
        System.err.println(defaultBaseDir);
    }

    public static void main(String[] args) throws Exception {

        tempFileTest2();
    }
}
