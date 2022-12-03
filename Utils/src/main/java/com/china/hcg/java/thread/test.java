package com.china.hcg.java.thread;

import com.alibaba.fastjson.JSONObject;
import com.china.hcg.http.utils.HttpUtil;
import org.apache.commons.lang3.ThreadUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.*;


/**
 * @autor hecaigui
 * @date 2021-5-10
 * @description
 * 		// 动态生成一个定时器 （定时器时间map<时间、定时器id>、用户id（定时器中来个list包含用户信息））
 * 		// 会员可以定时，非会员固定时间。
 */
public class test {
//	public static void main(String[] args) {
////		// 创建任务队列
////		ScheduledExecutorService scheduledExecutorService =
////				Executors.newScheduledThreadPool(10); // 10 为线程数量
////		// 执行任务
////		scheduledExecutorService.scheduleAtFixedRate(() -> {
////			System.out.println("Run Schedule：" + new Date());
////		}, 1, 3, TimeUnit.SECONDS); // 1s 后开始执行，每 3s 执行一次
//
//	}
// post请求钉钉中心接口用的线程池
static  ExecutorService postDingCenterApiThreadPool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<>(), new ThreadFactory() {
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setName("post请求钉钉中心接口用的线程池");
		System.out.println("创建线程："+t);
		return  t;
	}
});
	static class postDingCenterApiRunnable implements Runnable{
		String postUrl;
		JSONObject postObj;
		postDingCenterApiRunnable(String postUrl , JSONObject postObj){
			this.postUrl = postUrl;
			this.postObj = postObj;
		}
		@Override
		public void run(){
			try {
				System.err.println(Thread.currentThread().getName()+postUrl);
				Thread.sleep(3000);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) throws Exception{
		List arr = new ArrayList();
		arr.add(1);
		arr.add(1);
		List a2 = arr.subList(0,1);
//		postDingCenterApiThreadPool.submit(new postDingCenterApiRunnable("1", null));
//		postDingCenterApiThreadPool.submit(new postDingCenterApiRunnable("2", null));
//		postDingCenterApiThreadPool.submit(new postDingCenterApiRunnable("3", null));
//		postDingCenterApiThreadPool.submit(new postDingCenterApiRunnable(null, null));

		System.out.println("年: ");
	}
	/**
	 * @description 默认下午5点定时(或6点，应用启动时间为5点则6点)
	 * @author hecaigui
	 * @date 2021-5-10
	 * @param
	 * @return
	 */
	static void fixedTimeNotice() throws Exception{
		int noticeTime = 17;
		Calendar rightCalendar = Calendar.getInstance();//获取当前地区的日期信息
		System.out.println("年: " + rightCalendar.get(Calendar.HOUR_OF_DAY));
		System.out.println("年: " + rightCalendar.get(Calendar.MINUTE));

		int curHour = rightCalendar.get(Calendar.HOUR_OF_DAY);
		int curMINUTE = rightCalendar.get(Calendar.MINUTE);

		int initDelayMinutes = 0;


		ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
		executorService.scheduleAtFixedRate(new FixedTimeNotice(), initDelayMinutes, 24 * 60, TimeUnit.MINUTES);
		executorService.scheduleAtFixedRate(()->{
			System.out.println("coming");

//			try {
//
////				// 注意此处休眠时间为5s
////				Thread.sleep(5000);
////				System.out.println("sleep end");
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			// 延迟0s执行，周期为3s
		}, initDelayMinutes, 24 * 60, TimeUnit.MINUTES);
	}

}
