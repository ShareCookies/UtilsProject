package com.china.hcg.utils.num;

import java.text.NumberFormat;

/**
 * @autor hecaigui
 * @date 2021-4-26
 * @description
 */
public class Num {
	/**
	 * @description
	 * @demo percent(2,11);
	 * @author hecaigui
	 * @date 2021-4-26
	 * @param molecule
	 * @param denominator
	 * @return
	 */
	static String percent(int molecule,int denominator){
//		if (denominator == 0){
//
//		}
		float resultInt = ((float) molecule / (float)denominator) * 100;
		// 创建一个数值格式化对象
		NumberFormat numberFormat = NumberFormat.getInstance();
		// 设置精确到小数点后0位
		numberFormat.setMaximumFractionDigits(0);
		String resultPercent = numberFormat.format(resultInt)+"%";//所占百分比
		System.err.println(resultPercent);
		return resultPercent;
	}

	public static void main(String[] args) {
		percent(2,11);
	}
}
