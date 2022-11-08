package com.china.hcg.applications.chao_gu;

/**
 * @autor hecaigui
 * @date 2022-11-7
 * @description
 */
public class test {
    public static void main(String[] args) throws InterruptedException {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.err.println("用户线程存活着");
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //t.setDaemon(true);
        t.start();
        Thread.sleep(1000L);
        System.err.println("main用户线程走完");
    }

}
