package com.china.hcg.http;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.concurrent.*;

/**
 * @autor hecaigui
 * @date 2021-3-15
 * @description
 */
public class AsyncHttpClientUtil {
	// 请求线程池
	static ExecutorService postForJsonThreadPool = new ThreadPoolExecutor(2, 100, 0L, TimeUnit.MICROSECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setName("post异步请求线程池");
			System.out.println("创建线程："+t);
			return  t;
		}
	});
	static class postForJsonRunnable implements Runnable{
		String postUrl;
		JSONObject postObj;
		postForJsonRunnable(String postUrl , JSONObject postObj){
			this.postUrl = postUrl;
			this.postObj = postObj;
		}
		@Override
		public void run(){
			HttpClientUtil.postOfJson(postUrl,postObj);
		}
	}
	/**
	 * @description 异步发起一个json格式的post请求
	 */
	public static void asyncPostForJson (String postUrl , JSONObject postObj){
		postForJsonThreadPool.execute(new postForJsonRunnable(postUrl,postObj));
	}



//	public static ExecutorService executorService = new ThreadPoolExecutor(1, 1, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
//		@Override
//		public Thread newThread(Runnable r) {
//			return new Thread(r,"Therad-noticeUrlSend");
//		}
//	});
//	/**
//	 * @description 支付回调url调用
//	 * @author hecaigui
//	 * @date 2023/8/29
//	 * @param  * @param list
//	 * @return
//	 */
//	static void noticeUrlSend(String url,OrderResult orderResult) {
//		TerminalUtils.executorService.submit(() -> {
//
//		});
//	}
}
