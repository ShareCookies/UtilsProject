/**
 * 
 */
package com.china.hcg.utils.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author hecaigui
 */
public class DateUtilTest {
	static SimpleDateFormat ymdSdf = new SimpleDateFormat("yyyy-MM-dd");
	public static String format2StringYMD(Date date){
		return ymdSdf.format(date);
	}
	public static String format2StringYMD(Long date){
		return ymdSdf.format(date);
	}


	public static void main(String[] args) {
		
		Date date=new Date();//java.util.Date对象实例化的时候就保存了时间，时间是实例化时的时间戳。
		//System.currentTimeMillis();
		
		/**
		 * 通过java.util.Date获取时间戳，时间戳是the number of milliseconds since 1970-07-01, 00:00:00 GMT
		 */
		date.getTime();//获取时间戳
		System.out.println(date.getTime());

		/**
		 * util.Date转换成数据库能保存的时间：
		 * 这里是java.util.Date转成 java.sql.Date。转成其他sql时间下同理。
		 * java.sql包下时间介绍：
		 * 	Date：表示日期，只有年月日，没有时分秒。会丢失时间；
		 * 	Time：表示时间，只有时分秒，没有年月日。会丢失日期；
		 * 	Timestamp：表示时间戳，有年月日时分秒，以及毫秒。
		 * 	注:util.Date转sql.Date后，再从sql.Date获取时间戳，该时间戳任然保留了时分秒。
		 */
		java.sql.Date sqlDate=new java.sql.Date(date.getTime());
		System.out.println(sqlDate);
		
		/**
		 * 日期往后推迟n天
		 */
		Date date1 = new Date();//取时间 
	    Calendar calendar  =   Calendar.getInstance();
	    calendar.setTime(date1); //需要将date数据转移到Calender对象中操作
	    calendar.add(calendar.DATE,2);//把日期往后增加n天.正数往后推,负数往前移动 
	    date1=calendar.getTime();   //这个时间就是日期往后推一天的结果 
	    
		/**
		 * util.Date格式化成输出:
		 */
		SimpleDateFormat simpleDate=new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
		System.out.println(simpleDate.format(date));
		/**
		 * 字符串转成java.util.Date:
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//注：HH:mm:ss是24进制，hh:mm:ss是12进制
    	try {
			Date begDate=sdf.parse("2018-06-20");
			System.out.println(begDate);
			//System.out.println(sdf.format(begDate));
		} catch (Exception e) {
			System.out.println("日期出错");
			e.printStackTrace();
		}
    	/**
    	 * java获取年月日：
    	 * 	1.获取年月日的时间戳：
    	 * 		util.date用sdf格式化成年月日字符串，再把字符串转成util.Date:
    	 * 	2.单独获取年月日，在拼接
    	 *  	
    	 */
    	Calendar rightCalendar = Calendar.getInstance();//获取当前地区的日期信息
        System.out.println("年: " + rightCalendar.get(Calendar.YEAR));  
		/**
			1. 比真实月份少一个月
			2. 8月返回8 ，10月返回 10
		*/
        System.out.println("月: " + (rightCalendar.get(Calendar.MONTH) + 1));  
        System.out.println("日: " + rightCalendar.get(Calendar.DAY_OF_MONTH));  
        rightCalendar.get(Calendar.HOUR_OF_DAY);//时
        rightCalendar.get(Calendar.MINUTE);//分
        rightCalendar.get(Calendar.SECOND);//秒
        System.out.println("星期: " + (rightCalendar.get(Calendar.DAY_OF_WEEK)-1));//西方星期从星期日开始计算 
        //calendar.add(Calendar.MONTH, 1);//下月今天
        rightCalendar.set(calendar.get(Calendar.YEAR), rightCalendar.get(Calendar.MONTH)+1, 1);//把时间为下个月第一天
        rightCalendar.add(Calendar.DAY_OF_MONTH, -1);//时间倒退一天
    	System.out.println("该月最后一天："+rightCalendar.get(Calendar.DAY_OF_MONTH));
    	/**
    	 * 时间的一些应用
    	 * https://blog.csdn.net/cynhafa/article/details/8053166
    	 */
		
	}
	

}
