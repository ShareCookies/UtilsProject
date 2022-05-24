package com.china.hcg.java.thread;

import org.springframework.stereotype.Component;

/**
 * @description ScheduledTaskApplicationRunner
 */
@Component
public class FixedTimeNotice implements Runnable {


	@Override
	public void run(){
		long initialDelay = 12 * 60 * 60L; //定时任务延时启动
		long period = 24 * 60 * 60L; //定时任务时间间隔
		try {
			throw new Exception("错误");
		} catch (Exception e) {
			e.printStackTrace();
		}


		// 项目启动后，计算定时任务延时的启动时间
//		long currentTimeMillis = System.currentTimeMillis();
//		String taskTime = FORMAT_DAY.get().format(currentTimeMillis) + " " + time_clear_sector;
//		try {
//			long taskTimestamp = FORMAT.get().parse(taskTime).getTime();
//			initialDelay = (taskTimestamp - currentTimeMillis) > 0 ? (taskTimestamp - currentTimeMillis) / 1000 : 1;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// 定时任务启动（延时时间后周期执行）
//		scheduledExecutorService.scheduleAtFixedRate(taskService, initialDelay, period, TimeUnit.SECONDS);
	}

}
