package com.china.hcg.applications.chao_gu;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.china.hcg.applications.chao_gu.utilscommon.TextTable;
import com.china.hcg.applications.chao_gu.utilscommon.TextTableExpand;
import com.china.hcg.applications.chao_gu.utilsgu.GuMinuteDataUtils;
import com.china.hcg.http.HttpClientUtil;
import com.china.hcg.http.utils.HttpUtil;
import com.china.hcg.utils.date.DateUtil;
import com.sun.deploy.net.HttpUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @autor hecaigui
 * @date 2023-11-18
 * @description
 */
public class GuHistoryDayData {
    public static void main(String[] args) throws ParseException {
        try {

            //https://finance.sina.com.cn/realstock/company/sz000066/hisdata/2023/10.js?d=2023-10-31
            String gu = "sh600520";
            String date = "2024-01-02";
            String url = "https://finance.sina.com.cn/realstock/company/"+gu+"/hisdata/"+date.substring(0,4)+"/"+date.substring(5,7)+".js?d="+date;
            String r = HttpClientUtil.getForHttpsAndCookie(url);
            r = r.split("\"")[1];
            for (String s : r.split(",")) {
                Object result = executeJavaScriptFunction("D:\\heSpace\\mySelf\\UtilsProject\\Utils\\src\\main\\java\\com\\china\\hcg\\applications\\chao_gu\\utilsgu\\XinLangDayHistoryDecodeUtil.js", "decode",s);


                JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(result));
                JSONArray jsonArray = new JSONArray();


                JSONObject j0 = jsonObject.getJSONObject("0");
                BigDecimal standPrevclose = j0.getBigDecimal("prevclose");
                Date standDate = new Date(j0.getBigDecimal("date").longValue());
                standDate.setHours(9);
                standDate.setMinutes(30);
                j0.put("date",DateUtil.dateToString(standDate,"yyyy-MM-dd HH:mm"));

                for (int i = 1;i<=242;i++) {
                    String s1 = String.valueOf(i);
                    JSONObject jsonObject1 = jsonObject.getJSONObject(s1);




                    BigDecimal ratio = jsonObject1.getBigDecimal("price").subtract(standPrevclose).divide(standPrevclose,4,BigDecimal.ROUND_HALF_UP);
                    jsonObject1.put("ratio",ratio.multiply(new BigDecimal(100)).toPlainString().substring(0,4) +"%");

                    if (i == 1){
                        jsonObject1.put("date",j0.getString("date"));
                    } else if (jsonObject1.get("date") == null) {
                        jsonObject1.put("date",DateUtil.dateToString(standDate,"yyyy-MM-dd HH:mm"));
                    }
                    if (i == 121){standDate.setHours(13);standDate.setMinutes(0);}
                    else {

                        // 时间上加1分钟
                        standDate.setTime(standDate.getTime()+ 60000);
                    }


                    if (jsonObject1.get("prevclose") == null) jsonObject1.put("prevclose","");


                    BigDecimal volumePrice = jsonObject1.getBigDecimal("volume").multiply(jsonObject1.getBigDecimal("avg_price"));
                    jsonObject1.put("amount",volumePrice.divide(new BigDecimal(GuMinuteDataUtils.tenThousand),1,BigDecimal.ROUND_HALF_UP).toPlainString() +"万;");

                    BigDecimal w = jsonObject1.getBigDecimal("volume").divide(new BigDecimal(GuMinuteDataUtils.tenThousand),1,BigDecimal.ROUND_HALF_UP);
                    String volume;
                    if (w.longValue() >10000){
                        volume = jsonObject1.getBigDecimal("volume").divide(new BigDecimal(10),1,BigDecimal.ROUND_HALF_UP).toPlainString() + "亿股";
                    } else {
                        volume = w.toPlainString()+"万股";
                    }
                    jsonObject1.put("volume",volume);
                    for (String s2 : jsonObject1.keySet()) {
                        if (jsonObject1.get(s2) instanceof BigDecimal) jsonObject1.put(s2,jsonObject1.getBigDecimal(s2).toPlainString());
                    }


                    jsonArray.add(jsonObject.get(s1));
                }
                TextTable textTable = TextTableExpand.standardJsonArrayTextTable(jsonArray);
//                System.out.println(JSON.toJSONString(result));
                System.out.println(textTable.printTable());
            }
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public static Object executeJavaScriptFunction(String scriptPath, String functionName,Object... args) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        try {
            // 读取 JavaScript 文件
            engine.eval(Files.newBufferedReader(Paths.get(scriptPath), StandardCharsets.UTF_8));

            // 调用 JavaScript 函数
            if (engine instanceof Invocable) {
                Invocable invocable = (Invocable) engine;
                Object object = invocable.invokeFunction(functionName,args);
                return object;
            } else {
                throw new ScriptException("Invoking methods is not supported.");
            }
        } catch (IOException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}
