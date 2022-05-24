package com.china.hcg.java.thread;

import java.util.concurrent.*;

/**
 * @autor hecaigui
 * @date 2020-7-8
 * @description
 */
public class ThreadPoolUtilsDemo {



    /**
     * 静态方法测试： 静态方法可以被多线程同时调用，然后同时运行
     * @author hecaigui
     * @date 2020-7-21
     * @param args
     * @return
     */
    public static void staticMethodTest(String[] args) {
        ExecutorService exec= Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            exec.execute(new Runnable(){
                @Override
                public void run(){
                    ThreadPoolUtilsDemo.staticMethodCanCalledByThreadHowMuchOnSameTime();
                }
            });
        }
        exec.shutdown();
    }
    // https://www.cnblogs.com/-ROCKS/p/6108490.html 静态方法并发问题
    // 静态方法可以被多线程同时调用，然后同时运行
    // 变量共享问题了？
    // 内存回收问题了？
    public static void  staticMethodCanCalledByThreadHowMuchOnSameTime(){
        System.err.println(Thread.currentThread().getName());
        while(true){

        }
        //System.err.println(Thread.currentThread().getName()+"over");
    }


    /**
     * @description 自定义线程池
     * @author hecaigui
     * @date 2020-8-27
     * @param
     * @return
     */
    public void selfDefThreadPoolExecutor(){
//        通过ThreadPoolExecutor类可以快速创建自定义的线程池。
//        构造方法参数介绍：
//        corePoolSize：线程池中所保存的核心线程数，包括空闲线程。
//        maximumPoolSize：池中允许的最大线程数。
//        keepAliveTime：线程池中的空闲线程所能持续的最长时间。
//        unit：持续时间的单位。
//        workQueue：任务执行前保存任务的队列，仅保存由execute方法提交的Runnable任务。
        //注:
            //要使用带有ThreadFactory参数的ThreadPoolExecutor构造方法哦，这样你就可以方便的设置线程名字啦。
            //创建线程或线程池时请指定有意义的线程名称，方便出错时回溯。创建线程池的时候请使用带ThreadFactory的构造函数，并且提供自定义ThreadFactory实现或者使用第三方实现。
        ExecutorService es = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MICROSECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("线程名自定义");
                t.setDaemon(true);
                System.out.println("创建线程"+t);
                return  t;
            }
        });
        MyTask1 task = new MyTask1();
        es.submit(task);
    }
    public static class MyTask1 implements Runnable{
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis()+"当前运行的Thrad为:"+Thread.currentThread().getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
