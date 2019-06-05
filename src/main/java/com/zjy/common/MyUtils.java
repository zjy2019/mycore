package com.zjy.common;

import com.sun.scenario.effect.impl.prism.PrImage;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zjy
 * @des
 * @date 2019/6/4
 */
public class MyUtils {

    public static String getException(Exception e) {
        Writer w = new StringWriter();
        e.printStackTrace(new PrintWriter(w));
        return w.toString();
    }
    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    public static String getRemoteHost(javax.servlet.http.HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        if(ip.contains(",")){
            ip=ip.split(",")[0].trim();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }
    /**
     * ip 转换 long
     * @param ip
     * @return
     */
    public static long getIpNum(String ip)
    {
        long IpNum = 0;
        try {

            if (ip != null && ip.trim().length() >= 7) {
                String[] array = ip.split("\\.");
                if(array.length==4) {
                    for (String str : array) {
                        IpNum = IpNum << 8;
                        IpNum += Long.parseLong(str);
                    }
                }else{
                    if(ip.split(":").length>0) {
                        IpNum = getIPNumV6(ip);
                    }
                }
            } else {
                IpNum = -1;
            }
        }catch (Exception ex){
            System.out.println("getIpNum:IpNum=" + ip);
        }

        return IpNum;

    }

    public static long getIPNumV6(String ip){
        return  ip.hashCode();
    }

    public static String getIpString(long ipNum)
    {
        String IpStr = "";
        try
        {
            long n1 = ipNum >> 24;
            long n2 = (ipNum >> 16) - (n1 << 8);
            long n3 = (ipNum >> 8) - (n1 << 16) - (n2 << 8);
            long n4 = ipNum - (n1 << 24) - (n2 << 16) - (n3 << 8);
            IpStr = n1 + "." + n2 + "." + n3 + "." + n4;
        }
        catch (Exception ex)
        {
            System.out.println("getIpString异常:IpNum=" + ipNum);
        }
        return IpStr;

    }


    /**
     *  转换short为byte
     * <p>
     *  @param s 需要转换的short
     */
    public static byte[] short2ByteArray(short s) {
        byte[] buf = new byte[2];
        buf[1] = (byte) (s >> 8);
        buf[0] = (byte) (s >> 0);
        return buf;
    }

    /**
     * int到byte[]
     *
     * @param i
     * @return
     */
    public static byte[] int2ByteArray(int i) {
        byte[] buf = new byte[4];
        // 由高位到低位
        buf[3] = (byte) ((i >> 24) & 0xFF);
        buf[2] = (byte) ((i >> 16) & 0xFF);
        buf[1] = (byte) ((i >> 8) & 0xFF);
        buf[0] = (byte) (i & 0xFF);
        return buf;
    }

    /**
     * byte[]转int
     *
     * @param bytes
     * @return
     */
    public static int byteArray2Int(byte[] bytes) {
        int value = 0;
        // 由低位到高位
        for (int i = 3; i >= 0; i--) {
            int shift = i * 8;
            value += (bytes[i] & 0x000000FF) << shift;// 往低位游
        }
        return value;
    }

    public static boolean stringIsNullOrEmpty(String str) {
        return (str == null) || (str.length() == 0);
    }
    public static String stringIsNullDefault(String str,String defaultstr) {
        if (str == null || str.length() == 0){
            return defaultstr;
        } else{
            return str;
        }
    }
    public static String getGUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    static Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
    public static boolean isContainChinese(String str) {
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    private static byte[] bytebitmap = null;
    private static String stringbitmap = "137,80,78,71,13,10,26,10,0,0,0,13,73,72,68,82,0,0,0,1,0,0,0,1,8,6,0,0,0,31,21,196,137,0,0,0,1,115,82,71,66,0,174,206,28,233,0,0,0,4,103,65,77,65,0,0,177,143,11,252,97,5,0,0,0,9,112,72,89,115,0,0,14,195,0,0,14,195,1,199,111,168,100,0,0,0,13,73,68,65,84,24,87,99,248,255,255,255,127,0,9,251,3,253,5,67,69,202,0,0,0,0,73,69,78,68,174,66,96,130";
    /**
     * 填充map，供图片使用
     */
    public static byte[] fillBitMap () {

        if (bytebitmap == null)
        {
            String[] strarray = stringbitmap.split(",");
            bytebitmap = new byte[strarray.length];
            for (int i = 0; i < strarray.length; i++)
            {
                bytebitmap[i] = int2Byte(Integer.valueOf(strarray[i]));
            }
        }
        return bytebitmap;
    }
    private static byte int2Byte (int x) {
        return (byte)x;
    }
}
