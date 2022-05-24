package com.china.hcg.utils.poi.excel.export;

/**
 * @autor hecaigui
 * @date 2021-7-2
 * @description
 */
public interface ExportFields {
    /**
     * @description 获取excel导出列名
     */
    public String getExportName();
    /**
     * @description 获取excel导出列宽
     */
    default public int getExcelCellsWidth(){
        return (int)((15 + 0.72) * 256);
    }

}
