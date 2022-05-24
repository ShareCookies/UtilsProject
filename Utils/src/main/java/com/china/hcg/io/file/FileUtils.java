package com.china.hcg.io.file;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Hex;

/**
 * @autor hecaigui
 * @date 2020-3-9
 * @description
 */
public class FileUtils {
    /**
     * @description 读取文件夹下的所有文件
     * @param  * @param path
     * @return
     */
    public static List<File> traverseFolder(String path) {
        List<File> traverseFolderResults = new ArrayList<File>();
        File file = new File(path);
        if (file.exists()) {

            //Returns an array of abstract pathnames denoting the files in the directory denoted by this abstract pathname.
            // 返回代表文件的抽象路径。在(由抽象路径代表的)指定目录中
            // 通过native方法读取目录下所有文件路径。（子目录下的文件都已经帮忙读取好了）
                //File仅代表了路径
            File[] files = file.listFiles();
            if (files == null){
                System.out.println("路径未文件夹");
                return null;
            }
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        //System.out.println("文件夹:" + file2.getAbsolutePath());
                        traverseFolder(file2.getAbsolutePath());
                    } else {
                        traverseFolderResults.add(file2);
                        //System.out.println("文件:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }

        return traverseFolderResults;
    }
    /**
     * @description 递归删除指定目录。
     * @author hecaigui
     * @date 2021-11-16
     * @param path
     * @return
     */
    public static Boolean deleteDirectoryRecursively(String path){

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files == null){
                System.out.println("路径非文件夹");
                return false;
            }
            if (files.length == 0) {
                //System.out.println("文件夹是空的!");
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        //System.out.println("文件夹:" + file2.getAbsolutePath());
                        deleteDirectoryRecursively(file2.getAbsolutePath());
                    } else {
                        file2.delete();
                        //System.out.println("文件:" + file2.getAbsolutePath());
                    }
                }
            }
            file.delete();
        } else {
            System.out.println("文件夹不存在!");
            return false;
        }
        return true;
    }
    /**
     * @description 文件复制
     * java自带工具类方法：Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
     * @param  * @param path
     * @param  destFilePathName F:\autoGenerateMapper\test.txt
     * @return
     */
    public static void copyFileUsingFileStreams(File source, String destFilePathName) {
        File dest ;
        if (destFilePathName !=null && ! "".equals(destFilePathName)){
            dest =  new File(destFilePathName);
        } else{
            return;
        }
        InputStream input = null;
        OutputStream output = null;
        try {
            // 通过本地方法打开文件来读
                /**
                 * Opens the specified file for reading.
                 * @param name the name of the file
                 */
                //private native void open0(String name) throws FileNotFoundException;
            input = new FileInputStream(source);
            // 通过本地方法打开文件来写
                /**
                 * Opens a file, with the specified name, for overwriting or appending.
                 * @param name name of file to be opened
                 * @param append whether the file is to be opened in append mode
                 */
                //private native void open0(String name, boolean append) throws FileNotFoundException;
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];

            int bytesRead;
            // 通过本地方法从文件读取指定指定大小数据到字节数组中。
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
            //并发对文件读写了？
        } catch (IOException exception){
            System.err.println(exception);
        } finally {
            try {
                // channel.close();？
                // 掉本地方法关闭
                input.close();
                output.close();
            } catch (IOException exception){
                System.err.println(exception);
            }
        }
    }


    /**
     * @description 读取文本内容
     * @param  * @param file
     * @return
     */
    public static String readTxtContent(File file) {
        StringBuilder content = new StringBuilder();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
             fileReader = new FileReader(file);
             bufferedReader = new BufferedReader(fileReader);
            String line =bufferedReader.readLine();
            while (line != null){
                //System.out.println(line);
				if (line != null){
                    content.append(line+"\n");
                }
                line = bufferedReader.readLine();
            }
        }catch (IOException exception){
            System.err.println(exception);
        }finally {
            try {
                fileReader.close();
                // 仅关最外装饰即可，因为装饰 调用的是被装饰流的close
                // 最初的流则一般调native的close
                bufferedReader.close();
            } catch (IOException exception){
                System.err.println(exception);
            }
        }
        return content.toString();
    }
    /**
     * @description 文件转byte[]
     * @param  * @param file
     * @return
     */
    public static byte[] fileToByte(File file) {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        //byte[] buffer = null;
        byte[] bytes = null;
        try {

            fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream(fis.available());
            bytes = new byte[fis.available()];
            int temp;
            while ((temp = fis.read(bytes)) != -1) {
                baos.write(bytes, 0, temp);
            }
            //buffer = baos.toByteArray();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                baos.close();
            } catch (IOException exception){
                System.err.println(exception);
            }
        }
        //System.out.println(buffer.length);
        System.err.println(bytes.length);
        return bytes;
    }
    /**
     * @description 往文本写入内容
     * @param  * @param file
     * @return
     */
    public static void writeToFile(File file,String content) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);
            bufferedWriter.flush(); // 把缓存区内容压入文件
        }catch (IOException exception){
            System.err.println(exception);
        }finally {
            try {
                fileWriter.close();
                bufferedWriter.close();
            } catch (IOException exception){
                System.err.println(exception);
            }
        }
    }
    /**
     * @description 生成sha1，可用来验证文件完整性
     * <p>
     *     https://www.cnblogs.com/-beyond/p/10575078.html
     *     摘要算法简介：
     * 　　   摘要算法，也是加密算法的一种，还有另外一种叫法：指纹。
     *        摘要算法就是对指定的数据进行一系列的计算，然后得出一个串内容，该内容就是该数据的摘要。不同的数据产生的摘要是不同的.
     *     摘要算法作用：
     *        所以，可以用它来进行一些数据加密的工作：通过对比两个数据加密后的摘要是否相同，来判断这两个数据是否相同。
     * 　　   还可以用来保证数据的完整性，常见的软件在发布之后，会同时发布软件的md5和sha值，这个md5和sha值就是软件的摘要。当用户将软件下载之后，然后去计算软件的摘要，如果计算所得的摘要和软件发布方提供的摘要相同，则证明下载的软件和发布的软件一模一样，否则，就是下载过程中数据（软件）被篡改了。
     *
     * 　　常见的摘要算法包括：
     *          md、sha这两类。md包括md2、md4、md5；sha包括sha1、sha224、sha256、sha384、sha512。
     * </p>
     * @param
     * @return
     */
    public static String fileSha1(byte[] bytes) throws NoSuchAlgorithmException {
        // 获取指定摘要算法的messageDigest对象
        MessageDigest messageDigest = MessageDigest.getInstance("SHA"); // 此处的sha代表sha1

        // 调用digest方法，进行加密操作
        byte[] cipherBytes = messageDigest.digest(bytes);

        // 使用Apache的Hex类实现Hex(16进制字符串和)和字节数组的互转
        String cipherStr = Hex.encodeHexString(cipherBytes);

        System.out.println(cipherStr);
        return cipherStr;
    }
    /**
     * @description 通过字节来判断文件是否改变
     * @author hecaigui
     * @date 2021-3-5
     * @param bytes1
     * @param bytes2
     * @return true 文件改变 false 文件没改变
     */
    public static boolean fileChangeForByte(byte[] bytes1,byte[] bytes2)throws NoSuchAlgorithmException{
        String byte1ShaString = FileUtils.fileSha1(bytes1);
        String byte2ShaString = FileUtils.fileSha1(bytes2);
        if (byte1ShaString.equals(byte2ShaString)){
            return false;
        }
        return true;
    }
    /**
     * @description 文件的合并，以合并相同行的方式
     * @return
     * @demo fileMergeOfSameRow("D://file1.txt","D://file2.txt","D://result2.txt"," ",";");
     */
    public static void fileMergeOfSameRow(String file1, String file2,String resultFile,String mergeSeparator ,String newLineSuffix) throws IOException {
        BufferedReader file1BufferReader = new BufferedReader(new FileReader(file1));
        BufferedReader file2BufferReader = new BufferedReader(new FileReader(file2));
        BufferedWriter resultBufferWriter =  new BufferedWriter(new FileWriter(resultFile));
        boolean file1EOF = false;
        boolean file2EOF = false;
        while (true) {
            /* String 与 StringBuilder区别？StringBuilder 字符串是否会在常量池中 */
            String r1 = null;
            String r2 = null;
            if (!file1EOF){
                r1 = file1BufferReader.readLine();

            }
            if (!file2EOF){
                r2 = file2BufferReader.readLine();

            }
            if (r1 == null) {
                file1EOF = true;
            } else {
                r1+=mergeSeparator;
            }
            if (r2 == null) {
                file2EOF = true;
            } else {
                r2+=newLineSuffix+"\n";
            }
            if (file1EOF && file2EOF) {
                break;
            }

            if (!file1EOF){
                resultBufferWriter.write(r1);
            }
            if (!file2EOF){
                resultBufferWriter.write(r2);
            }
        }
        file1BufferReader.close();
        file2BufferReader.close();
        resultBufferWriter.close();
    }

