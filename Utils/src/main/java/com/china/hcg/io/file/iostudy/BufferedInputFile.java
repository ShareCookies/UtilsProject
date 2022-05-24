package com.china.hcg.io.file.iostudy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
public class BufferedInputFile {

	public static String read(String fileName) throws IOException{
	    // private static int defaultCharBufferSize = 8192;
        // this(in, defaultCharBufferSize);
        // 换成读取即，默认先读取比较多的字符量到内存中。
		BufferedReader in=new BufferedReader(new FileReader(fileName));
		String string;
		StringBuilder sb=new StringBuilder();
		while ((string=in.readLine())!=null) 
			sb.append(string+"\n");
		in.close();
		return sb.toString();
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println(read("E://test.txt"));
	}

}
