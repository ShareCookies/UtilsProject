package com.china.hcg.utils.xml;

import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @autor hecaigui
 * @date 2021-10-11
 * @description
 */
public class test {
    public static void main(String[] args) throws Exception {
//        String xmlText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<doc-package Version=\"1.0\"><doc><id>YVV7QISu5_Cc4eff</id><app-no>gongwen</app-no><send-unit><id>1300B00</id><name>浙江省高级人民法院</name></send-unit><receive-unit><id>1300B00</id><name>浙江高院</name></receive-unit><clear-msg><type>OFC</type><content><subject>起草自动启用流程1</subject><doc-content>eyJhcHByb3ZhbENvbnRlbnQiOiIiLCJhcHByb3ZhbFJlc3VsdCI6IjExIiwiYXJjaGl2ZUZsYWciOiIwIiwiYXR0YWNoRGVzYyI6IiIsImJ1c2luZXNzUHVibGljU3RhdHVzIjoicHVibGljIiwiY29weVNlbmQiOltdLCJjdXNSb3dJbmZvIjpbeyJpZGVudGlmaWVyIjoiIiwiaHVpcWlhbk9waW5pb25zIjpbXSwic2hlbnBpT3BpbmlvbnMiOltdfV0sImRlYWxGb3JtSWQiOiJZVlEwQWcwUGxFUUM5WVlOIiwiZG9jQ2F0ZSI6IkZDMDAwMDMiLCJkb2NNYXJrIjoiIiwiZG9jV29yZCI6IuWFrOWHvSIsImRyYWZ0RGF0ZSI6MTYzMjg0NDgwMDAwMCwiZHJhZnREZXB0Ijoi5Yqe5YWs5a6kIiwiZHJhZnREZXB0Tm8iOiIxMzAwQjAwMDIxMCIsImRyYWZ0VW5pdCI6Iua1meaxn+ecgemrmOe6p+S6uuawkeazlemZoiIsImRyYWZ0VW5pdE5vIjoiMTMwMEIwMCIsImRyYWZ0VXNlck5hbWUiOiLlkJXmlY/mt5EiLCJkcmFmdFVzZXJObyI6IjEzMDBCMDAwODEyMTc4NDMiLCJlcnJvckZsYWciOiIwIiwiZmxvd0xhYmVsIjoic2djbGMiLCJmbG93UGlkIjoiWVZWVUw0U3VBTDNCTFk2ZSIsImZsb3dTdGF0dXMiOiIxIiwiZmxvd1ZlcnNpb24iOiJWMS4wLjMiLCJpZCI6IllWVlVKb1N1QUwzQkxZNmMiLCJpc1JlY29yZFRvU1JEIjoiIiwibWFpblNlbmQiOltdLCJtYWluU3ViamVjdCI6Iui1t+iNieiHquWKqOWQr+eUqOa1geeoizEiLCJwdWJsaWNTdGF0dXMiOiIwIiwicmVhZGVycyI6WyIxMzAwQjAwMDIxMCIsIjEzMDBCMDAwODEyMTc4NDMiXSwic2VjTGV2ZWwiOiLmma7pgJoiLCJzaWduRmxhZyI6IjAiLCJzdWJqZWN0Ijoi6LW36I2J6Ieq5Yqo5ZCv55So5rWB56iLMSIsInN5c3RlbU5vIjoiWkpHWS1PQSIsInRyYW5zZmVyU3RhdHVzIjoiMCIsInVyZ2VudExldmVsIjoi5pmu6YCaIn0=</doc-content><sec-level>普通</sec-level><urgency>普通</urgency><send-unit>浙江省高级人民法院</send-unit><receive-unit>浙江高院</receive-unit></content></clear-msg></doc><doc><id>YWOkfw0P9ruuOR-5</id><app-no>gongwen</app-no><send-unit><id>1300B00</id><name>浙江省高级人民法院</name></send-unit><receive-unit><id>1300B00</id><name>浙江高院</name></receive-unit><clear-msg><type>OFC</type><content><subject>分发测试21</subject><doc-content>eyJhcHByb3ZhbENvbnRlbnQiOiIiLCJhcHByb3ZhbFJlc3VsdCI6IjExIiwiYXJjaGl2ZUZsYWciOiIwIiwiYXR0YWNoRGVzYyI6IiIsImJ1c2luZXNzUHVibGljU3RhdHVzIjoicHVibGljIiwiY29weVNlbmQiOltdLCJjdXNSb3dJbmZvIjpbeyJpZGVudGlmaWVyIjoiIiwiaHVpcWlhbk9waW5pb25zIjpbXSwic2hlbnBpT3BpbmlvbnMiOltdfV0sImRlYWxGb3JtSWQiOiJZVlEwQWcwUGxFUUM5WVlOIiwiZG9jQ2F0ZSI6IkZDMDAwMDMiLCJkb2NNYXJrIjoiIiwiZG9jV29yZCI6IuWFrOWHvSIsImRyYWZ0RGF0ZSI6MTYzMjkzMTIwMDAwMCwiZHJhZnREZXB0Ijoi5Yqe5YWs5a6kIiwiZHJhZnREZXB0Tm8iOiIxMzAwQjAwMDIxMCIsImRyYWZ0VW5pdCI6Iua1meaxn+ecgemrmOe6p+S6uuawkeazlemZoiIsImRyYWZ0VW5pdE5vIjoiMTMwMEIwMCIsImRyYWZ0VXNlck5hbWUiOiLlkJXmlY/mt5EiLCJkcmFmdFVzZXJObyI6IjEzMDBCMDAwODEyMTc4NDMiLCJlcnJvckZsYWciOiIwIiwiZmxvd0xhYmVsIjoic2djbGMiLCJmbG93UGlkIjoiWVdPa2R3MFBtSTBCMGVsVyIsImZsb3dTdGF0dXMiOiIxIiwiZmxvd1ZlcnNpb24iOiJWMS4wLjMiLCJpZCI6IllWVlp2ZzBQQkZNZHh6U1MiLCJpc1JlY29yZFRvU1JEIjoiIiwibWFpblNlbmQiOltdLCJtYWluU3ViamVjdCI6IuWIhuWPkea1i+ivlTIxIiwicHVibGljU3RhdHVzIjoiMCIsInJlYWRlcnMiOlsiMTMwMEIwMDAyMTAiLCIxMzAwQjAwMDgxMjE3ODQzIl0sInNlY0xldmVsIjoi5pmu6YCaIiwic2lnbkZsYWciOiIwIiwic3ViamVjdCI6IuWIhuWPkea1i+ivlTIxIiwic3lzdGVtTm8iOiJaSkdZLU9BIiwidHJhbnNmZXJTdGF0dXMiOiIwIiwidXJnZW50TGV2ZWwiOiLmma7pgJoifQ==</doc-content><sec-level>普通</sec-level><urgency>普通</urgency><send-unit>浙江省高级人民法院</send-unit><receive-unit>浙江高院</receive-unit></content></clear-msg></doc></doc-package>";
//        Document document = DocumentHelper.parseText(xmlText);
//        Element docPackage = document.getRootElement();
//        List<Element> docs = docPackage.elements("doc");
//        int num = docs.size();
//        List<String> trackIdsList = new ArrayList<>();
//        for(int i = 0; i < num; ++i) {
//            Element doc = (Element) docs.get(i);
//            trackIdsList.add(doc.elementTextTrim("id"));
//        }
//        String trackIds = trackIdsList.stream().collect(Collectors.joining(";"));
//        System.err.println(trackIds);

        test.xmlToJsonTest();
    }

