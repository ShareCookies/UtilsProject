package com.china.hcg.java.thread;

import com.alibaba.fastjson.JSONObject;
import com.china.hcg.http.HttpClientUtil;

import java.util.concurrent.*;

/**
 * @autor hecaigui
 * @date 2020-9-16
 * @description 异步请求接口
 */
public  class  AsyncHttp {
	// post请求消息中心接口用的线程池
	static ExecutorService postMessageCenterApiThreadPool = new ThreadPoolExecutor(5, 100, 0L, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setName("post请求接口用的线程池");
			System.out.println("创建线程："+t);
			return  t;
		}
	});
	static class postMessageCenterApiRunnable implements Runnable{
		String postUrl;
		JSONObject postObj;
		postMessageCenterApiRunnable(String postUrl , JSONObject postObj){
			this.postUrl = postUrl;
			this.postObj = postObj;
		}
		@Override
		public void run(){
			HttpClientUtil.postOfJson(postUrl,postObj);
		}
	}
	static void post(String postUrl,JSONObject postObj){
		postMessageCenterApiThreadPool.submit(new postMessageCenterApiRunnable(postUrl,postObj));
	}
}
