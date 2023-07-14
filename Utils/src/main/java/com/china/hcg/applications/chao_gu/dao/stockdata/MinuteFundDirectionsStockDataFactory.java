package com.china.hcg.applications.chao_gu.dao.stockdata;

import com.alibaba.fastjson.JSONObject;
import com.china.hcg.applications.chao_gu.model.GuInfo;
import com.china.hcg.applications.chao_gu.utilscommon.StockThreadPoolUtil;
import com.china.hcg.http.HttpClientUtil;

import javax.validation.constraints.NotNull;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @autor hecaigui
 * @date 2022-12-9
 * @description 分钟资金流量工厂
 */
public class MinuteFundDirectionsStockDataFactory extends AbstractStockDataFactory {
    JSONObject minuteFundDirectionData;
    Future<JSONObject> future = null;

    public MinuteFundDirectionsStockDataFactory(GuInfo guInfo) {
        super(guInfo);
        asyncRequest(guInfo.getCode());
    }


    public static boolean isSupport(StockDataTypes[] wantGetStockDatas) {
        if (wantGetStockDatas == null){
            return false;
        }
        for (StockDataTypes wantGetStockData : wantGetStockDatas) {
            if (wantGetStockData.equals(StockDataTypes.MinuteFundDirections)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void asyncRequest(String stockCode) {
        Future<JSONObject> future = StockThreadPoolUtil.executorService.submit(new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                return getMinuteFundsDirection(stockCode);
            }
        });
        this.future = future;
    }
    @Override
    public Object getAsyncRequestResult() {
        try {
            minuteFundDirectionData = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public StringBuilder decoratorDataTableString(StringBuilder tableString){
        //资金流势
        //stringBuilder.append(guName+"流入流出详细："+minuteFundDirectionData);
        //stringBuilder.append(System.getProperty("line.separator"));
        tableString.append(guInfo.getName()+"总流入流出"+minuteFundDirectionData.getJSONObject("title"));
        tableString.append(System.getProperty("line.separator"));

        tableString.append(guInfo.getName()+"流入流出详细：");
        JSONObject lrlcxq = new JSONObject();
        for (Object object : minuteFundDirectionData.getJSONArray("flash")) {
            JSONObject jsonObject =  (JSONObject)object;
            lrlcxq.put(jsonObject.getString("name"),jsonObject.getString("sr"));
        }
        tableString.append(lrlcxq);
        tableString.append(System.getProperty("line.separator"));
        return tableString;
    }

    /**
     * 获取实时资金流向数据
     * @date 2022-11-7
     * @return
     */
    private static JSONObject getMinuteFundsDirection(@NotNull String stockCode){
        String r = HttpClientUtil.get("http://stockpage.10jqka.com.cn/spService/"+stockCode+"/Funds/realFunds");
        JSONObject result = JSONObject.parseObject(r);
        return result;
    }

}
