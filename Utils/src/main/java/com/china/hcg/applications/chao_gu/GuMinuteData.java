package com.china.hcg.applications.chao_gu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.china.hcg.http.HttpClientUtil;
import com.china.hcg.applications.chao_gu.utilscommon.TextTable;
import com.china.hcg.applications.chao_gu.utilsgu.GuMinuteDataUtils;
import com.china.hcg.io.file.FileUtils;
import com.china.hcg.utils.date.DateUtil;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.*;
import java.util.concurrent.*;

/**
 * @autor hecaigui
 * @date 2022-9-15
 * @description
 */
public class GuMinuteData {
    //？核心线程是什么时候创建的
    static ExecutorService  executorService = new ThreadPoolExecutor(0,10,5,TimeUnit.SECONDS,new LinkedBlockingDeque());

    public static void main(String[] args) {

//        Map<String,String> guCodeNameInfo = new HashMap<>();
//        guCodeNameInfo.put("000725","京东方a");
//        guCodeNameInfo.put("002027","分众传媒");
//        guCodeNameInfo.put("000776","广发证券");
//        guCodeNameInfo.put("601456","国联证券");
//        guCodeNameInfo.put("600383","金地集团");
//        guCodeNameInfo.put("002241","歌尔股份");
//        guCodeNameInfo.put("601377","兴业证券");
//        guCodeNameInfo.put("601229","上海银行");
//        guCodeNameInfo.put("002415","海康威视");
//        guCodeNameInfo.put("600276","恒瑞医药");
//        guCodeNameInfo.put("601318","中国平安");
//        guCodeNameInfo.put("600741","华域汽车");
//        guCodeNameInfo.put("002460","赣锋锂业");
//        guCodeNameInfo.put("002466","天齐锂业");
//        guCodeNameInfo.put("000012","南玻A");
//        guCodeNameInfo.put("000792","盐湖股份");
//        outGuInfo(guCodeNameInfo);



        //printGuInfo("0000001","上证指数");
        //printGuInfo("000792","盐湖股份");
        //printGuInfo("002027","分众传媒");
        printGuInfo("000776","广发证券");
        //printGuInfo("002241","歌尔股份");

        //printGuInfo("601456","国联证券");
        //printGuInfo("000725","京东方a");
        //printGuInfo("000012","南玻A");
        //printGuInfo("600383","金地集团");
        //printGuInfo("600741","华域汽车");
        //printGuInfo("600276","恒瑞医药");
        //printGuInfo("601318","中国平安");
        //printGuInfo("000725","京东方a");
    }
    /**
     * @description console打印stock分时table
     */
    public static void printGuInfo(String guCode,String guName){
//        JSONArray minute_data_price = MinuteData.getMinuteData(guCode);
//        System.err.println(guCode+guName);
//        GuDataUtils.minuteDataCustom(minute_data_price);
//        TextTable textTable = MinuteData.standardJsonArrayPrint(minute_data_price);
//        System.err.println(textTable.printTable());
        System.err.println("大单：大于等于6万股或者30万元以上的成交单。\n" +
                "中单：大于等于1万股小于6万股或者大于等于5万元小于30万元的成交单。\n" +
                "小单：小于1万股或5万元的成交单。");
        System.err.println(minuteData(guCode,guName));
    }

    //?内部类的危害等
    static class MinuteData{
        JSONArray minutePriceData;
        String minutePriceDataTable;

        public MinuteData(JSONArray minutePriceData, String minutePriceDataTable) {
            this.minutePriceData = minutePriceData;
            this.minutePriceDataTable = minutePriceDataTable;
        }

