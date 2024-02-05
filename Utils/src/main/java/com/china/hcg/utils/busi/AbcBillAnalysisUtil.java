package com.china.hcg.utils.busi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class AbcBillAnalysisUtil {
    public static void main(String[] args) {
        //String inputData = "商户号|二级商户号^^103881310002701|1039913241ABYKK^^103881310002701|1039913241ABZA7";
        String inputData = "商户号|二级商户号";

        // 打印 JSONArray
        System.out.println(analysis(inputData));
    }
    public static JSONArray analysis(String inputData){
        // 分割数据
        String[] lines = inputData.split("\\^\\^");
        String[] fieldNames = lines[0].split("\\|");

        // 创建 JSONArray 对象
        JSONArray jsonArray = new JSONArray();

        // 填充 JSONArray
        for (int i = 1; i < lines.length; i++) {
            String[] fieldValues = lines[i].split("\\|");
            JSONObject dataObject = new JSONObject();

            for (int j = 0; j < fieldValues.length; j++) {
                dataObject.put(fieldNames[j], fieldValues[j]);
            }

            // 添加数据对象到 JSONArray
            jsonArray.add(dataObject);
        }
        return jsonArray;
    }
}
