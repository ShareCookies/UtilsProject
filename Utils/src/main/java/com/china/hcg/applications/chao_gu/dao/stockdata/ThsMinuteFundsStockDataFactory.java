package com.china.hcg.applications.chao_gu.dao.stockdata;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.china.hcg.applications.chao_gu.model.GuInfo;
import com.china.hcg.applications.chao_gu.utilscommon.StockThreadPoolUtil;
import com.china.hcg.applications.chao_gu.utilsgu.GuMinuteDataUtils;
import com.china.hcg.http.HttpClientUtil;

import javax.validation.constraints.NotNull;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @autor hecaigui
 * @date 2022-12-8
 * @description
 */
public class ThsMinuteFundsStockDataFactory extends AbstractStockDataFactory {

    JSONObject minuteFundsData = null;
    Future<JSONObject> future = null;

    @Override
    public  void asyncRequest(@NotNull String stockCode){
        Future<JSONObject> future = StockThreadPoolUtil.executorService.submit(new Callable<JSONObject>() {
            @Override
            public JSONObject call(){
                return getMinuteFunds(stockCode);
            }
        });
        this.future = future;
    }
    @Override
    public  Object getAsyncRequestResult() {
        try {
            System.err.println("get");
            minuteFundsData = future.get();
            return minuteFundsData;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ThsMinuteFundsStockDataFactory(GuInfo guInfo) {
        super(guInfo);
        asyncRequest(guInfo.getCode());
        System.err.println(super.toString());
        System.err.println(this.toString());
        System.err.println(super.guInfo);
        System.err.println(this.guInfo);
        System.err.println(minuteFundsData);
    }


    public static boolean isSupport(StockDataTypes[] wantGetStockDatas) {
        if (wantGetStockDatas == null){
            return false;
        }
        for (StockDataTypes wantGetStockData : wantGetStockDatas) {
            if (wantGetStockData.equals(StockDataTypes.ThsMinuteFunds)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public JSONArray decoratorData(JSONArray minutePriceData){
        if (minuteFundsData == null){return minutePriceData;}
        GuMinuteDataUtils.minuteDataCustomFunds(minutePriceData,minuteFundsData.getString("line").split("\\|"));
        return minutePriceData;
    }
    @Override
    public StringBuilder decoratorDataTableString(StringBuilder tableString){
        //分钟资金量
        tableString.append(minuteFundsData.getJSONObject("diff"));
        tableString.append(System.getProperty("line.separator"));
        JSONObject diff = new JSONObject();
        diff.put("ddje","大单净额"+minuteFundsData.getJSONObject("diff").get("ddje"));
        diff.put("zdje","中单净额"+minuteFundsData.getJSONObject("diff").get("zdje"));
        diff.put("xdje","小单净额"+minuteFundsData.getJSONObject("diff").get("xdje"));
        tableString.append(guInfo.getName()+"主力流入："+minuteFundsData.getJSONObject("dde").getString("zllx"));
        tableString.append(System.getProperty("line.separator"));
        tableString.append(guInfo.getName()+"资金占比："+diff);
        JSONObject diff2 = new JSONObject();
        diff2.put("ddjb","大单占比"+minuteFundsData.getJSONObject("diff").get("ddjb"));
        diff2.put("zdjb","中单占比"+minuteFundsData.getJSONObject("diff").get("zdjb"));
        diff2.put("xdjb","小单占比"+minuteFundsData.getJSONObject("diff").get("xdjb"));
        tableString.append(guInfo.getName()+"资金占比比例："+diff2);
        tableString.append(System.getProperty("line.separator"));
        return tableString;
    }

    /**
     * 获取实时资金数据
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
    private  JSONObject getMinuteFunds(@NotNull String stockCode){
        String r = HttpClientUtil.get("http://stockpage.10jqka.com.cn/spService/"+stockCode+"/Funds/lineFunds");
        JSONObject result = JSONObject.parseObject(r);
        return result;
    }

}
