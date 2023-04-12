package com.china.hcg.utils.Limiter;

import java.time.LocalDateTime;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

/** * 漏桶算法 */
public class LeakyBucketLimiterUtil {
    /** * 漏桶流出速率(多少纳秒执行一次) */
    private long outflowRateNanos;
    /** * 漏桶容器 */
    private volatile BlockingQueue<Drip> queue;
    /** * 滴水线程 */
    static private Thread outflowThread = new Thread("漏水线程");
    /** * 水滴 */
    private static class Drip {
        /** * 业务主键 */
        private String busId;
        /** * 水滴对应的调用者线程 */
        private Thread thread;
        public Drip(String busId, Thread thread) { this.thread = thread; }
        public String getBusId() { return this.busId; }
        public Thread getThread() { return this.thread; }
    }

    /**
     * @param second 秒
     * @param time 调用次数
     * */
    //todo:所以实例化一次就会有个线程创建，岂不是说会导致线程耗尽
    //todo:所以该对象只能实例化为static
    public LeakyBucketLimiterUtil(int second, int time) {
        if (second <= 0 || time <= 0) { throw new IllegalArgumentException("second and time must by positive"); }
        outflowRateNanos = TimeUnit.SECONDS.toNanos(second) / time;
        queue = new LinkedBlockingQueue<>(time);
        outflowThread = new Thread(() -> {
            while (true) {
                Drip drip = null;
                try {
                    // 阻塞，直到从桶里拿到水滴
                    drip = queue.take();
                } catch (Exception e) { e.printStackTrace(); }
                if (drip != null && drip.getThread() != null) {
                    // 唤醒阻塞的水滴里面的线程
                    LockSupport.unpark(drip.getThread());
                }
                // 休息一段时间，开始下一次滴水
                LockSupport.parkNanos(this, outflowRateNanos);
            }
        }, "漏水线程");
        outflowThread.start();
    }
    /** * 业务请求 * * @return */
    public boolean acquire(String busId) {
        Thread thread = Thread.currentThread();
        Drip drip = new Drip(busId, thread);
        if (this.queue.offer(drip)) { LockSupport.park(); return true; }
        else { return false; }
    }


    public static void main(String[] args) throws Exception {
        // 1秒限制执行1次
        LeakyBucketLimiterUtil leakyBucketLimiter = new LeakyBucketLimiterUtil(60, 2);
        for (int i = 0; i < 11; i++) {
            new Thread(new Runnable() {
                @Override public void run() {
                    String busId = "[业务ID：" + LocalDateTime.now().toString() + "]";
                    if (leakyBucketLimiter.acquire(busId)) {
                        System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + "：调用外部接口...成功：" + busId);
                    }
                    else {
                        System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + "：调用外部接口...失败：" + busId);
                    }
                }
            }, "测试线程-" + i).start(); TimeUnit.MILLISECONDS.sleep(6000);
        }
    }
}