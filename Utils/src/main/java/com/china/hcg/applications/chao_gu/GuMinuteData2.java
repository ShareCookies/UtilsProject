package com.china.hcg.applications.chao_gu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.china.hcg.applications.chao_gu.dao.stockdata.MinuteFundDirectionsStockDataFactory;
import com.china.hcg.applications.chao_gu.dao.stockdata.MinuteFundsStockDataFactory;
import com.china.hcg.applications.chao_gu.dao.stockdata.StockDataTypes;
import com.china.hcg.applications.chao_gu.dao.stockdata.ThsMinuteFundsStockDataFactory;
import com.china.hcg.applications.chao_gu.model.GuInfo;
import com.china.hcg.applications.chao_gu.utilscommon.StockThreadPoolUtil;
import com.china.hcg.applications.chao_gu.utilscommon.TextTable;
import com.china.hcg.applications.chao_gu.utilscommon.TextTableExpand;
import com.china.hcg.applications.chao_gu.utilsgu.GuMinuteDataUtils;
import com.china.hcg.http.HttpClientUtil;
import com.china.hcg.http.utils.HttpUtil;
import com.china.hcg.io.file.FileUtils;
import com.china.hcg.utils.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.*;
import java.util.concurrent.*;

/**
 * @autor hecaigui
 * @date 2022-9-15
 * @description
 */
@Slf4j
public class GuMinuteData2 {
    private static Logger logger = Logger.getLogger(GuMinuteData2.class);
    public List<GuInfo> list = new ArrayList();
    public GuMinuteData2() {

    }

    public static void main(String[] args) {
        String data = HttpClientUtil.get("https://push2.eastmoney.com/api/qt/ulist.np/get?fltt=2&fields=f2,f3,f12&secids=1.510300");
        System.err.println(data);
    }
    public static void start() {
//        printMinuteGuInfo(new GuInfo("600887","伊利","sz"));
    }
    public static void start2() {
        List<GuInfo> list = new ArrayList<>();
//        list.add(new GuInfo("510300","510300","sh"));



//        list.add(new GuInfo("600775","600775","sz"));


//        list.add(new GuInfo("600775","600775","sz"));

//        list.add(new GuInfo("002229","002229","sz"));
//        list.add(new GuInfo("002865","002865","sz"));
//        list.add(new GuInfo("603178","603178","sz"));
//        list.add(new GuInfo("603598","603598","sz"));
        list.add(new GuInfo("603660","603660","sz"));
//

        for (GuInfo guInfo : list) {
            printMinuteGuInfo(guInfo);
        }
    }
    /**
     * @description console打印股票分时详细信息table
     */
    public static void printMinuteGuInfo(GuInfo guInfo){
        System.out.println("大单：大于等于6万股或者30万元以上的成交单。\n" +
                "中单：大于等于1万股小于6万股或者大于等于5万元小于30万元的成交单。\n" +
                "小单：小于1万股或5万元的成交单。");
        MinuteData minuteData = minuteData(guInfo,new StockDataTypes[]{StockDataTypes.MinuteFundDirections,StockDataTypes.ThsMinuteFunds,StockDataTypes.MinuteFunds});
//        MinuteData minuteData = minuteData(guInfo,null);
        System.err.println(minuteData);
//        FileUtils.writeToFile(new File("C:\\Users\\98279\\Desktop\\gu收集\\成功案例\\"+));
    }
    /**
     * @description console打印多只股票最新价格信息
     */
    public static JSONArray printLatestMinuteGuInfo(List<GuInfo> list) {
        //数据的并发获取
        String data = HttpClientUtil.get("https://push2.eastmoney.com/api/qt/ulist.np/get?fltt=2&fields=f2,f3,f12&secids=1.510300");
        JSONArray diff = JSONObject.parseObject(data).getJSONObject("data").getJSONArray("diff");

        return diff;
    }


    /**
     * @description 输出股票详细分时信息到指定文件夹，当日分时json和分时table
     */
    static void outGuInfo(List<GuInfo> list,String outFilePath){
        for (GuInfo guInfo : list) {
            MinuteData minuteData = minuteData(guInfo,new StockDataTypes[]{StockDataTypes.MinuteFundDirections,StockDataTypes.ThsMinuteFunds,StockDataTypes.MinuteFunds});
            FileUtils.writeToFile(new File(outFilePath), DateUtil.dateToString(new Date(),"yyyy-MM-dd")+guInfo.name+"json.txt",minuteData.minutePriceData.toJSONString());
            FileUtils.writeToFile(new File(outFilePath),DateUtil.dateToString(new Date(),"yyyy-MM-dd")+guInfo.name+".txt",minuteData.minutePriceDataTable);
        }
    }

