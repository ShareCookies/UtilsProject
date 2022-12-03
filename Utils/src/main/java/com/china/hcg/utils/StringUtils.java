/**
 * 
 */
package com.china.hcg.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 *
 */
public class StringUtils {
	// 字符串拼接
	//StringUtils.join();
	/**
	 * 生成唯一标识符
	 * @return
	 */
	public static String uuidString(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	/**
	 * 字符串转为二进制流
	 * @param string
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static ByteArrayInputStream toStream(String string) throws UnsupportedEncodingException {
		return new ByteArrayInputStream(string.getBytes("utf-8"));
	}
	/**
	 * byte[]转为字符串
	 * 		将byte转为String：
	 * 		可以使用 String 接收 byte[] 参数的构造器来进行转换，
	 * 		注：
	 * 		需要注意的点是要使用的正确的编码，否则会使用平台默认编码。
	 */
	 public static void byteArrayToString() throws UnsupportedEncodingException {

	 }
	/**
	 * 第一个字符大写
	 * @param fildeName
	 * @return
	 * @throws Exception
	 */
    public static String firstCapital(String fildeName) throws Exception{  
        byte[] items = fildeName.getBytes();  
        items[0] = (byte) ((char) items[0] - 'a' + 'A');  
        return new String(items);  
    }  
	/**
	 * 第一个字符小写
	 * @param fildeName
	 * @return
	 * @throws Exception
	 */
    public static String firstLower(String fildeName) throws Exception{  
        byte[] items = fildeName.getBytes();  
        items[0] = (byte) ((char) items[0]+32);  
        return new String(items);  
    }    
    /**
     * 获取文件后缀名
     * @param fileName
     * @return
     */
    public static String fileSuffix(String fileName) {
		String[] strings=fileName.split("\\.");
		if (strings.length>=2) return strings[strings.length-1];
    	return null;
	}
    /**
     * 获取文件前缀名
     * @param fileName
     * @return
     */
    public static String filePrefix(String fileName) {
		String[] strings=fileName.split("\\.");
		String name="";
		if (strings.length>=2) {
			for (int i = 0; i < strings.length-1; i++) {
				name+=strings[i];
			}
			return name;
		}
    	return null;
	}
    /**
     * 字符串为null或leng为0 返回true
     * @return
     */
    public static boolean isNull(String string) {
		if(string==null||string.length()==0)return true;
		return false;
	}
    /**
     * 子字符串在父字符串内，返回true。区分大小写
     * @param parentStr
     * @param sonStr
     * @return
     */
    public static Boolean isContain(String parentStr,String sonStr) {
    	if (parentStr==null||sonStr==null) {
			return null;
		}
        if(parentStr.indexOf(sonStr)!=-1){ 
        	//System.out.println("包含"); 
        	return true;
        }else{ 
        	//System.out.println("不包含"); 
        	return false;
        }     	
    }
    public static String delHTMLTag(String htmlStr){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
         
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤  
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 

        return htmlStr.trim(); //返回文本字符串 
    } 
    public static String delSpace(String htmlStr){ 
        htmlStr = htmlStr.replaceAll("\n", "");
        htmlStr = htmlStr.replaceAll("\t", "");
        return htmlStr.trim(); //返回文本字符串 
    }
	/*
	字符串截取：
		https://blog.csdn.net/zjx2016/article/details/74557301

		String sb = "bbbdsajj,d,s";
		1.
			sb.substring(2);//结果：bdsajj,d,s。从索引号为2开始截取，一直到字符串末尾
			sb.substring(2, 4);//结果：bd 。索引号2开始到索引好4结束
	字符串分割：
    	String sb = ",bbb,dsaj,j";
		String[]  strs=sb.split(",");//结果："" "bbb" "dsaj" "j"
		System.out.println(strs);
		将正则传入split()。返回的是一个字符串数组类型。不过通过这种方式截取会有很大的性能损耗，因为分析正则非常耗时。
		注：
			1.无法分割点，.是正则表达式里的一个关键字，
			如果没有经过转义split会把它当作一个正则表达式来处理的，
			所以str.split("\\.");
			2.sb为"",好像会切割为[]。 !
	字符串替换
		http://c.biancheng.net/view/836.html
		例：
			../正则.txt
			//根据map替换参数，来替换字符串 replaceParams.put("域对象类名","User");
			public static String traverseMapForReplaceString(Map<String,String> replaceParams,String sourceString ){
				String resultString = sourceString;
				for (String key : replaceParams.keySet()){
					resultString = resultString.replaceAll(key,replaceParams.get(key));
				}
				return resultString;
			}			
			
	字符串转字符数组：
	    char ss[] = "rgrgd rgre 444".toCharArray();//利用toCharArray方法转换
        for (int i = 0; i < ss.length; i++) {
            System.out.print(ss[i]);
        }

	String类中的intern()方法：！
		intern()方法会首先从常量池中查找是否存在该常量值，如果常量池中不存在则现在常量池中创建，如果已经存在则直接返回。
		例：
			String s1=”aa”; 
			String s2=s1.intern(); 
			System.out.print(s1==s2);//返回true			
	*/
	/*
	
	String和StringBuilder、StringBuffer的区别：
		Java平台提供了两种类型的字符串：String和StringBuffer/StringBuilder，它们均可以储存和操作字符串。
		String是常量字符串：
			String引用的字符串内容是不能被改变的。
			例：
				String str="abc";System.out.println(str);
				str=str+"de";System.out.println(str);
				该段代码会先输出“abc”，然后又输出“abcde”，好像是str这个对象被更改了，其实，这只是一种假象罢了。
				JVM对于这几行代码是这样处理的，首先创建一个String对象str，并把“abc”赋值给str，
				然后在第三行中，其实JVM又创建了一个新的对象也名为str，然后再把原来的str的值和“de”加起来再赋值给新的str。
				而原来的str就会被JVM的垃圾回收机制（GC）给回收掉了，所以，str实际上并没有被更改，也就是前面说的String对象一旦创建之后就不可更改了。
				所以，Java中对String对象进行的操作实际上是一个不断创建新的对象并且将旧的对象回收的一个过程，所以执行速度很慢。
		StringBuffer/StringBuilder是变量字符串：
			其表示的字符串对象可以直接进行修改。
			StringBuilder是Java 5中引入的，它和StringBuffer的方法完全相同，
			区别在于StringBuilder是在单线程环境下使用的，因为它的所有方法都没有被synchronized修饰，因此它的效率也比StringBuffer要高。
		总结：
	　　String：适用于少量的字符串操作的情况
	　　StringBuilder：适用于单线程下在字符缓冲区进行大量操作的情况
	　　StringBuffer：适用多线程下在字符缓冲区进行大量操作的情况	
	*/

	/**
	 * @description 判断字符串是否为数字
	 * <p>
	 *     https://www.cnblogs.com/xinruyi/p/11518617.html
	 * </p>
	 * @param str
	 * @return
	 */
	public static boolean isInt(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}
	/**
	 * @description 正则替换字符串，所有符合规则的部分为不同的值
	 * @author hecaigui
	 * @date 2020-11-13
	 * @param s 例："helD2lo java，helD8lo php"
	 * @param reg 例："D[0-9]"
	 * @return
	 */
	public static String regReplace(String target,String reg,String replaceWith){
		String words = target;
		String newStr = "未找到任何结果";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(words);
		while (matcher.find()){
			System.out.println(matcher.group());
			newStr = matcher.replaceFirst(replaceWith);
			matcher = pattern.matcher(newStr);
		}
		System.out.println(newStr);
		return newStr;
	}
	public static void main(String[] args)throws Exception{

	}
//    public static void main(String[] args){
//	    String userPos = "政治部主任 党组成员\n";
//	    String[]  strs = userPos.split(";");
////	    if (org.apache.commons.lang3.StringUtils.isNotBlank(userPos)){
////		    //党组成员，巡视员，副院长
////		    //政治部主任,党组成员
////		    userPos = userPos.replace(" ","");
////		    userPos = userPos.replace("\n","");
////		    userPos = userPos.replace(",",";");
////		    userPos = userPos.replace("，",";");
////		    if (userPos.contains(";")){
////			    String[]  strs = userPos.split(";");
////			    userPos = strs[strs.length - 1];
////		    }
////		    try {
////		    	UserLevel.valueOf(userPos).ordinal();
////		    }catch (IllegalArgumentException e){
////			    e.printStackTrace();
////			    System.out.println(userPos);
////			    System.out.println("请更新职务常量类");
////		    }
////	    }
//	    String sb = "bbbdsajj,d,s";
//	    System.err.println(sb.substring(0,sb.length()-1));
//	    System.err.println(userPos);
//
//
//
//    }


}
