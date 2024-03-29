package com.china.hcg.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Random;

/**
 * https://blog.csdn.net/liudao51/article/details/103007892
 * Twitter_Snowflake<br>
 * SnowFlake的结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId<br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 */
//todo：
//@Component
public class TwitterUidGeneratorUtil implements InitializingBean {
 
    // ==============================Fields===========================================
    /**
     * 开始时间截 (2015-01-01 00:00:00) 毫秒级时间戳
     */
    private final long twepoch = 1420041600000L;
 
    /**
     * 机器id所占的位数
     */
    private final long workerIdBits = 5L;
 
    /**
     * 数据标识id所占的位数
     */
    private final long datacenterIdBits = 5L;
 
    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
 
    /**
     * 支持的最大数据标识id，结果是31
     */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
 
    /**
     * 序列在id中占的位数
     */
    private final long sequenceBits = 12L;

    /**
     * 机器ID向左移12位
     */
    private final long workerIdShift = sequenceBits;
 
    /**
     * 数据标识id向左移17位(12+5)
     */
    private final long datacenterIdShift = sequenceBits + workerIdBits;
 
    /**
     * 时间截向左移22位(5+5+12)
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
 
    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);
 
    /**
     * 工作机器ID(0~31)
     */
    private static long workerId;
 
    /**
     * 数据中心ID(0~31)
     */
    private static long datacenterId;
 
    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;
 
    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //==============================Constructors=====================================
    @Override
    public void afterPropertiesSet() throws Exception{
        Integer i = 0;
        String mac = getOneNetworkMac();
        int maxWorkIdNum = 31;
        while (i < maxWorkIdNum){
            i++;
            boolean result = stringRedisTemplate.opsForHash().putIfAbsent("TwitterUidGeneratorUtilWorkerId",i.toString(),getOneNetworkMac());
            if (result){
                TwitterUidGeneratorUtil.workerId = i;
                TwitterUidGeneratorUtil.datacenterId = 0;
                break;
            } else {
                Object address = stringRedisTemplate.opsForHash().get("TwitterUidGeneratorUtilWorkerId",i.toString());
                if (mac.equals(address)){
                    TwitterUidGeneratorUtil.workerId = i;
                    TwitterUidGeneratorUtil.datacenterId = 0;
                    break;
                }
            }
        }
    }
    /**
     * @description 获取一张网卡mac
     * https://blog.csdn.net/qq_43080741/article/details/124237926
     * @author hecaigui
     * @date 2022-8-15
     * @param null
     * @return
     */
     String  getOneNetworkMac() throws Exception{
        Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        byte[] mac = null;
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = allNetInterfaces.nextElement();
            if (netInterface.isLoopback() || netInterface.isVirtual() || netInterface.isPointToPoint() || !netInterface.isUp()) {
                continue;
            } else {
                mac = netInterface.getHardwareAddress();
                if (mac != null) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : "\n"));
                    }
                    return sb.toString();
                }
            }
        }
        throw new Error("TwitterUidGeneratorUtil.class唯一标识码初始化失败,getOneNetworkMac异常，请联系管理员");
    }
    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     */
    public TwitterUidGeneratorUtil(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        TwitterUidGeneratorUtil.workerId = workerId;
        TwitterUidGeneratorUtil.datacenterId = datacenterId;
    }
    public TwitterUidGeneratorUtil() {

    }

    // ==============================Methods==========================================
 
    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        if (TwitterUidGeneratorUtil.workerId == 0){
            throw new Error("TwitterUidGeneratorUtil.class唯一标识码初始化失败,workerId为0，请联系管理员");
        }
        long timestamp = timeGen();
 
        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
 
        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }
 
        //上次生成ID的时间截
        lastTimestamp = timestamp;
 
        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift) //
                | (datacenterId << datacenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence;
    }
 
    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }
 
    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }
 
 
    //==============================Test=============================================
 
    /**
     * 测试
     */
//    public static void main(String[] args) throws Exception{
//        // 构造方法设置机器码：第9个机房的第20台机器
//        TwitterUidGeneratorUtil idWorker = new TwitterUidGeneratorUtil();
//        for (int i = 0; i < 1000; i++) {
//            long id = idWorker.nextId();
//            System.out.println(Long.toBinaryString(id));
//            System.out.println(id);
//        }
//    }

    /**
     * 通过位移方式进行计算
     * https://blog.csdn.net/beijiaoguzai/article/details/124667298
     * @param bs
     * @return
     */
    static long bytes2long(byte[] bs)  {
        int bytes = bs.length;

        switch(bytes) {
            case 0:
                return 0;
            case 1:
                return (long)((bs[0] & 0xff));
            case 2:
                return (long)((bs[0] & 0xff) <<8 | (bs[1] & 0xff));
            case 3:
                return (long)((bs[0] & 0xff) <<16 | (bs[1] & 0xff) <<8 | (bs[2] & 0xff));
            case 4:
                return (long)((bs[0] & 0xffL) <<24 | (bs[1] & 0xffL) << 16 | (bs[2] & 0xffL) <<8 | (bs[3] & 0xffL));
            case 5:
                return (long)((bs[0] & 0xffL) <<32 | (bs[1] & 0xffL) <<24 | (bs[2] & 0xffL) << 16 | (bs[3] & 0xffL) <<8 | (bs[4] & 0xffL));
            case 6:
                return (long)((bs[0] & 0xffL) <<40 | (bs[1] & 0xffL) <<32 | (bs[2] & 0xffL) <<24 | (bs[3] & 0xffL) << 16 | (bs[4] & 0xffL) <<8 | (bs[5] & 0xffL));
            case 7:
                return (long)((bs[0] & 0xffL) <<48 | (bs[1] & 0xffL) <<40 | (bs[2] & 0xffL) <<32 | (bs[3] & 0xffL) <<24 | (bs[4] & 0xffL) << 16 | (bs[5] & 0xffL) <<8 | (bs[6] & 0xffL));
            case 8:
                return (long)((bs[0] & 0xffL) <<56 | (bs[1] & 0xffL) << 48 | (bs[2] & 0xffL) <<40 | (bs[3] & 0xffL)<<32 |
                        (bs[4] & 0xffL) <<24 | (bs[5] & 0xffL) << 16 | (bs[6] & 0xffL) <<8 | (bs[7] & 0xffL));
            default:
                return 0;
        }
    }

}