    /**
     * @description 数据结构-存储分钟数据
     */
    //?内部类的危害等
    static class MinuteData{
        String guCode;
        JSONArray minutePriceData;
        String minutePriceDataTable;

        public MinuteData(String guCode, JSONArray minutePriceData, String minutePriceDataTable) {
            this.guCode = guCode;
            this.minutePriceData = minutePriceData;
            this.minutePriceDataTable = minutePriceDataTable;
        }

        public String getGuCode() {
            return guCode;
        }

        public void setGuCode(String guCode) {
            this.guCode = guCode;
        }

        public JSONArray getMinutePriceData() {
            return minutePriceData;
        }

        public void setMinutePriceData(JSONArray minutePriceData) {
            this.minutePriceData = minutePriceData;
        }

        public String getMinutePriceDataTable() {
            return minutePriceDataTable;
        }

        public void setMinutePriceDataTable(String minutePriceDataTable) {
            this.minutePriceDataTable = minutePriceDataTable;
        }

        @Override
        public String toString() {
            return minutePriceDataTable;
        }

    }
    /**
     * @description 获取分钟数据-外观模式，整合了所有分钟数据相关接口
     * @author hecaigui
     * @date 2022-12-6
     * @param
     * @return
     */
    public static MinuteData minuteData(GuInfo guInfo, StockDataTypes[] wantGetStockDatas){
        //好处?
        StringBuilder stringBuilder = new StringBuilder();

        try {
            // 1. 获取请求
            Future<JSONArray> minutePrice = GuMinuteData2.asyncGetMinuteData(guInfo.getCode());
            //1.1 根据需求，自定义发起异步请求获取不同的分钟扩展数据
            //1.2 自定义-分钟资金量
            ThsMinuteFundsStockDataFactory thsMinuteFundsStockDataFactory = null;
            if (ThsMinuteFundsStockDataFactory.isSupport(wantGetStockDatas)){
                 thsMinuteFundsStockDataFactory = new ThsMinuteFundsStockDataFactory(guInfo);
            }
            //1.3 自定义-分钟资金流势
            MinuteFundDirectionsStockDataFactory minuteFundDirectionsStockDataFactory = null;
            if (MinuteFundDirectionsStockDataFactory.isSupport(wantGetStockDatas)){
                 minuteFundDirectionsStockDataFactory = new MinuteFundDirectionsStockDataFactory(guInfo);
            }
            //1.4 自定义-分钟大单资金量
            MinuteFundsStockDataFactory minuteFundsStockDataFactory = null;
            if (MinuteFundsStockDataFactory.isSupport(wantGetStockDatas)){
                minuteFundsStockDataFactory = new MinuteFundsStockDataFactory(guInfo);
            }
            //2. 分钟数据
            JSONArray minutePriceData = minutePrice.get();
            if (thsMinuteFundsStockDataFactory != null) {
                thsMinuteFundsStockDataFactory.getAsyncRequestResult();
            }
            //2.2
            if (minuteFundDirectionsStockDataFactory != null) {
                minuteFundDirectionsStockDataFactory.getAsyncRequestResult();
            }
            //2.4
            if (minuteFundsStockDataFactory != null) {
                minuteFundsStockDataFactory.getAsyncRequestResult();
            }
            //3.
            GuMinuteDataUtils.minuteDataCustomZhangDieLiang(minutePriceData);
            //3.1 自定义-分钟资金量
            if (thsMinuteFundsStockDataFactory != null) {
                thsMinuteFundsStockDataFactory.decoratorData(minutePriceData);
            }
            //3.4 自定义-分钟大单资金量
            if (minuteFundsStockDataFactory != null) {
                minuteFundsStockDataFactory.decoratorData(minutePriceData);
            }

            //4. 分钟数据table
            TextTable textTable = TextTableExpand.standardJsonArrayTextTable(minutePriceData);
            stringBuilder.append(textTable.printTable());
            stringBuilder.append(System.getProperty("line.separator"));
            //4.1 自定义-分钟资金量
            if (thsMinuteFundsStockDataFactory != null) {
                thsMinuteFundsStockDataFactory.decoratorDataTableString(stringBuilder);
            }
            //4.2 自定义-分钟资金流势
            if (minuteFundDirectionsStockDataFactory != null) {
                minuteFundDirectionsStockDataFactory.decoratorDataTableString(stringBuilder);
            }
            //4.4 自定义-大单分钟资金量
            if (minuteFundsStockDataFactory != null) {
                minuteFundsStockDataFactory.decoratorDataTableString(stringBuilder);
            }
            return new MinuteData(guInfo.getCode(),minutePriceData,stringBuilder.toString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
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
        Future<JSONArray> future = StockThreadPoolUtil.executorService.submit(new Callable<JSONArray>() {
            @Override
            public JSONArray call() throws Exception {
                return getMinuteData(stockCode);
            }
        });
        return future;
    }


}
