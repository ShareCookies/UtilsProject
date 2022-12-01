package com.china.hcg.utils.timer;

import org.apache.tomcat.jni.Error;

import java.util.concurrent.*;

/**
 * @autor hecaigui
 * @date 2022-10-13
 * @description
 */
public class JavaTimer {
    //为什么不能在方法里初始化了？
    static ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("JavaTimer.class定时器用的线程池");
            return t;
        }
    },new ThreadPoolExecutor.DiscardPolicy());


    /**
     * @description
     * @demo
     *         JavaTimer javaTimer = new JavaTimer();
     *         javaTimer.loopTask(new Runnable() {
     *             @Override
     *             public void run() {
     *                 System.err.println(11);
     *             }
     *         },5L);
     * @author hecaigui
     * @date 2022-10-20
     * @param task
     * @param delaySeconds
     * @return
     */
    public static void loopTask(Runnable task,long delaySeconds){
        executorService.scheduleWithFixedDelay(task,0L,delaySeconds,TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        executorService.scheduleWithFixedDelay(new Runnable() {

            @Override
            public void run() {
                System.err.println("线程1");
                System.err.println(Thread.currentThread().getId());
                throw new RuntimeException("");
//  想要任务不结束要进行捕获
//                try {
//                    System.err.println(1);
//                    throw new RuntimeException("");
//                }catch (Exception e){
//
//                }

            }
        },0L,1L,TimeUnit.SECONDS);

        executorService.scheduleWithFixedDelay(new Runnable() {

            @Override
            public void run() {
                System.err.println("线程2");
                System.err.println(Thread.currentThread().getId());
            }
        },0L,1L,TimeUnit.SECONDS);
    }
}
