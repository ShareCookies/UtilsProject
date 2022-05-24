package com.china.hcg.io.file.iostudy;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 作用：读取一文件字符串
 * 介绍：
 * 	使用string或file对象生产FileReader，
 * 	将产生的引用传给BufferedReader构造器，对文件进行缓冲以便提高速度。
 * 	BufferedReader的readLine()是进行读取的接口，当readLine返回null时，就到达文件的末尾。
 * BufferedReader缓冲字符输入流：
 * 	该类是一个包装类，它可以包装字符流，将字符流放入缓存里，
 * 	先把字符读到缓存里，到缓存满了或者你flush的时候，再读入内存，
 * 	就是为了提供读的效率而设计的。
 * @author Administrator
 * @name 缓冲输入文件
 */
public class ReaderTest {

	public static String read(String fileName) throws IOException{
        // 换成读取即，默认先读取比较多的字符量到内存中。
		//1. 先通过具体的io底层操作类，FileReader，(以读模式)来打开文件
		//2. 通过io装饰类，BufferedReader来提高文件操作效率
		BufferedReader in=new BufferedReader(new FileReader(fileName));
		String string;
		StringBuilder sb=new StringBuilder();

		//StreamDecoder？
		while ((string=in.readLine())!=null)
			sb.append(string+"\n");
		in.close();
		return sb.toString();
	}
	public static boolean write(String fileName,String content) throws IOException{
		// private static int defaultCharBufferSize = 8192;
		// this(in, defaultCharBufferSize);
		// 换成读取即，默认先读取比较多的字符量到内存中。
		//1. 先通过具体的io底层操作类，FileWrite，(以写模式)来打开文件
		//2. 通过io装饰类，BufferedWriter 来提高文件操作效率
		BufferedWriter out=new BufferedWriter(new FileWriter(fileName));
		out.write(content);
		// flush只有输出流才需要用
		// flush的作用时，把缓存在内存中的内容输出到对应的目的地(例文件中)
		// 有缓存时flush才有意义，当然建议所有输出流都写
		// 调close时，其也会调用flush
		out.flush();
		out.close();
		return true;
	}
	public static void main(String[] args) throws IOException {
		JSONObject jsonObject =  JSONObject.parseObject("{\"s\":\"success\",\"r\":{\"leaveId\":\"111\"}}");
		Path rootLocation;
		rootLocation = Paths.get("C:\\Users\\Administrator\\Downloads");
		System.out.println(rootLocation);
	}

}
