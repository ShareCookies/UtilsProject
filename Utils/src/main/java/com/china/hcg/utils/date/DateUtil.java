/**
 * 
 */
package com.china.hcg.utils.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author hecaigui
 */
public class DateUtil {
	//一天的毫秒数
	public static final Long ONE_DAY_LONG = 1000*3600*24L;
	public static Long getOneDayLong() {
		return ONE_DAY_LONG;
	}

	static SimpleDateFormat ymdSdf = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * @description
	 * @author hecaigui
	 * @date 2021-5-21
	 * @param date 可以为多种类型 long Date Timestamp
	 * @return
	 */
	public static String format2StringYMD(Object date){
		return ymdSdf.format(date);
	}

	public static Date stringToDate(String dateStr, String dateFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		dateStr = dateStr.substring(0, dateFormat.length());
		return sdf.parse(dateStr);
	}

	public static String dateToString(Date date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}
	//当前日期往前推n天
	public static String curDateMinus(int day) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		// 获取当前日期
		LocalDate currentDate = LocalDate.now();

		// 往前推2天
		LocalDate previousDate = currentDate.minusDays(day);
		return  formatter.format(previousDate);
	}
	public static void main(String[] args) throws Exception{
		Date string = stringToDate("2023-11-07 23:00:00","yyyy-MM-dd hh:mm:ss");
		System.err.println(string);
//String file = "D:\\JHCERT\\billfile\\SHOP.105000957222494.20230804.20230804.20230810162410855.qE6O.zip";
//		String path = file.substring(file.lastIndexOf(".")+1,file.length());
//System.err.println(path);
//        Set<String> msgSendTypes= new HashSet<>();
//        msgSendTypes.add("1");
//        msgSendTypes.add("1");
//		Calendar rightCalendar = Calendar.getInstance();//获取当前地区的日期信息
//		System.out.println("年: " + rightCalendar.get(Calendar.YEAR));
//		Date date = new Date(1648137600000L);
//        System.err.println(date);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Timestamp timestamp = new Timestamp(1648137600000L);
//		System.err.println(sdf.format(timestamp));
//		Date handlePeriodDate=sdf.parse("2022-03-22");
//		System.err.println(handlePeriodDate);
//		System.err.println("2022-03-22".length());
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		Date activeDate=sdf.parse("2022-03-22 07:22:00");
//		Date curDate = new Date();
//		long remainTime = curDate.getTime() - activeDate.getTime();
////		System.err.println(DateUtil.format2StringYMD(new Timestamp(1619034071000L)));
//		long remainTimeHour = remainTime/(60*60*1000);
//        System.err.println(sdf.format(curDate));
//        System.err.println(remainTimeHour/24);
//        System.err.println(remainTimeHour%24);

    }

	/**
	 * 传入Data类型日期，返回字符串类型时间（ISO8601标准时间）
	 * @param date
	 * @return
	 * @test
	 *  Date date = new Date();
	 *  System.err.println(getISO8601Timestamp(date,"yyyy-MM-dd'T'HH:mm:ss'+08:00'"));
	 */
	public static String getISO8601Timestamp(Date date, String timeTypeFormat){
		TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
		DateFormat df = new SimpleDateFormat(timeTypeFormat);
		df.setTimeZone(tz);
		String nowAsISO = df.format(date);
		return nowAsISO;
	}
	//最后一天
	public static Date lastDay() throws ParseException{
		Calendar rightCalendar = Calendar.getInstance();
		String d = rightCalendar.get(Calendar.YEAR)+"12"+"31";
		return  DateUtil.stringToDate(d,"yyyy-MM-dd");
	}
}
