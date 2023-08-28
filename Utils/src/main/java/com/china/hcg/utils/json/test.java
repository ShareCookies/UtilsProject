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
/*
JAVA之Json对象和Json字符串的区别：
	https://blog.csdn.net/happy_bigqiang/article/details/76710852

        JSONObject result = new JSONObject();
		    ObjectMapper mapper = new ObjectMapper();
			mapper.setTimeZone(TimeZone.getDefault());
        result.put("form", JSON.parse(mapper.writeValueAsString(dispatch)));
			JSONObject permission = null;
        result.put("permission", permission);
使用JSONObject生成和解析json
	https://www.cnblogs.com/joahyau/p/6736637.html
fastJson：
	maven地址：
		https://mvnrepository.com/artifact/com.alibaba/fastjson
	import com.alibaba.fastjson.JSONObject;
	https://www.cnblogs.com/cdf-opensource-007/p/7106018.html
	fastJson对于json格式字符串的解析主要用到了一下三个类：
		JSON：
			fastJson的解析器，用于JSON格式字符串与JSON对象及javaBean之间的转换。

		JSONObject：
			fastJson提供的json对象。

		JSONArray：
			fastJson提供json数组对象。
fastJson转换：
	https://blog.csdn.net/xuforeverlove/article/details/80842148
	将JAVA对象转换为JSON对象：
		JSONObject jsonObj = (JSONObject) JSON.toJSON(对象);
		或
			先对象转json字符串，在字符串转json对象
	JAVA对象转JSON字符串：
		String s = JSON.toJSONString(对象);
		注：
			java对象属性要有get set或.
	Jackson-JSON字符串转JAVA对象:
	    ObjectMapper mapper = new ObjectMapper();
		String json = "{\"name\":\"Alice\",\"age\":30}";
		//mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		Person person = mapper.readValue(json, Person.class);
	FastJSON-JSON字符串转JAVA对象:
		Person person = JSON.parseObject(json, Person.class);
		//注意的是，FastJSON要求目标Java类必须有一个无参构造函数。如果Java类没有提供这样的构造函数，则在解析JSON时会抛出异常。
	JSON字符串转JSON对象：
		String s ="{\"action\":\"add\",\"id\":\"1\",\"ordinal\":8,\"organUnitFullName\":\"testJSON\",\"parent\":\"0\",\"suborderNo\":\"58961\"}";
		JSONObject jsonObject = JSON.parseObject(s);

		@JsonProperty
			这个注解提供了序列化和反序列化过程中该java属性所对应的名称
		@JsonAlias
			这个注解只只在反序列化时起作用，指定该java属性可以接受的更多名称

	...
	String转json数组
		JSONArray jsArr = JSONObject.parseArray(aaa);
	jsonArray遍历：
		for遍历：
			JSONArray jSONArray = new JSONArray();
			for(int i=0;i<ja.size();i++) {
				System.out.println(ja.getJSONObject(i).get("id"));
			}
    常用方法：
		判断：
			.containsKey("")
		获取：
			.getJSONArray
			.getJSONObject

JS:
	字符串转对象：
		JSON.parse() 将字符串转换为 JavaScript 对象。
	对象转字符串：
		JSON.stringify()	用于将 JavaScript 值转换为 JSON 字符串。

@JSONField

日期格式化：
	https://blog.csdn.net/qq_42033668/article/details/122928849
附：
	如何打印对象拥有的属性：1. toString 2. 转jsonobject 3. 反射
 */

