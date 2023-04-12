package com.china.hcg.utils.Limiter;

import com.google.common.util.concurrent.RateLimiter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @autor hecaigui
 * @date 2023-1-28
 * @description
 */
public class test {
    public static void main(String[] args) throws InterruptedException {
        // qps 2
        RateLimiter rateLimiter = RateLimiter.create(1,1, TimeUnit.MINUTES);
        for (int i = 0; i < 10; i++) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
            System.out.println(time + ":" + rateLimiter.tryAcquire());
            Thread.sleep(1000);
        }
    }
}
