import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import utils.FileUtils;
import utils.StringUtils;
import utils.XmlUtils;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.*;

/**
 * @作者：
 */
public class StartReplaceModelsMGB {
    public static  Map<String,String> replaceParams = new HashMap<String, String>();
    static{
        //附：User user = new User(); 我把User 叫做 域对象类名, 把user 叫做 对象名 。
//        replaceParams.put("域对象类名","WrongImg");
//        replaceParams.put("对象名","wrongImg");
//
//        replaceParams.put("包名","com.china.hcg.eas");
//        replaceParams.put("表名","WRONG_IMG");
//
//        replaceParams.put("数据库链接地址","106.54.209.129:3306/eas?characterEncoding=utf8");
//        replaceParams.put("数据库账号","root");
//        replaceParams.put("数据库密码","A520131sjk");
        //???BaseResultMap生成不完整。text的不会生成
        replaceParams.put("域对象类名","SolicitOpinionProjectItemTask");
        replaceParams.put("对象名","solicitOpinionProjectItemTask");

        replaceParams.put("包名","com.rongji.egov.majorurger");
        replaceParams.put("表名","MAJOR_URGER_SOLICIT_OPINION_PROJECT_ITEM_TASK");

        replaceParams.put("数据库链接地址","192.168.210.186:3306/zjgy_real?characterEncoding=utf8");
        replaceParams.put("数据库账号","root");
        replaceParams.put("数据库密码","root");
    }
    public static void main(String[] args) throws Exception {
        String currentUserWorkingPath = System.getProperty("user.dir");

        //初始化
        List<File> deleteFiles = FileUtils.traverseFolder(currentUserWorkingPath+"/src/main/resources/MGB_Replace_Models_Results");
        for (File file : deleteFiles){
            file.delete();
        }
        // 复制并重命名模板文件
        List<File> files = FileUtils.traverseFolder(currentUserWorkingPath+"/src/main/resources/MGB_Replace_Models");
        for (File file : files){
            String fileName = file.getName();
            String newFileName = StringUtils.traverseMapForReplaceString(replaceParams,fileName);
            String filePathName = currentUserWorkingPath+"/src/main/resources/MGB_Replace_Models_Results/"+newFileName;
            FileUtils.copyFileUsingFileStreams(file,filePathName);
        }

        // 替换复制后的模板文件
        List<File> resultFiles = FileUtils.traverseFolder(currentUserWorkingPath+"/src/main/resources/MGB_Replace_Models_Results");
        File mapperXmlFile = null;
        File xmlConfigFile = null;//mbg 最终配置文件
        for (File file : resultFiles){
            String fileContent = FileUtils.readFileContent(file);
            String newContent = StringUtils.traverseMapForReplaceString(replaceParams,fileContent);
            FileUtils.writeToFile(file,newContent);
            if ("generatorConfig.xml".equals(file.getName())){
                xmlConfigFile = file;
            } else if (file.getName().contains("xml")){
                mapperXmlFile = file;
            }
        }
        // 用mbg生成mapper.xml
        try {
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
//            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//            InputStream is = classloader.getResourceAsStream("generatorConfig.xml");
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(xmlConfigFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 以mbg生成mapper.xml来替换复制后的xml模板
        String mbgXmlFilePath = null;
        File mbgXmlFile = null; // mbg自动生成的mybaits文件
        Element rootElm = XmlUtils.getRootElementFormXML(xmlConfigFile);
        //取得某节点下指定名称的所有节点并进行遍历.      
        Element contextElm = rootElm.element("context");
        List contextElmNodes = contextElm.elements();
        for (Iterator it = contextElmNodes.iterator(); it.hasNext();) {
            Element elm = (Element) it.next();
            //System.out.println(elm.getText());//节点内容
            //System.out.println(elm.getName()); // 节点名
            if ("sqlMapGenerator".equals(elm.getName())){
                String targetProject = elm.attributeValue("targetProject");
                String targetPackage = elm.attributeValue("targetPackage");
                String targetPackagePath = "";
                String[] strings = targetPackage.split("\\.");
                for (String s : strings) {
                    targetPackagePath = targetPackagePath + s+"/";
                }
                mbgXmlFilePath = currentUserWorkingPath+"/"+targetProject+"/"+targetPackagePath;
            }
            // 复制model类
            if ("javaModelGenerator".equals(elm.getName())){
                String targetProject = elm.attributeValue("targetProject");
                String targetPackage = elm.attributeValue("targetPackage");
                String targetPackagePath = "";
                String[] strings = targetPackage.split("\\.");
                for (String s : strings) {
                    targetPackagePath = targetPackagePath + s+"\\";
                }
                List<File> mbgfiles = FileUtils.traverseFolder(currentUserWorkingPath+"\\"+targetProject+"\\"+targetPackagePath);
                //todo：文件未找到提醒
                for (File file : mbgfiles){

                    if (file.getName().equals(replaceParams.get("域对象类名")+".java")){
                        FileUtils.copyFileUsingFileStreams(file,currentUserWorkingPath+"/src/main/resources/MGB_Replace_Models_Results"+"/"+file.getName());
                    }
                }
            }
        }
        List<File> mbgfiles = FileUtils.traverseFolder(mbgXmlFilePath);
        for (File file : mbgfiles){
            if (file.getName().contains("xml")){
                mbgXmlFile = file;
            }
        }

            //获取mbg生成mapper.xml的内容
        Element mbgXmlFileElm = XmlUtils.getRootElementFormXML(mbgXmlFile);
        List mbgXmlFileElms =  mbgXmlFileElm.elements();
        //Map<String,String> mbgXmlFileContent = new HashMap<String, String>();
        Map<String,Map<String,Object>> mbgXmlFileContent = new HashMap<String, Map<String,Object>>();
        for (Iterator it = mbgXmlFileElms.iterator(); it.hasNext();) {
            Element elm = (Element) it.next();
            String attributeValue = elm.attributeValue("id");
            System.out.println(attributeValue);
            if ("BaseResultMap".equals(attributeValue)){
                //String allContent = elm.asXML();
                //allContent = allContent.substring(allContent.indexOf(">")+1,allContent.lastIndexOf("<"));
                Map<String,Object> map = new HashMap<java.lang.String, Object>();
                map.put("text",elm.getText());
                map.put("elements",elm.content());
                mbgXmlFileContent.put("BaseResultMap",map);
            } else if ("Base_Column_List".equals(attributeValue)){
                Map<String,Object> map = new HashMap<java.lang.String, Object>();
                map.put("text",elm.getText());
                map.put("elements",elm.content());
                mbgXmlFileContent.put("Base_Column_List",map);
            } else if ("insertSelective".equals(attributeValue)){
                Map<String,Object> map = new HashMap<java.lang.String, Object>();
                map.put("text",elm.getText());
                map.put("elements",elm.content());
                mbgXmlFileContent.put("insertSelective",map);
            } else if ("updateByPrimaryKeySelective".equals(attributeValue)){
                Map<String,Object> map = new HashMap<java.lang.String, Object>();
                map.put("text",elm.getText());
                map.put("elements",elm.content());
                mbgXmlFileContent.put("updateByPrimaryKeySelective",map);
            }
        }
            //替换复制后的mapper模板文件内容
        Element modelCopyXmlFileElm = XmlUtils.getRootElementFormXML(mapperXmlFile);
        List modelCopyXmlFileElms =  modelCopyXmlFileElm.elements();
        for (Iterator it = modelCopyXmlFileElms.iterator(); it.hasNext();) {
            Element elm = (Element) it.next();
            String text = elm.getText();
            if (text.contains("以id为标识全替换")) {
                int start = text.indexOf("{");
                int end = text.lastIndexOf("}");
                String replaceIdMark = text.substring(start+1,end);
                if (mbgXmlFileContent.get(replaceIdMark) != null){
                    Map map = mbgXmlFileContent.get(replaceIdMark);
                    //elm.setContent((List) map.get("elements"));
                    //elm.setText((String) map.get("text"));
                    //elm.addText((String) map.get("text"));
                    elm.setContent((List) map.get("elements"));
                }
            }
        }
        System.out.println(modelCopyXmlFileElm.getDocument().asXML());
        if (XmlUtils.writeToFile(modelCopyXmlFileElm.getDocument(),mapperXmlFile)){
            System.out.println("mapper生成成功");
        } else {
            System.out.println("mapper生成失败");
        }
    }
}