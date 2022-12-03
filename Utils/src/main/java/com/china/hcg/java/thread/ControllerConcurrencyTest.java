package com.china.hcg.java.thread;

import com.alibaba.fastjson.JSONObject;
import com.china.hcg.http.HttpClientUtil;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
系统吞吐量（TPS）、用户并发量：
	http://www.ha97.com/5095.html
压测工具：
	JMeter
		jmeter 是由java编写的一个GUI测试工具，功能强大且结果多样


 * @autor hecaigui
 * @date 2020-10-15
 * @description
 * <P>
 *     多台机器一起执行，那么机器时间如何同步了...
 *     时间同步服务器NTP
 *          https://blog.csdn.net/sx8160194/article/details/107012949?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-4.add_param_isCf&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-4.add_param_isCf
 *          https://blog.csdn.net/zisefeizhu/article/details/81535299?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.add_param_isCf&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.add_param_isCf
 *     https://blog.csdn.net/hounanjsj/article/details/77896412?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-7.add_param_isCf&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-7.add_param_isCf
 * </P>
 */
public class ControllerConcurrencyTest {
	/**
	 * @description  * FixedThreadPool使用了有限的线程集来执行所提交的任务，
	 *  * 一次性预先执行代价高昂的线程分配，因而可以限制线程数量和节约创建线程时间。、
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ControllerConcurrencyTest.getTodoListConcurrency();
	}

	/**
	 * 控制层接口并发简易测试
	 * <p>
	 *     300左右并发耗时结果较为真实，越后面结果越大。
	 *     因为越后面接口返回时间越慢，此时线程因为io耗时原因会被系统移入阻塞队列，此时的耗时就为线程阻塞耗时+接口耗时。？？？
	 * </p>
	 * @author hecaiguis
	 * @date 2020-7-21
	 * @param
	 * @return
	 */
	public static void controllerConcurrency(){
		ExecutorService exec= Executors.newFixedThreadPool(200);
		//JSONArray concurrencyResult = new JSONArray();
		String[] addresss = {"http://localhost:8888"};
//        System.err.println("请输入并发测试ip");
//        //System.in表示标准化输出，也就是键盘输出
//        Scanner sc = new Scanner(System.in);
//        addresss[0] = sc.next();
		for (int i = 0; i < 1000; i++) {
			exec.execute(new Runnable(){
				@Override
				public void run(){
					//System.err.println(Thread.currentThread().getName()+"============");
					Date date=new Date();
					ArrayList toread =new ArrayList<>();
					toread.add("toread");
					toread.add("todo");
					ArrayList moduleList =new ArrayList<>();
					moduleList.add("DISPATCH");

					JSONObject postObj = new JSONObject();
					//postObj.put("tokenid","dXNlcm5hbWU9dGVzdCZkdD0yMDIwMDExNzA5NDA0NyZjb2RlPTYzZTQ4NTk1ZmIyMTY3NGY0ODZjMjUzMWFmNDBkNDcx");
					//postObj.put("tokenid", tokenidTest.randomGeneToken());
					postObj.put("tokenid", "dXNlcm5hbWU9c3dhZG1pbiZkdD0yMDIyMDUyNDE2MzIxNCZjb2RlPTU0MDU5YmQ4OTI0YjNlODQ2NmY5NDM4Y2EyMDEyMjA5");
					postObj.put("stateList",toread);
					postObj.put("moduleList",moduleList);
					//String[] addresss = {"http://localhost:6051","http://localhost:8888","http://192.168.210.171:6051","http://192.168.210.171:8888"};
					//String[] addresss = {"http://localhost:6051","http://localhost:8888"};
					//String[] addresss = {"http://203.48.27.52:6081"};
					//String[] addresss = {"http://localhost:6051"};
					//String[] addresss = {"http://www.baidu.com"};
					Random random = new Random();
					int addressI = random.nextInt(addresss.length);
					JSONObject jsonObject = HttpClientUtil.postOfJson2(addresss[addressI]+"/openApi/wflow/getTodoAndToRead",postObj);
					//JSONObject jsonObject = HttpClientUtil.post2(addresss[addressI],null);
					Date date2=new Date();
					System.err.println(Thread.currentThread().getName()+"============耗时"+(date2.getTime()-date.getTime())+"毫秒");
					if ((date2.getTime()-date.getTime())>3000){
						System.err.println(Thread.currentThread().getName()+"============高耗时IP"+(addresss[addressI]));
//                      System.err.println(Thread.currentThread().getName()+"============高耗时IP状态"+jsonObject.getString("status"));
//                      System.err.println(Thread.currentThread().getName()+"============高耗时IP响应"+jsonObject.getString("body"));
					}
					//concurrencyResult.add(Thread.currentThread().getName()+"============耗时"+(date2.getTime()-date.getTime())+"毫秒");
					//System.err.println(concurrencyResult.toJSONString());
				}
			});
		}
		exec.shutdown();
	}


