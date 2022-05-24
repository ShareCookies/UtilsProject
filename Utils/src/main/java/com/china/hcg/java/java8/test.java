package com.china.hcg.java.java8;

/**
 * @autor hecaigui
 * @date 2021-4-23
 * @description
 */
public class test {
	public static void main(String[] args) {
		for (int i = 0;i<10;i++){
			try {
				throw new Exception("错误发生捕获了，for还是能继续运行吧");
			}catch (Exception e){

			}
			System.err.println(i);
		}
	}
}
