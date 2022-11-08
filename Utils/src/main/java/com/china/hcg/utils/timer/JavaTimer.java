package com.china.hcg.utils.timer;

import java.util.concurrent.*;

/**
 * @autor hecaigui
 * @date 2022-10-13
 * @description
 */
public class JavaTimer {
    //为什么不能在方法里初始化了？
    static ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(3,new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("post请求消息中心接口用的线程池");
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
}
