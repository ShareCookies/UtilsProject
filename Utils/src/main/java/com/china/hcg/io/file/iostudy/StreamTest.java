package com.china.hcg.io.file.iostudy;


import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 */
public class StreamTest {

	public static String read(String fileName) throws IOException{
        // 换成读取即，默认先读取比较多的字符量到内存中。
		//1. 先通过具体的io底层操作类，FileInputStream，来打开文件
		//2. 通过io装饰类，BufferedInputStreamr来提高文件操作效率
		BufferedInputStream in=new BufferedInputStream(new FileInputStream(fileName));
		//utf8下通常一个汉字为3个byte，你一个个读出来转码是不行的。例：new byte[1] 就会出错
		//所以 1. 可以全部读出来统一转码，然后输出。2. 大部分情况下3应该是可以吧？
		byte[] container = new byte[3];
		StringBuilder sb = new StringBuilder();
		// in.read 以字节方式读取文件内容
		// eof: -1 为文件结束
		while ((in.read(container))!=-1)
			sb.append(new String(container,"UTF-8"));
		in.close();
		return sb.toString();
	}

	/**
	 * @description java汉字字节问题
	 *  https://blog.csdn.net/loveshunyi/article/details/90680487
	 *  不同的编码格式占字节数是不同的，UTF-8编码下一个中文所占字节也是不确定的，可能是2个、3个、4个字节；(通常1英文1字节 1中文3字节)
		UTF-8
			UTF-16 统一采用两个字节表示一个字符，
			虽然在表示上非常简单方便，但是也有其缺点，有很大一部分字符用一个字节就可以表示的现在要两个字节表示，存储空间放大了一倍，在现在的网络带宽还非常有限的今天，这样会增大网络传输的流量。

			而 UTF-8 采用了一种变长技术，每个编码区域有不同的字码长度。不同类型的字符可以是由 1~6 个字节组成。
			UTF-8 有以下编码规则：？
				如果一个字节，最高位（第 8 位）为 0，表示这是一个 ASCII 字符（00 - 7F）。可见，所有 ASCII 编码已经是 UTF-8 了。
				如果一个字节，以 11 开头，连续的 1 的个数暗示这个字符的字节数，例如：110xxxxx 代表它是双字节 UTF-8 字符的首字节。
				如果一个字节，以 10 开始，表示它不是首字节，需要向前查找才能得到当前字符的首字
	        ？
	            那字节容器到底要开辟多少合适了，避免出现乱码
	I/O 操作中存在的编码
		...
	Java Web 涉及到的编码：
	        ...?
	内存中操作中的编码
	    ...
	 */
	void charsetTest() throws IOException {
		String a = "名";
		System.out.println("UTF-8编码长度:"+a.getBytes("UTF-8").length);
		System.out.println("GBK编码长度:"+a.getBytes("GBK").length);
		System.out.println("GB2312编码长度:"+a.getBytes("GB2312").length);
		System.out.println("==========================================");



		char[] arr = Character.toChars(0x20001);
		System.out.println("char array length:" + arr.length);
		String s = new String(arr);
		System.out.println("content:|  " + s + " |");
		System.out.println("String length:" + s.length());
		System.out.println("UTF-8编码长度:"+s.getBytes("UTF-8").length);
		System.out.println("GBK编码长度:"+s.getBytes("GBK").length);
		System.out.println("GB2312编码长度:"+s.getBytes("GB2312").length);
		System.out.println("==========================================");
	}
	/**
	 * @description 写测试
	 * System.out.println( write("D:\\outputStreamTest.txt","fds地方大师傅十分房贷首付223213".getBytes(StandardCharsets.UTF_8)));
	 * @author hecaigui
	 * @date 2021-11-1
	 * @param fileName
	 * @param content
	 * @return
	 */
	public static boolean write(String fileName,byte[] content) throws IOException{
		BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(fileName));
		out.write(content);
		out.flush();
		out.close();
		return true;
	}
	public static void main(String[] args) throws IOException {
//		System.out.println( read("D:\\file1.txt"));

		System.out.println( write("D:\\outputStreamTest.txt","fds地方大师傅十分房贷首付223213".getBytes(StandardCharsets.UTF_8)));

	}

}
