package com.zag.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.core.util
 * @ClassName: ${HttpUtil}
 * @Description: 程序中访问http数据接口
 * @Author: skyhuihui
 * @CreateDate: 2018/8/27 14:27
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/27 14:27
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class HttpUtil {
    /**
     * 程序中访问http数据接口
     */
    public static String getURLContent(String urlStr) {
        /** 网络的url地址 */
        URL url = null;
        /** http连接 */
        HttpURLConnection httpConn = null;
        /**//** 输入流 */
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(urlStr);
            in = new BufferedReader(new InputStreamReader(url.openStream(), "GBK"));
            String str = null;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception ex) {

        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }
        String result = sb.toString();
        return result;

    }
}
