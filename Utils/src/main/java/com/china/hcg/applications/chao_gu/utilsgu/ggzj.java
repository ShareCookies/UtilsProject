package com.china.hcg.applications.chao_gu.utilsgu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.china.hcg.applications.chao_gu.GuDayData;
import com.china.hcg.applications.chao_gu.model.GuInfo;
import com.china.hcg.http.HttpClientUtil;
import com.china.hcg.io.SerializableUtils;
import com.china.hcg.io.file.FileUtils;
import org.springframework.http.HttpHeaders;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor hecaigui
 * @date 2023/7/10
 * @description 个股资金
 */
public class ggzj {
    /*
股票代码分类:
1、创业板的代码是300打头。
2、沪市A股的代码是以600、601、603或605打头。
3、沪市B股的代码是以900打头。
4、科创板的代码是688打头。
5深市A股的代码是以000打头。
6、中小板的代码是002打头。
7、深圳B股的代码是以200打头
8、沪市新股申购的代码是以730打头，深市新股申购的代码与深市股票买卖代码一样.
9、配股代码，沪市以700打头，深市以080打头。
10、权证代码，沪市是580打头 深市是031打头.
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        start("5");
//        byte[] data = SerializableUtils.out(guInfoList);
//        FileUtils.writeToFile();
//        List<GuInfo> guInfoList2 = SerializableUtils.in(s.getBytes(StandardCharsets.UTF_8));
//        System.err.println(guInfoList2);
    }
    public static List<GuInfo> start(String page){
        List<GuInfo> guInfoList = new ArrayList<>();
        JSONArray values = ggzj.getRankingGGZJ(page,"200");
        for (Object value : values) {
            JSONObject jsonObject = (JSONObject)value;
            String symbol = jsonObject.getString("symbol");
            if (!symbol.contains("bj") ) {
                String symbol2 = symbol.substring(2,symbol.length());
                //30 300创业板股票//688科创板
                if (symbol2.startsWith("30") || symbol2.startsWith("688") ){
                    continue;
                }
                System.err.println(symbol2);
                guInfoList.add(new GuInfo(symbol2));
            }
        }
        return guInfoList;
    }
    //获取个股资金排行
    //http://data.10jqka.com.cn/funds/ggzjl/field/zdf/order/desc/page
    //涨跌幅 http://data.10jqka.com.cn/funds/ggzjl/field/zdf/order/desc/page/6/ajax/1/free/1/
    //换手率 http://data.10jqka.com.cn/funds/ggzjl/field/hsl/order/DESC/ajax/1/free/1/
    //流入资金 http://data.10jqka.com.cn/funds/ggzjl/field/flowin/order/DESC/ajax/1/free/1/
    //流出资金  http://data.10jqka.com.cn/funds/ggzjl/field/flowout/order/DESC/ajax/1/free/1/
//    private static JSONArray getRankingGGZJ(@NotNull String page){
//        HttpHeaders headers = new HttpHeaders();
//        headers.set(HttpHeaders.USER_AGENT,"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:9.0) Gecko/20100101 Firefox/9.0");
//        headers.set(HttpHeaders.COOKIE,"v=A1MAn_pRM9EvpP8FSnZh_rod4tx-COfKoZwr_gVwr3KphH2Kjdh3GrFsu08W");
//        String r = HttpClientUtil.getWithHeader("http://data.10jqka.com.cn/funds/ggzjl/field/zdf/order/desc/page/"+page+"/ajax/1/free/1/",headers);
////        String r = HttpClientUtil.getForHttpsAndCookie("http://data.10jqka.com.cn/funds/ggzjl/field/zdf/order/desc/page/"+page+"/ajax/1/free/1/");
//        return null;
//    }
    //http://vip.stock.finance.sina.com.cn/moneyflow/#sczjlx
    private static JSONArray getRankingGGZJ(@NotNull String page,String num){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT,"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:9.0) Gecko/20100101 Firefox/9.0");
        //headers.set(HttpHeaders.COOKIE,"v=A1MAn_pRM9EvpP8FSnZh_rod4tx-COfKoZwr_gVwr3KphH2Kjdh3GrFsu08W");
        String r = HttpClientUtil.getWithHeader("https://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/MoneyFlow.ssl_bkzj_ssggzj?page="+page+"&num="+num+"&sort=changeratio&asc=0&bankuai=&shichang=",headers);
//        String r = HttpClientUtil.getForHttpsAndCookie("http://data.10jqka.com.cn/funds/ggzjl/field/zdf/order/desc/page/"+page+"/ajax/1/free/1/");
        return JSONArray.parseArray(r);
    }
}
