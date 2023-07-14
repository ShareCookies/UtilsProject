package com.china.hcg.io;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;

/**
 * @autor hecaigui
 * @date 2023/7/13
 * @description
 */
public class SerializableUtils {
    public static byte[] out(Object obj) throws IOException {
        //序列化User对象存储到temp.txt
        //FileOutputStream fos = new FileOutputStream("D:\\temp.txt");
        ByteOutputStream byteOutputStream = new ByteOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byteOutputStream);
        oos.writeObject(obj);

        oos.flush();
        oos.close();
        return byteOutputStream.getBytes();
    }
    public static <T> T in(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream oin = new ObjectInputStream(bis);

        T user1 = (T) oin.readObject();
        return user1;
    }
}