        @Override
        public String toString() {
            return minutePriceDataTable;
        }
    }
    public static MinuteData minuteData(String guCode,String guName){
        //好处?
        StringBuilder stringBuilder = new StringBuilder();
        Future<JSONArray> minutePrice = GuMinuteData.asyncGetMinuteData(guCode);
        Future<JSONObject> minuteFunds = GuMinuteData.asyncGetMinuteFunds(guCode);
        Future<JSONObject> minuteFundDirections = GuMinuteData.asyncGetMinuteFundsDirection(guCode);
        try {
            JSONArray minutePriceData = minutePrice.get();
            JSONObject minuteFundsData = minuteFunds.get();
            JSONObject minuteFundDirectionData = minuteFundDirections.get();

            GuMinuteDataUtils.minuteDataCustomZhangDieLiang(minutePriceData);
            GuMinuteDataUtils.minuteDataCustomFunds(minutePriceData,minuteFundsData.getString("line").split("\\|"));

            TextTable textTable = GuMinuteData.standardJsonArrayPrint(minutePriceData);
            stringBuilder.append(textTable.printTable());
            stringBuilder.append(System.getProperty("line.separator"));

            //分钟资金量
            stringBuilder.append(minuteFundsData.getJSONObject("diff"));
            stringBuilder.append(System.getProperty("line.separator"));
            JSONObject diff = new JSONObject();
            diff.put("ddje","大单净额"+minuteFundsData.getJSONObject("diff").get("ddje"));
            diff.put("zdje","中单净额"+minuteFundsData.getJSONObject("diff").get("zdje"));
            diff.put("xdje","小单净额"+minuteFundsData.getJSONObject("diff").get("xdje"));
            stringBuilder.append(guName+"主力流入："+minuteFundsData.getJSONObject("dde").getString("zllx"));
            stringBuilder.append(System.getProperty("line.separator"));
            stringBuilder.append(guName+"资金占比："+diff);
            JSONObject diff2 = new JSONObject();
            diff2.put("ddjb","大单占比"+minuteFundsData.getJSONObject("diff").get("ddjb"));
            diff2.put("zdjb","中单占比"+minuteFundsData.getJSONObject("diff").get("zdjb"));
            diff2.put("xdjb","小单占比"+minuteFundsData.getJSONObject("diff").get("xdjb"));
            stringBuilder.append(guName+"资金占比比例："+diff2);
            stringBuilder.append(System.getProperty("line.separator"));

            //资金流势
            //stringBuilder.append(guName+"流入流出详细："+minuteFundDirectionData);
            //stringBuilder.append(System.getProperty("line.separator"));
            stringBuilder.append(guName+"总流入流出"+minuteFundDirectionData.getJSONObject("title"));
            stringBuilder.append(System.getProperty("line.separator"));

            stringBuilder.append(guName+"流入流出详细：");
            JSONObject lrlcxq = new JSONObject();
            for (Object object : minuteFundDirectionData.getJSONArray("flash")) {
                JSONObject jsonObject =  (JSONObject)object;
                lrlcxq.put(jsonObject.getString("name"),jsonObject.getString("sr"));
            }
            stringBuilder.append(lrlcxq);
            stringBuilder.append(System.getProperty("line.separator"));

            //
            return new MinuteData(minutePriceData,stringBuilder.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void printDayGuInfo(String guCode,String guName){
        JSONArray minute_data_price = GuMinuteData.getDayData(guCode);
        System.err.println(guCode+guName);
        TextTable textTable = GuMinuteData.standardJsonArrayPrint(minute_data_price);
        System.err.println(textTable.printTable());
        //System.err.println(guCode+guName);
    }

    /**
     * @description 输出股票信息，当日分时json和分时table
     */
    static void outGuInfo(Map<String,String> guCodeNameInfo){
        Set<String> guCodes = guCodeNameInfo.keySet();
        for (String guCode : guCodes) {
            MinuteData minuteData = minuteData(guCode,guCodeNameInfo.get(guCode));
            String outFilePath = "D:/chaogu/";
            FileUtils.writeToFile(new File(outFilePath), DateUtil.dateToString(new Date(),"yyyy-MM-dd")+guCodeNameInfo.get(guCode)+"json.txt",minuteData.minutePriceData.toJSONString());
            FileUtils.writeToFile(new File(outFilePath),DateUtil.dateToString(new Date(),"yyyy-MM-dd")+guCodeNameInfo.get(guCode)+".txt",minuteData.minutePriceDataTable);
        }
    }
    /**
     * @description 获取分时数据
     * @return
     */
    private static JSONArray getMinuteData(@NotNull String stockCode){
        String r = HttpClientUtil.getForHttpsAndCookie("https://gushitong.baidu.com/opendata?openapi=1&dspName=iphone&tn=tangram&client=app&query="+stockCode+"&code="+stockCode+"&word="+stockCode+"&resource_id=5429&ma_ver=4&finClientType=pc");
        //?
        //https://blog.csdn.net/adsl624153/article/details/79562282
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        JSONObject rj = JSONObject.parseObject(r);

        JSONArray minute_data_price = rj.getJSONArray("Result").getJSONObject(1).getJSONObject("DisplayData").getJSONObject("resultData").getJSONObject("tplData").getJSONObject("result").getJSONObject("minute_data").getJSONArray("priceinfo");
        return minute_data_price;
    }
    public static Future<JSONArray> asyncGetMinuteData(@NotNull String stockCode){
        Future<JSONArray> future = executorService.submit(new Callable<JSONArray>() {
            @Override
            public JSONArray call() throws Exception {
                return getMinuteData(stockCode);
            }
        });
        return future;
    }
    private static JSONArray getDayData(@NotNull String stockCode){
        String r = HttpClientUtil.getForHttpsAndCookie("https://finance.pae.baidu.com/selfselect/getstockquotation?code="+stockCode+"&all=1&ktype=1&isIndex=false&isBk=false&isBlock=false&isFutures=false&stockType=ab&group=quotation_kline_ab&finClientType=pc");
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        JSONObject rj = JSONObject.parseObject(r);

        JSONArray minute_data_price = rj.getJSONArray("Result");
        JSONArray newData = new JSONArray();
        for (int i = 0; i < minute_data_price.size(); i++) {
            JSONObject jsonObject = minute_data_price.getJSONObject(i);
            JSONObject newJsonObject = jsonObject.getJSONObject("kline");
            newJsonObject.put("date",jsonObject.getString("date"));
            newData.add(newJsonObject);
        }
        return newData;
    }
    /**
     * 实时资金
     *     //主力流向
     *     //{"dde":{"zllx":"-1079.94"},
     *     // 分钟资金流向数据，大单、中单、小单
     *     // "line":"09:30;-414.69;102.51;34.70|",
     *     // 资金占比差异，ddje 大单净额 ddjb 大单占比，zdje 中单净额 zdjb 中单占比，xdje 小单净额 xdjb 小单占比
     *     // "diff":{"ddje":"-1079.94","ddjb":"-4.27","zdjb":"-0.74","xdjb":"0.57","zdje":"-187.54","xdje":"143.73"},
     * 	//"info":""}
     * @date 2022-11-7
     * @return
     */

    private static JSONObject getMinuteFunds(@NotNull String stockCode){
        String r = HttpClientUtil.get("http://stockpage.10jqka.com.cn/spService/"+stockCode+"/Funds/lineFunds");
        JSONObject result = JSONObject.parseObject(r);
        return result;
    }
    public static Future<JSONObject> asyncGetMinuteFunds(@NotNull String stockCode){
        Future<JSONObject> future = executorService.submit(new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                return getMinuteFunds(stockCode);
            }
        });
        return future;
    }
    /**
     * 实时资金流向
     * @date 2022-11-7
     * @return
     */
    private static JSONObject getMinuteFundsDirection(@NotNull String stockCode){
        String r = HttpClientUtil.get("http://stockpage.10jqka.com.cn/spService/"+stockCode+"/Funds/realFunds");
        JSONObject result = JSONObject.parseObject(r);
        return result;
    }
    public static Future<JSONObject> asyncGetMinuteFundsDirection(@NotNull String stockCode){
        Future<JSONObject> future = executorService.submit(new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                return getMinuteFundsDirection(stockCode);
            }
        });
        return future;
    }
    /**
     * @description 标准json数组打印
     * 标准：所有的列数量要相同
     */
    public static TextTable standardJsonArrayPrint(JSONArray minute_data_price){
        //列名
        Set<String> sets = minute_data_price.getJSONObject(0).keySet();
        List<String> columnNameList = new ArrayList<String>(sets);

        //列值
        List<List<String>> allValues = new ArrayList<>();
        for (Object js : minute_data_price) {
            JSONObject  jsonObject = (JSONObject)js;
            Collection values = jsonObject.values();
            List<String> valueList = new ArrayList(values);
            allValues.add(valueList);
        }
        TextTable textTable = new TextTable(columnNameList,allValues);
        return textTable;
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