	public static void getTodoListConcurrency(){
		ExecutorService exec= Executors.newFixedThreadPool(200);
		//JSONArray concurrencyResult = new JSONArray();
		String[] addresss = {"http://localhost:8888"};
//        System.err.println("请输入并发测试ip");
//        //System.in表示标准化输出，也就是键盘输出
//        Scanner sc = new Scanner(System.in);
//        addresss[0] = sc.next();
		for (int i = 0; i < 500; i++) {
			exec.execute(new Runnable(){
				@Override
				public void run(){
					//System.err.println(Thread.currentThread().getName()+"============");
					Date date=new Date();
					ArrayList toread =new ArrayList<>();
					toread.add("toread");
					toread.add("todo");
					ArrayList moduleList =new ArrayList<>();
					moduleList.add("DISPATCH");

					JSONObject postObj = new JSONObject();
					//postObj.put("tokenid","dXNlcm5hbWU9dGVzdCZkdD0yMDIwMDExNzA5NDA0NyZjb2RlPTYzZTQ4NTk1ZmIyMTY3NGY0ODZjMjUzMWFmNDBkNDcx");
					//postObj.put("tokenid", tokenidTest.randomGeneToken());
					postObj.put("tokenid", "");
					postObj.put("stateList",toread);
					postObj.put("moduleList",moduleList);
					//String[] addresss = {"http://localhost:6051","http://localhost:8888","http://192.168.210.171:6051","http://192.168.210.171:8888"};
					//String[] addresss = {"http://localhost:6051","http://localhost:8888"};
					//String[] addresss = {"http://203.48.27.52:6081"};
					//String[] addresss = {"http://localhost:6051"};
					//String[] addresss = {"http://www.baidu.com"};
					Random random = new Random();
					int addressI = random.nextInt(addresss.length);
					HttpHeaders headers = new HttpHeaders();
					headers.add("x-auth-token","537d57c2-2a57-4f99-b09c-af1e6bd39b3c");
					String jsonObject = HttpClientUtil.getWithHeader(addresss[addressI]+"/gyExtendFlowUtilController/getTodoList?status=1&todoType=1&offset=0&limit=15",headers);
					//JSONObject jsonObject = HttpClientUtil.post2(addresss[addressI],null);
					Date date2=new Date();
					System.err.println(Thread.currentThread().getName()+"============耗时"+(date2.getTime()-date.getTime())+"毫秒");
					if ((date2.getTime()-date.getTime())>3000){
						System.err.println(Thread.currentThread().getName()+"============高耗时IP"+(addresss[addressI]));
//                      System.err.println(Thread.currentThread().getName()+"============高耗时IP状态"+jsonObject.getString("status"));
//                      System.err.println(Thread.currentThread().getName()+"============高耗时IP响应"+jsonObject.getString("body"));
					}
					//concurrencyResult.add(Thread.currentThread().getName()+"============耗时"+(date2.getTime()-date.getTime())+"毫秒");
					//System.err.println(concurrencyResult.toJSONString());
				}
			});
		}
		exec.shutdown();
	}
}
