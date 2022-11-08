package com.china.hcg.applications.chao_gu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.china.hcg.applications.chao_gu.model.GuInfo;
import com.china.hcg.applications.chao_gu.utilsgu.GuDataUtils;
import com.china.hcg.utils.timer.JavaTimer;
import com.china.hcg.utils.windows.InfoUtil;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @autor hecaigui
 * @date 2022-10-21
 * @description
 */
public class AutoGuMinuteData {


    ExecutorService executorService = Executors.newSingleThreadExecutor();
    public static void main(String[] args) throws Exception{
        List<GuInfo> list = new ArrayList();
//        list.add(new GuInfo("000725","京东方a",5 * GuDataUtils.oneHundredMillion));
//        list.add(new GuInfo("002027","分众传媒",2 * GuDataUtils.oneHundredMillion));
//        list.add(new GuInfo("000776","广发证券",4 * GuDataUtils.oneHundredMillion));
        list.add(new GuInfo("600383","金地集团",10 * GuDataUtils.oneHundredMillion,null,8f));
        for (GuInfo s : list) {
            minuteGuInfoLoop(s.code,s.name,s.totalAmountReference,s);
        }
    }

    static void minuteGuInfoLoop(String code,String name,double largeTotalAmountReference,GuInfo s) throws Exception{
        JavaTimer javaTimer = new JavaTimer();
        JavaTimer.loopTask(new Runnable() {
            @Override
            public void run() {
                //开始条件判断
//                Calendar rightCalendar = Calendar.getInstance();
//                int hour = rightCalendar.get(Calendar.HOUR_OF_DAY);
//                if (hour < 9 || hour > 15){
//                    return;
//                }


                JSONArray minuteData = GuMinuteData.getMinuteData(code);
                s.priceNotice(minuteData);

                int largeTotalAmount = 0;
                int totalAmount = 0;
                //提醒参照值
                double largelargeTotalAmountReference = largeTotalAmountReference;
                for (int i = 0; i < minuteData.size(); i++) {
                    JSONObject minute_data_price = minuteData.getJSONObject(i);
                    float amountValue = GuDataUtils.minuteComputeAmountValue(minute_data_price);
                    //原有数据改造
                    GuDataUtils.minuteDataCustomZhangDieLiang(minuteData,i);

                    totalAmount += amountValue;

                    if (amountValue > 5000000){
                        largeTotalAmount += amountValue;
                        //大额提醒
                        //todo：参照值自动新增
                        if (largeTotalAmount > largelargeTotalAmountReference){
                            largelargeTotalAmountReference += 10 * GuDataUtils.tenMillion;
                            InfoUtil test = new InfoUtil();
                            test.show(name+"大额交易超过亿", "达到"+(double) largeTotalAmount / GuDataUtils.oneHundredMillion + "亿;"+InfoUtil.breakLine+"    "+"总额"+ totalAmount / GuDataUtils.oneHundredMillion + "亿");
                        }
                    }
                }

            }
        },60L);
    }

    //单独一个提示线程
    void independentShow(){

//        InfoUtil test = new InfoUtil();
//        test.show(name+"    大额交易超过亿", "达到"+largeTotalAmount / 10000 + "万");
    }
}
