package com.china.hcg.utils.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateRangeExample {

    public static List<String> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<String> dates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (!startDate.isAfter(endDate)) {
            dates.add(startDate.format(formatter));
            startDate = startDate.plusDays(1);
        }

        return dates;
    }

    public static void main(String[] args) {
        String startDateStr = "2023-01-01";
        String endDateStr = "2023-01-10";

        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        List<String> dateList = getDatesBetween(startDate, endDate);

        // 输出所有日期
        for (String date : dateList) {
            System.out.println(date);
        }
    }
}
