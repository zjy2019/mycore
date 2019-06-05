package com.zjy.Config;

import com.zjy.common.MyUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author zjy
 * @des
 * @date 2019/6/4
 */
public class ConfigReadTest {
    private static Properties paramProp = new Properties();
    public static String path = getConfigPath();
    public static void main(String[] args) {

    }

    private static String getConfigPath() {
        String returnpath = "";
        returnpath=ConfigReadTest.class.getClassLoader().getResource("server.properties").getPath();
//        try {
//            String serverfilepath = "";//配置文件路径
//            try {
//                serverfilepath = ConfigReadTest.class.getClassLoader().getResource("server.properties").getPath();
//            } catch (Exception e) {
//                System.out.println("初始化 serverfilepath 路径异常 ex:"+MyUtils.getException(e) );
//            }
//            System.out.println("configinit.serverfilepath:" + serverfilepath);
//            if (MyUtils.stringIsNullOrEmpty(serverfilepath)) {//server.properties 不存在
//                returnpath = PropUtil.class.getClassLoader().getResource("paramServer.properties").getPath();
//            } else {
//                InputStream is = new FileInputStream(serverfilepath);
//                Properties proptemp = new Properties();
//                proptemp.load(is);
//                System.out.println("configinit.isWindows:" + MyUtils.isWindows());
//                String configfilepath = proptemp.getProperty("configfilepath");
//                if (MyUtils.stringIsNullOrEmpty(configfilepath)) {
//                    configfilepath = PropUtil.class.getClassLoader().getResource("paramServer.properties").getPath();
//                }
//                returnpath = configfilepath;
//            }
//        } catch (Exception e) {
//
//            System.out.println("初始化 serverfilepath 路径异常 ex:"+MyUtils.getException(e) );
//        }
//        System.out.println("配置文件路径:" + returnpath+ "configinit");

        return returnpath;
    }


    public static Object getParam(String propKey) {
        try {
            return getParam(propKey, null);
        } catch (Exception e) {

            System.out.println(" errorgetparam初始化 key:"+propKey+" ex:"+MyUtils.getException(e) );
            return  null;
        }

    }

    public static Object getParam(String propKey, Object... defaultValue) {
        try {
            if (paramProp.isEmpty()) {
                if (MyUtils.isWindows()) {
                    initParamProp(path);
                } else {
                    initParamProp(path);
                }
            }
            Object val = paramProp.getProperty(propKey);
            if (val == null) {
                if (defaultValue != null && defaultValue.length > 0) {
                    return defaultValue[0];
                } else {
                    throw new Exception("doesn't exist key with:" + propKey);
                }
            }
            return val;
        } catch (Exception e) {
            System.out.println(" errorgetparam 初始化 key:"+propKey+" ex:"+ MyUtils.getException(e) );
            return defaultValue;
        }

    }

    public static void initParamProp(String parampath) throws FileNotFoundException, IOException {
        path = parampath;
        if (MyUtils.isWindows()) {
            //paramProp.load(new FileInputStream(PropUtil.class.getClassLoader().getResource(path).getPath()));
            paramProp.load(new FileInputStream(path));
        } else {
            paramProp.load(new FileInputStream(path));
        }
        //paramProp.load(new FileInputStream(path));
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getAllProps(String path) throws Exception {
        if (paramProp.isEmpty()) {
            initParamProp(path);
        }
        Map<String, Object> map = new HashMap<String, Object>((Map) paramProp);
        return map;
    }

    public static void setProps(String path, Map<String, Object> kvs) throws IOException {
        if (paramProp.isEmpty()) {
            initParamProp(path);
        }
        OutputStream os = new FileOutputStream(ConfigReadTest.class.getClassLoader().getResource(path).getPath());
        for (Map.Entry<String, Object> kv : kvs.entrySet()) {
            paramProp.setProperty(kv.getKey(), kv.getValue().toString());
        }
        paramProp.store(os, "");
        os.close();
        initParamProp(path);
    }
}
