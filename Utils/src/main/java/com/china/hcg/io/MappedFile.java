package com.china.hcg.io;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @autor hecaigui
 * @date 2021-4-26
 * @description
 */
public class MappedFile {
	static int length = 0x8FFFFFF; // 128 MB
	public static void main(String[] args) throws Exception{
		//为了既能写又能读，我们先由RandomAccessFile开始，获得该文件上的通道，然后调用map0产生MappedByteBuffer, 这是一种特殊类型的直接缓冲器。
			//MappedByteBuffer由ByteBuffer继承而来，因此它具有ByteBuffer的所有方法。如asCharBuffer0等这样的用法。
		// 注意我们必须指定映射文件的初始位置和映射区域的长度，这意味着我们可以映射某个大文件的较小的部分。
		//似乎我们可以一次访问到整个文件，因为只有一部分文件放入了内存，文件的其他部分被交换了出去？。用这种方式，很大的文件(可达2GB)也可以很容易地修改。（是每次都载入一部分吗？）
//		MappedByteBuffer out =
//		new RandomAccessFile("test.txt","rw").getChannel().
//				map(FileChannel.MapMode.READ_WRITE,0,length);
//		for(int i=0; i < length; i++)
//					out.put((byte)'x');
//			print("Finished Writing");
//		for(int i = length/2; i < length/2 + 6; i++)
//			printnb((char)out.get(1));
	}
}
