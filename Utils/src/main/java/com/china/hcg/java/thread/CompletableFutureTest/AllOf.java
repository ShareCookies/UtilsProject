package com.china.hcg.java.thread.CompletableFutureTest;

import java.util.concurrent.*;

/**
 * @autor hecaigui
 * @date 2022-12-7
 * @description
 */
public class AllOf {
    public static void main(String[] args) {
        supplyAsyncWithExecutorTest();
    }
    /**
     * @description 1. CompletableFuture里任务是并发执行 2.allOf测试
     */
    static void allOfTest(){
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                //1.休眠了，但future2任能运行，证明CompletableFuture里任务是并发执行
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future1完成！");
            return "future1返回的值！";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("异常测试");
//            System.out.println("future2完成！");
//            return "future2返回的值！";
        });
        //2.allOf()：当所有给定的 CompletableFuture 完成时，返回一个新的 CompletableFuture
        CompletableFuture<Void> combindFuture = CompletableFuture.allOf(future1, future2);
        try {
            combindFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e){}
        String s1 = "";
        String s2 = "";
//        try {
//            combindFuture.get();
//            s1 = future1.get();
//            s2 = future2.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        System.err.println("main打印"+s1+s2);
    }
    static ExecutorService es = new ThreadPoolExecutor(1, 5, 0L, TimeUnit.MICROSECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("自定的线程名");
            return  t;
        }
    });
    static ExecutorService  es2 = new ThreadPoolExecutor(0,100,5,TimeUnit.SECONDS,new LinkedBlockingDeque());
    /**
     * @description 1. CompletableFuture自定义线程池测试
     */
    static void supplyAsyncWithExecutorTest(){
        // 自定义线程池
        ExecutorService executorService = es2;
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("do something....");
            System.err.println(Thread.currentThread().getName());
            return "result";
        }, es2);

        //等待子任务执行完成
        try {
            System.out.println("结果->" + cf.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
