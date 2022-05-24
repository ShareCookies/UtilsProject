package com.china.hcg.io.file.iostudy;

import java.io.*;

/**
 * @autor hecaigui
 * @date 2021-1-13
 * @description
 */
public class SystemIO {


	void systemIOTest() throws Exception{
		//BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		BufferedOutputStream bWriter = new BufferedOutputStream(System.out);
		bWriter.write("fdsfdsfdsfds1111房贷首付".getBytes());// 为什么这里字节流能正确打印？
		bWriter.flush();
		//bWriter.close();//系统控制台还能关闭的，那怎么在打开了？

		System.out.println("fdsfdsfdsfds1111房贷首付".getBytes());
		System.out.println(1111);
	}

	public static void main(String[] args) throws Exception{
	}
}
