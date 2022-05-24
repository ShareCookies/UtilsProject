package com.china.hcg.io.file.nioStudy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/**
 * @autor hecaigui
 * @date 2021-1-13
 * @description
 * nio在文件上优势不是太大，因为nio的异步特性在文件读取上并不能体现出来.
 *  文件不会开所以体现不出来
 */
public class file {
	/**
	 * @description 文件的复制
	 * @param src
	 * @param dist
	 * @return
	 */
	public static void fastCopy(String src, String dist) throws IOException {
		/* 获得源文件的输入字节流 */
		FileInputStream fin = new FileInputStream(src);
		File file = new File("");
		/* 获取输入字节流的文件通道 */
		FileChannel fcin = fin.getChannel();
		/* 获取目标文件的输出字节流 */
		FileOutputStream fout = new FileOutputStream(dist);
		/* 获取输出字节流的文件通道 */
		FileChannel fcout = fout.getChannel();
		/* 为缓冲区分配 1024 个字节 */
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
		while (true) {
			/* 从输入通道中读取数据到缓冲区中 */
			int r = fcin.read(buffer);


			/* read() 返回 -1 表示 EOF */
				//EOF end of file
				// 1。 文本里有35个字符，r值和position都是35？ 2. 不应该直接读到结尾了吗，为什么还要在来一次while，r才到eof？
			if (r == -1) {
				break;
			}
			/* 切换读写 */
			buffer.flip();
			/* 把缓冲区的内容写入输出文件中 */
			fcout.write(buffer);
			/* 清空缓冲区 */
			buffer.clear();
//读前
//limit = 1024
//position = 0
//mark = -1
//offset = 0

// 读后
// limit = 1024
// position = 39
// mark = -1
// offset = 0

//切换读写后
//limit = 39
//position = 0
//mark = -1
//offset = 0

//写后
//limit = 39
//position = 39
//mark = -1
//offset = 0

//清理后
//limit = 1024
//position = 0
//mark = -1
//offset = 0
		}
		fcin.close();
		fin.close();
	}

	public static void main(String[] args) throws Exception{
		fastCopy("F://testFile.txt","F://test.txt");
	}
}
