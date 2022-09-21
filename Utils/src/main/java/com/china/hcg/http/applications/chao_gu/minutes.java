package com.china.hcg.http.applications.chao_gu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.china.hcg.http.HttpClientUtil;
import com.china.hcg.http.utils.HttpUtil;
import org.apache.commons.collections4.ListUtils;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @autor hecaigui
 * @date 2022-9-15
 * @description
 */
public class minutes {
    public static void main(String[] args) {
                //System.err.println(minute_data_price);
        //JSONArray minute_data_price = minutes.getMinuteData("002415");
        // 分众 002027
//        JSONArray minute_data_price = minutes.getMinuteData("002027");
        //京东方a
        JSONArray minute_data_price = minutes.getMinuteData("000725");
        minutes.standardJsonArrayPrint(minute_data_price);

//        if (minute_data_price.size() > 10){
//            minutes.standardJsonArrayPrint((JSONArray)minute_data_price.subList(minute_data_price.size() - 10,minute_data_price.size()));
//        }else {
//            minutes.standardJsonArrayPrint(minute_data_price);
//        }
    }
    static JSONArray getMinuteData(@NotNull String stockCode){
        String r = HttpClientUtil.getForHttpsAndCookie("https://gushitong.baidu.com/opendata?openapi=1&dspName=iphone&tn=tangram&client=app&query="+stockCode+"&code="+stockCode+"&word="+stockCode+"&resource_id=5429&ma_ver=4&finClientType=pc");
        //?
        //https://blog.csdn.net/adsl624153/article/details/79562282
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        JSONObject rj = JSONObject.parseObject(r);

        JSONArray minute_data_price = rj.getJSONArray("Result").getJSONObject(1).getJSONObject("DisplayData").getJSONObject("resultData").getJSONObject("tplData").getJSONObject("result").getJSONObject("minute_data").getJSONArray("priceinfo");
        return minute_data_price;
    }
    /**
     * @description 标准json数组打印
     * 标准：所有的列数量要相同
     */
    static void standardJsonArrayPrint(JSONArray minute_data_price){
        //列
        Set<String> sets = minute_data_price.getJSONObject(0).keySet();
        List<String> columnNameList = new ArrayList<String>(sets);

        //
        List<List<String>> allValues = new ArrayList<>();
        for (Object js : minute_data_price) {
            JSONObject  jsonObject = (JSONObject)js;
            Collection values = jsonObject.values();
            List<String> valueList = new ArrayList(values);
            allValues.add(valueList);
        }


        TextTable textTable = new TextTable(columnNameList,allValues);
        System.err.println(textTable.printTable());
    }
    //https://zhidao.baidu.com/question/1576619087572264900.html
    // VOLUME在股票中叫做成交量，指一个时间单位内对某项交易成交的数量。
    // 成交量指的是当天成交的股票总手数（其中1手=100股）。需要注意的是，通常人们说的大盘成交量指的是成交金额。说明市场的活跃度和资金规模。

    // AMOUNT在股票中叫做成交金额，指一个时间单位内对某项交易成交的金额。
    // 成交量与成交金额用下列公式表示：成交数量（成交量）*成交均价=成交金额(成交额)



    /*
英文：Volume 表示股票成交量的意思，成交量=成交股数*成交价格 再加权求和；成交量指标(VOL)是指一个时间单位内撮合成交的股数，用柱形实体表示。里面绿柱，红柱是随当日K线的变化而变化，也就是说日K线收红则成交量柱也为红，反之是绿。但并不是说涨了就为红，而是收盘价高于开盘价则K线为红，收盘价低于开盘价则K线为绿。
成交量：指当天成交的股票数量。一般情况下，成交量大且价格上涨的股票，趋势向好。成交量持续低迷时，一般出现在熊市或股票整理阶段，市场交投不活跃。
成交量是判断股票走势的重要依据，对分析主力行为提供了重要的依据。投资者对成交量异常波动的股票应当密切关注。
关于成交量的股谚有：量在价先，即天量见天价，地量见地价；底部放量，闭眼买进。据此操作，成功率较高，机会也不少。
一般行情分析软件中，上面的大图是主图，下面的两个小图，其中一个就是成交量或成交额。
新手可以先看些K线方面的书，如《日本蜡烛图》，了解一下技术分析的基础知识。各K线、均线的运用，前期可用个牛股宝模拟炒股去看一下，里面的各项指标都有详细说明如何运用，在什么样的形态下表示什么意思，使用起来要方便很多，愿可帮助到你，祝投资愉快！
     */
}
