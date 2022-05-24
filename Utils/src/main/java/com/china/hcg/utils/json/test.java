package com.china.hcg.utils.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @autor hecaigui
 * @date 2020-11-4
 * @description
 */
public class test {

	//JSONObject.parseObject(data, ZgfRcvDocDto.class);
	public static void main(String[] args) {
		String string = "[{'11': '111'}, {'22': '222'}]";
		JSONArray jsonArray = JSONArray.parseArray(string);
		for (Object jsonObject : jsonArray){
			JSONObject jsonObject1 = (JSONObject)jsonObject;
			System.err.println(jsonObject1.toJSONString());
		}
		System.err.println(jsonArray.toJSONString());
	}
}
