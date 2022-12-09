package com.china.hcg.applications.chao_gu.utilscommon;

import com.china.hcg.java.thread.ThreadPoolUtilsDemo;

import java.util.concurrent.*;

/**
 * @autor hecaigui
 * @date 2022-12-9
 * @description
 */
public class StockThreadPoolUtil {
    //？核心线程是什么时候创建的
    public static ExecutorService executorService = new ThreadPoolExecutor(0, 100, 5, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"StockThreadPoolUtil");
        }
    });

}
