//package com.china.hcg.excel;
//
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.ss.usermodel.BorderStyle;
//import org.apache.poi.ss.usermodel.HorizontalAlignment;
//import org.apache.poi.ss.usermodel.IndexedColors;
//import org.apache.poi.ss.usermodel.VerticalAlignment;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.OutputStream;
//import java.net.URLEncoder;
//import java.text.SimpleDateFormat;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * @autor hecaigui
// * @date 2020-12-25
// * @description
// */
//public class GenerateHSSFWorkbook {
//	@GetMapping("/urger/exportExcel")
//	public void exportExcel(UrgerExporExcel urgerExporExcel, HttpServletResponse response) throws Exception {
//		SecurityUser securityUser = SecurityUtils.getPrincipal();
//
//		Urger urger = urgerExporExcel.getUrger();
//		if (urger != null){
//			urger.setSystemNo(securityUser.getSystemNo());
//		}
//		Page<Urger> urgerPage = this.urgerMng.getDetailDistributedUrger(urgerExporExcel.getPaging(),urgerExporExcel.getUrger(),urgerExporExcel.getWord());
//		HashMap<String,String> map=new HashMap<>();
////        map.put("startDate",beginDate);
////        map.put("endDate",endDate);
////        map.put("subject",subject);
////        map.put("docMark",docMark);
////        map.put("sourceUnit",sourceUnit);
////        map.put("docCate",docCate);
////
//		String dateStr="";
//		String date1="";
//		String date2="今";
//		SimpleDateFormat simpleDate=new SimpleDateFormat("yyyy年MM月dd日");
//		if(urger.getBeginDate() != null){
//			date1=simpleDate.format(urger.getBeginDate());
//		}
//		if(urger.getEndDate() != null){
//			date2=simpleDate.format(urger.getEndDate());
//		}
//		dateStr=date1+"至"+date2;
//
//		List<String> exportFields = urgerExporExcel.getExportFields();
////        //生产excel
//		HSSFWorkbook wb = urgerMng.excelExport(dateStr, urgerPage.getList(),exportFields);
//
//		//输出excel
//		OutputStream output=null ;
//		try {
//			response.setContentType("application/octet-stream");
//			response.addHeader("Content-Disposition",
//					String.format("attachment;filename*=utf-8'zh_cn'%s", URLEncoder.encode("督查登记表.xls", "UTF-8")));
//			output=response.getOutputStream();
//			wb.write(output);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new BusinessException("导出失败");
//		}finally {
//			if(output!=null){
//				output.flush();
//				output.close();
//			}
//		}
//	}
//	@Override
//	public HSSFWorkbook excelExport(String dateStr, List<Urger> list, List<String> exportFields) {
//		//创建HSSFWorkbook对象(excel的文档对象)
//		HSSFWorkbook wb = new HSSFWorkbook();
//
//		//-----------------标题--单元格样式--------------------
//		HSSFCellStyle titleStyle = wb.createCellStyle();
//		//水平垂直居中
//		titleStyle.setAlignment(HorizontalAlignment.CENTER);
//		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//		//字体
//		HSSFFont titleFont = wb.createFont();
//		titleFont.setFontName("华文中宋");
//		titleFont.setFontHeightInPoints((short) 12);
//		titleFont.setBold(true);
//		titleStyle.setFont(titleFont);
//
//		//-----------------时间范围--单元格样式-----------------
//		HSSFCellStyle dateStyle = wb.createCellStyle();
//		dateStyle.setAlignment(HorizontalAlignment.CENTER);
//		dateStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//		//字体
//		HSSFFont dateFont = wb.createFont();
//		dateFont.setFontName("仿宋_GB2312");
//		dateFont.setFontHeightInPoints((short) 12);
//		dateStyle.setFont(dateFont);
//
//		//------------------字段--单元格样式-------------------
//		HSSFCellStyle fieldStyle = wb.createCellStyle();
//		fieldStyle.setAlignment(HorizontalAlignment.CENTER);
//		fieldStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//		fieldStyle.setBorderBottom(BorderStyle.THIN);
//		fieldStyle.setBottomBorderColor(IndexedColors.GREEN.getIndex());
//		fieldStyle.setBorderLeft(BorderStyle.THIN);
//		fieldStyle.setBorderTop(BorderStyle.THIN);
//		fieldStyle.setBorderRight(BorderStyle.THIN);
//		//字体
//		HSSFFont fieldFont = wb.createFont();
//		fieldFont.setFontName("黑体");
//		fieldFont.setFontHeightInPoints((short) 11);
//		fieldStyle.setFont(fieldFont);
//
//		//------------------字段内容--单元格样式-------------------
//		HSSFCellStyle contentStyle = wb.createCellStyle();
//		contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//		contentStyle.setBorderBottom(BorderStyle.THIN);
//		contentStyle.setBorderLeft(BorderStyle.THIN);
//		contentStyle.setBorderRight(BorderStyle.THIN);
//		//内容自动换行
//		contentStyle.setWrapText(true);
//		//字体
//		HSSFFont contentFont = wb.createFont();
//		contentFont.setFontName("仿宋_GB2312");
//		contentFont.setFontHeightInPoints((short) 11);
//		contentStyle.setFont(contentFont);
//
//		//建立新的sheet对象
//		HSSFSheet sheet=wb.createSheet("督查登记表");
//		//设置缺省行高\列宽
//		sheet.setDefaultRowHeightInPoints(40);
//		for (int i = 0; i < exportFields.size(); i++){
//			UrgerExportFields urgerExportFields = UrgerExportFields.valueOf(exportFields.get(i));
//			sheet.setColumnWidth(i,  urgerExportFields.getExcelCellsWidth());
//		}
////        sheet.setColumnWidth(0,  (int)((15 + 0.72) * 256));
////        sheet.setColumnWidth(1,  (int)((15 + 0.72) * 256));
//
//		//-----------------------在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
//		HSSFRow row1=sheet.createRow(0);
//		row1.setHeightInPoints((float) 17.25);
//		//创建单元格
//		HSSFCell titleCell=row1.createCell(0);
//		//标题单元格内容
//		titleCell.setCellValue("督查登记表");
//		//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
//		sheet.addMergedRegion(new CellRangeAddress(0,0,0,exportFields.size()-1));
//		//配置单元格样式
//		titleCell.setCellStyle(titleStyle);
//
//		//------------------------第二行
//		HSSFRow row2=sheet.createRow(1);
//		row2.setHeightInPoints((float)14.25);
//		HSSFCell dateCell=row2.createCell(0);
//		dateCell.setCellValue(dateStr);
//		sheet.addMergedRegion(new CellRangeAddress(1,1,0,exportFields.size()-1));
//		//行设置样式
//		dateCell.setCellStyle(dateStyle);
//
//		//------------------------在sheet里创建第三行
//		HSSFRow row3=sheet.createRow(2);
//		row3.setHeightInPoints((float)19);
//		//创建单元格并设置单元格内容
//		for (int i = 0; i < exportFields.size(); i++){
//			UrgerExportFields urgerExportFields = UrgerExportFields.valueOf(exportFields.get(i));
//			HSSFCell field1=row3.createCell(i);
//			field1.setCellValue(urgerExportFields.getExportName());
//			field1.setCellStyle(fieldStyle);
//		}
//
//
//		//在sheet里创建第四行
//		HSSFRow dataRow;
//		Urger receival;
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		for (int i = 0; i < list.size(); i++) {
//			Urger urger = list.get(i);
//			dataRow=sheet.createRow(i+3);
//			//根据需要导出对应字段内容
//			// 应用了策略模式实现改需求。根据不同的需求，同一个方法能表现出不同的行为
//			for (int j = 0; j < exportFields.size(); j++){
//				UrgerExportFields urgerExportFields = UrgerExportFields.valueOf(exportFields.get(j));
//				urgerExportFields.setDocOpinionMng(docOpinionMng);
//				HSSFCell field1=dataRow.createCell(j);
//				field1.setCellValue(urgerExportFields.getExcelExportContentFromUrger(urger));
//				field1.setCellStyle(contentStyle);
//			}
//
////            HSSFCell content1=dataRow.createCell(0);
////            content1.setCellValue(formatter.format(receival.getDraftDate()));
////            content1.setCellStyle(contentStyle);
////
////            HSSFCell content2=dataRow.createCell(1);
////            content2.setCellValue(receival.getSourceUnit());
////            content2.setCellStyle(contentStyle);
////
//		}
//
//		return wb;
//	}
//}
