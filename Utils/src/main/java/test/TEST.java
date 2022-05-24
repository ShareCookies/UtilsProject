package test;

import java.nio.channels.Channel;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @autor hecaigui
 * @date 2021-11-16
 * @description
 */
public class TEST {
    static Path tempFolder(String folderName)throws Exception{
        String defaultBaseDir = System.getProperty("java.io.tmpdir");
        SimpleDateFormat ymdSdf = new SimpleDateFormat("yyyyMMdd");
        String directory = defaultBaseDir+"/rjoa"+ymdSdf.format(new Date());
        Path path = Paths.get(directory);
        System.err.println(path.toString());
        try {
            Files.createDirectory(path);
        }catch (java.nio.file.FileAlreadyExistsException e){
            return path;
        }
        return path;
    }

    public static void main(String[] args) throws Exception {
        ServerSocketChannel channel ;
        SocketChannel channel2 ;
        Selector selector ;
    }
}