//    /**
//     * @description 二进制流（来源要为字符）转字符串。
//     * @param  * @param args
//     * @return
//     */
//    public static String streamToString(InputStream inputStream)
//    {
//        byte[] bytes = "hello world".getBytes();
//        // 字节进行base64编码
//        String encoded = Base64.getEncoder().encodeToString(bytes);
//        System.out.println( encoded );
//        // base64解码
//        byte[] decoded = Base64.getDecoder().decode(encoded);
//        System.out.println( new String(decoded) );
//    }

//https://blog.csdn.net/zhaozihao594/article/details/104376096
private static void tempFileTest()throws Exception{
    //1.临时文件夹路径。
    String defaultBaseDir = System.getProperty("java.io.tmpdir");
    File file = new File(defaultBaseDir+"rjoa");
    System.err.println(defaultBaseDir);
    //2.创建一个带有自定义前缀的临时文件夹：C:\Users\ADMINI~1\AppData\Local\Temp\test1156080134250973867
    Path tmpNoPrefix = Files.createTempDirectory("rjoa");
    System.err.println(tmpNoPrefix);
    //3.创建一个临时文件：
    //Path tmpCustomPrefixAndSuffix = Files.createTempFile();

    //4.1删除临时文件夹/文件是可以由操作系统或专用工具完成的任务。
        //linux tmp下文件是自动删除的。原因:https://blog.csdn.net/chao199512/article/details/113920887
        //windows了
    //但是，有时，我们需要以编程方式进行控制，并基于不同的设计考虑因素删除文件夹/文件。


    // 4.2当我们需要在JVM关闭之前立即完成某些任务（例如，清理任务）时，此机制就很有用。//当JVM在关闭时执行shutdown-hook时，将调用其 方法。
//    Runtime.getRuntime().addShutdownHook(new Thread() {
//        @Override
//        public void run() {
//            try (DirectoryStream ds = Files.newDirectoryStream(tmpDir)) {
//                for (Path file: ds) {
//                    Files.delete(file);
//                    Files.delete(tmpDir);
//                }
//                //simulate some operations with temp file until delete it
//
//                Thread.sleep(10000);
//
//            }
//        });

    //4.3删除临时文件夹/文件的另一种解决方案依赖于该 File.deleteOnExit()方法。通过调用此方法，我们可以注册删除文件夹/文件。JVM关闭时，将执行删除操作：
    // path.toFile().deleteOnExit();

}
    /**
     * @description 系统临时目录中创建文件夹
     *  //linux tmp下文件10天前文件是自动删除的。原因:https://blog.csdn.net/chao199512/article/details/113920887
     *  //windows了？
     * @author hecaigui
     * @date 2021-11-16
     * @param folderName
     * @return
     */
    public static Path tempFolder(String folderName)throws IOException{
        String defaultBaseDir = System.getProperty("java.io.tmpdir");

        String directory = defaultBaseDir+folderName;
        Path path = Paths.get(directory);
        System.err.println(path.toString());
        try {
            Files.createDirectory(path);
        }catch (java.nio.file.FileAlreadyExistsException e){
            return path;
        }
        return path;
    }

    public static void main(String[] args) throws Exception
    {
        System.err.println(File.separator);
        //        Long oneDayLong = 1000*3600*24L;
        SimpleDateFormat ymdSdf = new SimpleDateFormat("yyyyMMdd");
        deleteDirectoryRecursively(System.getProperty("java.io.tmpdir")+File.separator+"rjoa"+ymdSdf.format(new Date(System.currentTimeMillis())));


        //Paths.get(System.getProperty("java.io.tmpdir")+"/rjoa"+ymdSdf.format(new Date(System.currentTimeMillis() - oneDayLong))).toFile().delete();
//        Thread.sleep(1000);
//        Path path = tempFolder("/rjoa"+ymdSdf.format(new Date()));
//        Files.createFile(Paths.get(path.toString()+"/test.txt"));



//traverseFolder("F:\\he_space\\stepByStep\\SHARE_CONTENT\\java基础编程\\IO\\IO");
//        String filePath = "C:\\Users\\Administrator\\Desktop\\1.pdf";
//        File file = new File(filePath);
//
//        byte[] bytes = "hello world".getBytes();
//        // 字节进行base64编码
//        String encoded = Base64.getEncoder().encodeToString(bytes);
//        System.out.println( encoded );
//        // base64解码
//        byte[] decoded = Base64.getDecoder().decode(encoded);
//        System.out.println( new String(decoded) );
    }

//    public static void readFileContent(File file) {
//        String content = "";
//        FileReader fileReader = null;
//        BufferedReader bufferedReader = null;
//        try {
//            fileReader = new FileReader(file);
//            bufferedReader = new BufferedReader(fileReader);
//            String line =bufferedReader.readLine();
//            while (line!=null){
//                System.out.println(line);
//                line = bufferedReader.readLine();
//            }
//        }catch (IOException exception){
//            System.err.println(exception);
//        }finally {
//            try {
//                fileReader.close();
//                bufferedReader.close();
//            } catch (IOException exception){
//                System.out.println(exception);
//            }
//        }
//    }

	//文件转流
//	File file = new File(resourcePath);
//	new FileInputStream(file);
//	//将流转成字节数组
//	  ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//	  byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据
//	  int rc = 0;
//	  while ((rc = inputIs.read(buff, 0, 100)) > 0) {
//		  outStream.write(buff, 0, rc);
//	  }
//	  //合并之后的字节数组
//	  byte[] in_merge = outStream.toByteArray();
}
