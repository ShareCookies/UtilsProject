package com.china.hcg.utils.poi.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @autor hecaigui
 * @date 2020-12-25
 * @description
 */
public class ExportExcelController {
	/**
	 * @description
	 * 控制层方法参数加上 HttpServletResponse response，生成好excel的内容HSSFWorkbook，调用改方法即可返回文件给前端。
	 * @author hecaigui
	 * @date 2020-12-25
	 * @param wb
	 * @param response
	 * @return 文件流
	 */
	public static void exportExcel(String fileName,HSSFWorkbook wb, HttpServletResponse response) throws Exception {
		if (StringUtils.isBlank(fileName)){
			fileName = "excel表.xls";
		}
		//输出excel
		OutputStream output=null ;
		try {
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition",
					String.format("attachment;filename*=utf-8'zh_cn'%s", URLEncoder.encode(fileName, "UTF-8")));
			output=response.getOutputStream();
			wb.write(output);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("导出失败");
		} finally {
			if(output!=null){
				output.flush();
				output.close();
			}
		}
	}
}
