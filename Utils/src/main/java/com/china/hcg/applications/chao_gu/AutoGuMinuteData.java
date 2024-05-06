package com.china.hcg.applications.chao_gu;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.china.hcg.applications.chao_gu.dao.stockdata.StockDataTypes;
import com.china.hcg.applications.chao_gu.model.GuInfo;
import com.china.hcg.applications.chao_gu.utilsgu.GuMinuteDataNoticeUtils;
import com.china.hcg.applications.chao_gu.utilsgu.GuMinuteDataUtils;
import com.china.hcg.utils.date.DateUtil;
import com.china.hcg.utils.timer.JavaTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @autor hecaigui
 * @date 2022-10-21
 * @description
 */
public class AutoGuMinuteData {
    private static final Logger logger= LoggerFactory.getLogger(AutoGuMinuteData.class);

    public static void main(String[] args) throws Exception{
        JavaTimer.loopTask(() ->{
            Calendar rightCalendar = Calendar.getInstance();//获取当前地区的日期信息
            int hour = rightCalendar.get(Calendar.HOUR_OF_DAY);
//            if (!(hour >= 9 && hour < 15)){
//                System.err.println("退出");
//                return;
//            }
            try {
                Map<String,GuInfo> guInfoMap = new HashMap();

//                guInfoMap.put("002456",new GuInfo("002456","10.16","sz",null,null));//36.725*0.04  35.26
//                guInfoMap.put("603083",new GuInfo("603083"," 41.2","sz",41.5f,40.77f));//36.725*0.04  35.26
                guInfoMap.put("000818",new GuInfo("000818","5 风险高-平行加仓","sz",null,100d));//36.725*0.04  35.26
                guInfoMap.put("600072",new GuInfo("600072","5 风险高-平行加仓","sz",17.43f,100d));//36.725*0.04  35.26

                List<GuInfo> list = new ArrayList();
                for (String s : guInfoMap.keySet()) {
                    list.add(guInfoMap.get(s));
                }
                List<Map<String,String>> minute_data_price = GuMinuteData.printLatestMinuteGuInfo(list);
                for (Map<String, String> map : minute_data_price) {
                    //价格提醒
                    GuMinuteDataNoticeUtils guMinuteDataNoticeUtils = new GuMinuteDataNoticeUtils(guInfoMap.get(map.get("guCode")));
                    guMinuteDataNoticeUtils.priceNotice(Float.valueOf(map.get("price")));
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        },10L);
    }
    void autoNotice() throws Exception {
        List<GuInfo> list = new ArrayList();
        //list.add(new GuInfo("000792","盐湖股份",5 * GuMinuteDataUtils.oneHundredMillion,25.2F,24.95F));
        for (GuInfo guInfo : list) {
            minuteGuInfoLoop(guInfo);
        }
    }



    static void minuteGuInfoLoop(GuInfo guInfo) throws Exception{
        JavaTimer.loopTask(new Runnable() {
            @Override
            public void run() {
                //开始条件判断
//                Calendar rightCalendar = Calendar.getInstance();
//                int hour = rightCalendar.get(Calendar.HOUR_OF_DAY);
//                if (hour < 9 || hour > 15){
//                    return;
//                }
                //todo：有报错要照样打印啊
                try {
                    logger.error("定时器"+DateUtil.dateToString(new Date(System.currentTimeMillis()),"yyyy-MM-dd HH:mm:ss"));
                    GuMinuteData.MinuteData minuteDataObj = GuMinuteData.minuteData(guInfo,new StockDataTypes[]{StockDataTypes.MinuteFundDirections,StockDataTypes.ThsMinuteFunds});
                    JSONArray minuteData = minuteDataObj.minutePriceData;
                    GuMinuteDataNoticeUtils guMinuteDataNoticeUtils = new GuMinuteDataNoticeUtils(guInfo);
                    guMinuteDataNoticeUtils.priceNotice(minuteData);
                    guMinuteDataNoticeUtils.totalAmountNotice(minuteData);
                    guMinuteDataNoticeUtils.largeFundsFlowNotice(minuteData);
                } catch (Exception | Error e){
                    e.printStackTrace();
                }
            }
        },60L);
    }


}
