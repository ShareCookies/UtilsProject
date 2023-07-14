package com.china.hcg.applications.chao_gu;

import com.china.hcg.applications.chao_gu.model.GuInfo;
import com.china.hcg.applications.chao_gu.utilsgu.ggzj;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor hecaigui
 * @date 2023/7/13
 * @description
 */
public class GuDayData2 {
    public static void main(String[] args) {
        List<GuInfo> list = ggzj.start("4");
        GuDayData.printLatestTwoDay(list);
    }
}
