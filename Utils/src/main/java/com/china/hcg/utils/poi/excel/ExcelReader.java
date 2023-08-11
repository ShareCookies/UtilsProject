package com.china.hcg.utils.poi.excel;

/**
 * @autor hecaigui
 * @date 2020-9-14
 * @description
 */


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * https://www.cnblogs.com/Dreamer-1/p/10469430.html
 * Author: Dreamer-1
 * Date: 2019-03-01
 * Time: 10:21
 * Description: 读取Excel内容
 * <p>
 *
 * </p>
 */
public class ExcelReader {

	private static Logger logger = Logger.getLogger(ExcelReader.class.getName()); // 日志打印类

	private static final String XLS = "xls";
	private static final String XLSX = "xlsx";


	public static void main(String[] args) {
		List<List<String>> result = ExcelReader.readExcel("C:\\Users\\Administrator\\Desktop\\test.xlsx");
	}
	/**
	 * 读取Excel文件内容
	 * @param fileName 要读取的Excel文件所在路径
	 * @return 读取结果列表（结果省略第一行内容），读取失败时返回null
	 */
	public static List<List<String>> readExcel(String fileName) {

		Workbook workbook = null;
		FileInputStream inputStream = null;

		try {
			// 获取Excel后缀名
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			// 获取Excel文件
			File excelFile = new File(fileName);
			if (!excelFile.exists()) {
				logger.warn("指定的Excel文件不存在！");
				return null;
			}

			// 获取Excel工作簿
			inputStream = new FileInputStream(excelFile);
			workbook = getWorkbook(inputStream, fileType);

			// 读取excel中的数据
			List<List<String>> resultDataList = parseExcel(workbook);

			return resultDataList;
		} catch (Exception e) {
			logger.warn("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());
			return null;
		} finally {
			try {
				if (null != workbook) {
					workbook.close();
				}
				if (null != inputStream) {
					inputStream.close();
				}
			} catch (Exception e) {
				logger.warn("关闭数据流出错！错误信息：" + e.getMessage());
				return null;
			}
		}
	}
	/**
	 * @description 读取excel流文件内容
	 * @author hecaigui
	 * @date 2020-9-14
	 * @param inputStream 文件流
	 * @param fileType  文件后缀
	 * @return 读取结果列表（结果省略第一行内容），读取失败时返回null
	 */
	public static List<List<String>> readExcel(InputStream inputStream, String fileType) {
		List<List<String>> resultDataList = null;
		Workbook workbook = null;
		try {
			workbook = getWorkbook(inputStream, fileType);
			// 读取excel中的数据
			resultDataList = parseExcel(workbook);
		} catch (IOException ioException){
			ioException.printStackTrace();
		} finally {
			try {
				if (null != workbook) {
					workbook.close();
				}
				if (null != inputStream) {
					inputStream.close();//外部传进来的流，这里有必要手动关闭吗
				}
			} catch (Exception e) {
				logger.warn("关闭数据流出错！错误信息：" + e.getMessage());
				return null;
			}
		}
		return resultDataList;
	}
	/**
	 * 根据文件后缀名类型获取对应的工作簿对象
	 * @param inputStream 读取文件的输入流
	 * @param fileType 文件后缀名类型（xls或xlsx）
	 * @return 包含文件数据的工作簿对象
	 * @throws IOException
	 */
	public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
		Workbook workbook = null;
		if (fileType.equalsIgnoreCase(XLS)) {
			workbook = new HSSFWorkbook(inputStream);
		} else if (fileType.equalsIgnoreCase(XLSX)) {
			workbook = new XSSFWorkbook(inputStream);
		}
		return workbook;
	}
	/**
	 * 解析Excel数据
	 * @param workbook Excel工作簿对象
	 * @return 解析结果
	 */
	private static List<List<String>> parseExcel(Workbook workbook) {
		List<List<String>> resultDataList = new ArrayList<>();
		// 解析sheet
		for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
			Sheet sheet = workbook.getSheetAt(sheetNum);

			// 校验sheet是否合法
			if (sheet == null) {
				continue;
			}

			// 获取第一行数据
			int firstRowNum = sheet.getFirstRowNum();
			Row firstRow = sheet.getRow(firstRowNum);
			if (null == firstRow) {
				logger.warn("解析Excel失败，在第一行没有读取到任何数据！");
			}

			// 解析每一行的数据，构造数据对象
			int rowStart = firstRowNum + 1;
			int rowEnd = sheet.getPhysicalNumberOfRows();
			for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
				Row row = sheet.getRow(rowNum);

				if (null == row) {
					continue;
				}

				List<String> resultData = convertRowToData(row);
				if (null == resultData) {
					logger.warn("第 " + row.getRowNum() + "行数据不合法，已忽略！");
					continue;
				}
				resultDataList.add(resultData);
			}
		}

		return resultDataList;
	}

	/**
	 * 将单元格内容转换为字符串
	 * @param cell
	 * @return
	 */
	private static String convertCellValueToString(Cell cell) {
		if(cell==null){
			return null;
		}
		String returnValue = null;
		switch (cell.getCellType()) {
			case NUMERIC:   //数字
				Double doubleValue = cell.getNumericCellValue();

				// 格式化科学计数法，取一位整数
				DecimalFormat df = new DecimalFormat("0");
				returnValue = df.format(doubleValue);
				break;
			case STRING:    //字符串
				returnValue = cell.getStringCellValue();
				break;
			case BOOLEAN:   //布尔
				Boolean booleanValue = cell.getBooleanCellValue();
				returnValue = booleanValue.toString();
				break;
			case BLANK:     // 空值
				break;
			case FORMULA:   // 公式
				returnValue = cell.getCellFormula();
				break;
			case ERROR:     // 故障
				break;
			default:
				break;
		}
		return returnValue;
	}

	/**
	 * 提取每一行中需要的数据，构造成为一个结果数据对象
	 *
	 * 当该行中有单元格的数据为空或不合法时，忽略该行的数据
	 *
	 * @param row 行数据
	 * @return 解析后的行数据对象，行数据错误时返回null
	 */
	private static List<String> convertRowToData(Row row) {
		List<String> columnData = new ArrayList<String>();
		Cell cell;
		int cellNum = 0;

		Iterator<Cell> iterable = row.cellIterator();
		while (iterable.hasNext()){
			cell = iterable.next();
			columnData.add(convertCellValueToString(cell));
		}
		return columnData;
	}


}
