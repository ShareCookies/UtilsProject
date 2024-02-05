package com.china.hcg.applications.chao_gu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.china.hcg.applications.chao_gu.model.GuInfo;
import com.china.hcg.io.file.FileUtils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @autor hecaigui
 * @date 2023/9/21
 * @description
 */
public class GuDayDataLt {
    public static void main(String[] args) throws InterruptedException {
//////        跑龙头
        String arr = FileUtils.readTxtContent(new File("D:\\heSpace\\gu\\lt.txt"));
      JSONArray jsonArray = JSONArray.parseArray(arr);
       List<GuInfo> list = new LinkedList<>();
       for (Object o : jsonArray) {
       JSONObject jsonObject = (JSONObject) o;
      GuInfo guInfo = new GuInfo(jsonObject.getString("code"),jsonObject.toJSONString());
        list.add(guInfo);
     }
        GuDayData.printLatestTwoDay(list);
    }
 }
