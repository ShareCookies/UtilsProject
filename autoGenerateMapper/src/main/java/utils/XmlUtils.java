package utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @autor hecaigui
 * @date 2020-3-12
 * @description
 */
public class XmlUtils {

    /**
     * @description  读取XML文档
     * 读取指定的xml文件之后返回一个Document对象，这个对象代表了整个XML文档，用于各种Dom运算。
     * 执照XML文件头所定义的编码来转换。
     */
    public static Document loadXmlFile(File file) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(file); // 读取XML文件,获得document对象
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }

    /**
     * @description 获取xml根节点
     * @see XmlUtils#loadXmlFile(File)
     * 根节点是xml分析的开始，任何xml分析工作都需要从根开始
     */
    public static Element getRootElementFormXML(File file) {
        Document document = XmlUtils.loadXmlFile(file);
        return document.getRootElement();
    }

    /**
     * @description 写入文件
     *
     */
    public static boolean writeToFile(Document document , File file) {
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setTrimText(false);//设置text中是否要删除其中多余的空格
        format.setEncoding("UTF-8");// 指定XML编码
        try {
            XMLWriter writer = new XMLWriter(new FileWriter(file),format);
            writer.write(document);
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