    static void xmlToJsonTest() throws Exception{
        String xml = "<data><signList><sign><orgCode>GO_75cf057cd156499eb8731aa922907243</orgCode><content>已经签收</content><person/><time>2022-05-26 16:54:52</time></sign></signList></data>";
        Document dom = DocumentHelper.parseText(xml);
        JSONObject jsonObject = new JSONObject();
        test.element2json(dom.getRootElement(), jsonObject);
        System.err.println(jsonObject);
    }
    /**
     * 递归函数xml转json 多层遍历
     * @param elmt 根元素
     * @param json 主键为当前元素的节点名,值为当前元素的所有直接子元素
     */
    public static void element2json(Element elmt, JSONObject json) {
        if (null == elmt) {
            return;
        }
        String name = elmt.getName();

        if (elmt.isTextOnly() && !name.equals("field")) {
            json.put(name, elmt.getText());
        } else if (name.equals("field")) {
            json.put(elmt.attributeValue("key"), elmt.attributeValue("value"));
        } else {
            JSONObject jsonSub = new JSONObject();
            List<Element> elements = (List<Element>) elmt.elements();
            for (Element elmtSub : elements) {
                element2json(elmtSub, jsonSub);
            }
            Object firstObj = json.get(name);
            boolean first = true;
            //只出现一次那么他就会是{}
            // 但某些又不是
            //所以这里对名称进行判断
            if (name.contains("List")){
                first = false;
            }

            if (first) {
                json.put(name, jsonSub);
            } else {
                if (firstObj instanceof List<?>) {
                    ((List) firstObj).add(jsonSub);
                } else {
                    List<Object> listSub = new ArrayList<Object>();
                    if (firstObj!=null){
                        listSub.add(firstObj);
                    }
                    listSub.add(jsonSub);
                    json.put(name, listSub);
                }
            }
        }
    }
}
