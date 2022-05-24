//package com.china.hcg.pdf;
//
//import com.openhtmltopdf.extend.FSSupplier;
//import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
//import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
//import info.monitorenter.cpdetector.io.*;
//import org.jsoup.Jsoup;
//import org.jsoup.helper.W3CDom;
//import org.jsoup.nodes.Document;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.io.ClassPathResource;
//
//import java.io.*;
//import java.nio.charset.Charset;
//
//import static java.awt.SystemColor.info;
//
//
//public class HtmlToPdfUtil {
//    public static final Logger logger = LoggerFactory.getLogger(HtmlToPdfUtil.class);
//    public static byte[] html2Pdf(String html) throws Exception {
//        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
//
//            final ClassPathResource msyh = new ClassPathResource("fonts/msyh.ttf");
//
//            final ClassPathResource msyhbd = new ClassPathResource("fonts/msyhbd.ttf");
//            PdfRendererBuilder builder = new PdfRendererBuilder();
//
//            builder.useFastMode();
//
//            FSSupplier<InputStream> msyhFSSupplier = new FSSupplier<InputStream>() {
//                @Override
//                public InputStream supply() {
//                    try {
//                        return msyh.getInputStream();
//                    } catch (IOException e) {
//                        logger.warn("读取字体文件出错", e);
//                    }
//                    return null;
//                }
//            };
//
//            builder.useFont(msyhFSSupplier, "Microsoft YaHei", 400, BaseRendererBuilder.FontStyle.NORMAL, true);
//            builder.useFont(msyhFSSupplier, "微软雅黑", 400, BaseRendererBuilder.FontStyle.NORMAL, true);
//
//            FSSupplier<InputStream> msyhbdFSSupplier = new FSSupplier<InputStream>() {
//                @Override
//                public InputStream supply() {
//                    try {
//                        return msyhbd.getInputStream();
//                    } catch (IOException e) {
//                        logger.warn("读取字体文件出错", e);
//                    }
//                    return null;
//                }
//            };
//
//            builder.useFont(msyhbdFSSupplier, "Microsoft YaHei", 700, BaseRendererBuilder.FontStyle.NORMAL, true);
//            builder.useFont(msyhbdFSSupplier, "微软雅黑", 700, BaseRendererBuilder.FontStyle.NORMAL, true);
//            Document document = Jsoup.parse(html);
//            // 移除o:p标签
//            document.select("o|p").remove();
//            builder.withW3cDocument(new W3CDom().fromJsoup(document), "");
//
//            builder.toStream(bos);
//            builder.run();
//            return bos.toByteArray();
//        }
//    }
//    public static String getCharCode(String filePath) {
//        String result = "utf-8";
//
//			/*------------------------------------------------------------------------
//
//
//			  detector是探测器，它把探测任务交给具体的探测实现类的实例完成。
//
//			  cpDetector内置了一些常用的探测实现类，这些探测实现类的实例可以通过add方法
//
//			  加进来，如ParsingDetector、 JChardetFacade、ASCIIDetector、UnicodeDetector。
//
//			  detector按照“谁最先返回非空的探测结果，就以该结果为准”的原则返回探测到的
//
//			  字符集编码。
//
//			--------------------------------------------------------------------------*/
//
//        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
//
//			/*-------------------------------------------------------------------------
//
//			  ParsingDetector可用于检查HTML、XML等文件或字符流的编码,构造方法中的参数用于
//
//			  指示是否显示探测过程的详细信息，为false不显示。
//
//			---------------------------------------------------------------------------*/
//
//        detector.add(new ParsingDetector(false));// 如果不希望判断xml的encoding，而是要判断该xml文件的编码，则可以注释掉
//
//			/*--------------------------------------------------------------------------
//
//			   JChardetFacade封装了由Mozilla组织提供的JChardet，它可以完成大多数文件的编码
//
//			   测定。所以，一般有了这个探测器就可满足大多数项目的要求，如果你还不放心，可以
//
//			   再多加几个探测器，比如下面的ASCIIDetector、UnicodeDetector等。
//
//			  ---------------------------------------------------------------------------*/
//
//        detector.add(JChardetFacade.getInstance());
//
//        // ASCIIDetector用于ASCII编码测定
//
//        detector.add(ASCIIDetector.getInstance());
//
//        // UnicodeDetector用于Unicode家族编码的测定
//
//        detector.add(UnicodeDetector.getInstance());
//
//        Charset charset = null;
//
//        File f = new File(filePath);
//
//        try {
//
//            charset = detector.detectCodepage(f.toURL());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        if (charset != null) {
//            if ("windows-1252".equalsIgnoreCase(charset.name().trim())) {
//                result = "utf-8";
//            } else {
//                if("Big5".equalsIgnoreCase(charset.name().trim())){
//                    result = "GB18030";
//                }else{
//                    result = charset.name().trim();
//
//                }
//            }
//            logger.info("--------待转html" + f.getName() + "编码是：" + charset.name() + "设置为"+result+" ---------");
//        }
//        return result;
//    }
//    public static String readFileContent(String filePath,String charCode) {
//        StringBuilder result = new StringBuilder();
//        try{
//            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),charCode));
//            String s = null;
//            while((s = br.readLine())!=null){
//                result.append(System.lineSeparator()+s);
//            }
//            br.close();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return result.toString();
//    }
//    //打印样式异常，要调整html的样式。其只支持到CSS2.1的样式
//    public static void main(String[] args) throws Exception{
//        String html = HtmlToPdfUtil.readFileContent("D:/test.html","UTF-8");
//        byte[] htmlByte = HtmlToPdfUtil.html2Pdf(html);
//        String filePath = "D:/test.pdf";
//        try{
//            OutputStream outputStream=new FileOutputStream(filePath);
//            outputStream.write(htmlByte);
//            outputStream.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//}
