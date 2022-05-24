package com.china.hcg.io;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * @autor hecaigui
 * @date 2021-4-26
 * @description
 */
public class IntBufferDemo {
	private static final int BSIZE = 1024;
	public static void main(String[] args) {
		ByteBuffer bb = ByteBuffer.allocate(BSIZE);
		IntBuffer ib = bb.asIntBuffer();
		ib. put(new int[]{ 11,2 ,33});
		System.out.println(ib. get(1));
		ib. put(1,1811);
		ib.flip();
		while(ib. hasRemaining()) {
			int i = ib.get();
			System.out.println(i);
		}
	}
}
