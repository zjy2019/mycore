package com.zjy.common;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author zjy
 * @des
 * @date 2019/6/4
 */
public class HttpUtils {

    /** POST请求 */
    public static JSONObject doPost(String url, JSONObject params) {

        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", null);
        result.put("code", 200);
        result.put("msg", null);
        OutputStream out = null;
        DataOutputStream dataOutputStream = null;
        InputStream in = null;
        ByteArrayOutputStream baos = null;
        try {
            URLConnection urlConnection = new URL(url).openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlConnection;

            httpUrlConnection.setConnectTimeout(1000);
            httpUrlConnection.setReadTimeout(1000);

            // 设置是否向httpUrlConnection输出，post请求，参数要放在http正文内，因此需要设为true,
            // 默认情况下是false;
            httpUrlConnection.setDoOutput(true);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpUrlConnection.setDoInput(true);
            // 忽略缓存
            httpUrlConnection.setUseCaches(false);
            // 设定请求的方法为"POST"，默认是GET
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.connect();

            // 建立输入流，向指向的URL传入参数

            String queryString = "";

            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    queryString += entry.getKey()
                            + "="
                            + URLEncoder.encode(entry.getValue().toString(),
                            "UTF-8") + "&";
                }
            }

            if (queryString.length() > 0) {
                queryString = queryString
                        .substring(0, queryString.length() - 1);
                out = httpUrlConnection.getOutputStream();
                dataOutputStream = new DataOutputStream(out);
                dataOutputStream.writeBytes(queryString);

                dataOutputStream.flush();

                out.flush();
            }

            // 获得响应状态
            int responseCode = httpUrlConnection.getResponseCode();

            if (HttpURLConnection.HTTP_OK == responseCode) {
                baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                in = httpUrlConnection.getInputStream();
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                    baos.flush();
                }

                result.put("success", true);
                result.put("data", baos.toString("UTF-8"));
                result.put("code", 200);
                result.put("msg", "请求成功");
            } else {
                result.put("success", false);
                result.put("code", responseCode);
                result.put("msg", "请求异常");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("code", 500);
            result.put("msg",
                    "请求异常，异常信息：" + e.getClass() + "->" + e.getMessage());
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    result.put("success", false);
                    result.put("code", 500);
                    result.put("msg",
                            "请求异常，异常信息：" + e.getClass() + "->" + e.getMessage());
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    result.put("success", false);
                    result.put("code", 500);
                    result.put("msg",
                            "请求异常，异常信息：" + e.getClass() + "->" + e.getMessage());
                }
            }
            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    result.put("success", false);
                    result.put("code", 500);
                    result.put("msg",
                            "请求异常，异常信息：" + e.getClass() + "->" + e.getMessage());
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    result.put("success", false);
                    result.put("code", 500);
                    result.put("msg",
                            "请求异常，异常信息：" + e.getClass() + "->" + e.getMessage());
                }
            }
        }

        return result;
    }

    /** GET请求 */
    private static JSONObject doGetprivate(String url,String ua,int timeout,boolean isredirect) {

        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("url2", url);
        result.put("data", null);
        result.put("code", 200);
        result.put("msg", null);

        InputStream in = null;
        ByteArrayOutputStream baos = null;

        try {

            URLConnection urlConnection = new URL(url).openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlConnection;
            httpUrlConnection.setConnectTimeout(timeout);
            httpUrlConnection.setReadTimeout(timeout);
            // 设置是否向httpUrlConnection输出，post请求，参数要放在http正文内，因此需要设为true,
            // 默认情况下是false;
            httpUrlConnection.setDoOutput(false);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpUrlConnection.setDoInput(true);
            // 忽略缓存
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setRequestProperty("User-Agent",ua);
            // 设定请求的方法为"POST"，默认是GET
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setInstanceFollowRedirects(isredirect);
            httpUrlConnection.connect();

            // 获得响应状态
            int responseCode = httpUrlConnection.getResponseCode();

            if (HttpURLConnection.HTTP_OK == responseCode) {
                baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                in = httpUrlConnection.getInputStream();
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                    baos.flush();
                }

                result.put("success", true);
                result.put("data", baos.toString("UTF-8"));
                result.put("code", 200);
                result.put("msg", "请求成功");
            } else {
                result.put("success", false);
                result.put("code", responseCode);
                result.put("msg", "请求异常");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("code", 500);
            result.put("msg",
                    "请求异常，异常信息：" + e.getClass() + "->" + e.getMessage());
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    result.put("success", false);
                    result.put("code", 500);
                    result.put("msg",
                            "请求异常，异常信息：" + e.getClass() + "->" + e.getMessage());
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    result.put("success", false);
                    result.put("code", 500);
                    result.put("msg",
                            "请求异常，异常信息：" + e.getClass() + "->" + e.getMessage());
                }
            }
        }
        return result;
    }
    public static JSONObject doGet(String url,String ua) {
        return doGetprivate(url,ua,1000,false);
    }
    public static JSONObject doGet(String url,String ua,int timeout,boolean isredirect) {
        return doGetprivate(url,ua,timeout,isredirect);
    }
}
