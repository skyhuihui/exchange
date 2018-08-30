package com.zag.core.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * HttpClient工具类
 */
public class HttpClientUtil {

    protected static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static PoolingHttpClientConnectionManager connectionManager;

    private static CloseableHttpClient httpclient = null;

    private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 最大连接数
     */
    public final static int MAX_TOTAL_CONNECTIONS = 800;
    /**
     * 获取连接的最大等待时间
     */
    public final static int WAIT_TIMEOUT = 60000;
    /**
     * 每个路由最大连接数
     */
    private final static int MAX_ROUTE_CONNECTIONS = 250;
    /**
     * 连接超时时间
     */
    private final static int CONNECT_TIMEOUT = 30000;
    /**
     * 读取超时时间
     */
    public final static int READ_TIMEOUT = 30000;

    private final static int BUFFER_SIZE = 4096;

    private final static int SO_TIMEOUT = 60000;

    /**
     * 默认重新尝试次数
     */
    private final static int DEFAULT_RETRY_TIMES = 3;

    /**
     * 字符编码
     */
    private static final String CHARSET = "UTF-8";

    private static SSLConnectionSocketFactory sslsf;

    private static SSLContext sslcontext;

    private static HttpRequestRetryHandler requestRetryHandler;

    static {
        SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true).setSoTimeout(SO_TIMEOUT).setTcpNoDelay(false).setSoReuseAddress(true).setSoKeepAlive(true).build();

        ConnectionConfig connectionConfig = ConnectionConfig.custom().setBufferSize(BUFFER_SIZE).build();

        requestRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int retryCount, HttpContext httpContext) {
                return retryCount < DEFAULT_RETRY_TIMES && (exception instanceof NoHttpResponseException || exception
						instanceof InterruptedIOException || exception instanceof UnknownHostException || exception
						instanceof ConnectException || exception instanceof SSLException);
            }
        };

        try {
            sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
            sslsf = new SSLConnectionSocketFactory(sslcontext, NoopHostnameVerifier.INSTANCE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        connectionManager = new PoolingHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build());
        connectionManager.setDefaultSocketConfig(socketConfig);
        connectionManager.setDefaultConnectionConfig(connectionConfig);
        connectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
        connectionManager.closeExpiredConnections();
        connectionManager.closeIdleConnections(0, TimeUnit.SECONDS);

        httpclient = HttpClients.custom().addInterceptorFirst(new GZIPRequestInterceptor()).addInterceptorFirst(new
				GZIPResponseInterceptor()).setConnectionManager(connectionManager).setDefaultConnectionConfig
				(connectionConfig).setRetryHandler(requestRetryHandler)
                //.setRedirectStrategy(r)
                .build();
    }

    public static void printHeader(HttpResponse response) {
        Header[] headers = response.getAllHeaders();
        StringBuilder cookie = new StringBuilder();
        int ii = 0;
        while (ii < headers.length) {
            System.out.println(headers[ii].getName() + ": " + headers[ii].getValue());
            if (StringUtils.endsWithIgnoreCase(headers[ii].getName(), "Set-Cookie")) {
                cookie.append(headers[ii].getValue()).append(";");
            }
            ++ii;
        }
        System.out.println(cookie.toString());
    }

    public static String doGet(String url) {
        HttpClientContext context = HttpClientContext.create();
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        try{
            response = httpclient.execute(httpget, context);
            HttpUriRequest req = (HttpUriRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
            HttpHost currentHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
            return req.getURI().isAbsolute() ? req.getURI().toString() : (currentHost.toURI() + req.getURI());
        } catch (Exception e) {
            e.printStackTrace();
            httpget.abort();
        } finally {
            try{
                if (response != null) {
                    response.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String doPost(String url, JSONObject jsonObject) throws Exception {
        Map<String, Object> map = new HashMap<>();
        try {
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    map.put(entry.getKey(), entry.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doPost(url, map);
    }


    public static String doPost(String url, Map<String, Object> map) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("HttpClientUtil doPost  url:{},param:{}", url, map);
        }
        HttpClientContext context = HttpClientContext.create();
        HttpEntity entity;
        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECT_TIMEOUT)
				.setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(CONNECT_TIMEOUT).build();
        httpPost.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        try {
            List<NameValuePair> params = new ArrayList<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            response = httpclient.execute(httpPost, context);
            entity = response.getEntity();
            context.getCookieStore().clear();
            return EntityUtils.toString(entity, "utf-8");
        } catch (ConnectionPoolTimeoutException e) {
            log.error("Exception: 连接池超时.");
            throw e;
        } catch (ConnectTimeoutException e) {
            log.error("Exception: 连接超时");
            throw e;
        } catch (SocketTimeoutException e) {
            log.error("Exception: Socket超时.");
            throw e;
        } catch (ConnectException e) {
            log.error("Exception: 连接被拒绝.");
            throw e;
        } catch (Exception e) {
            log.error("HTTP Exception", e);
            throw e;
        } finally {
            httpPost.abort();
            if (response != null) {
                response.close();
            }
        }
    }

    public static String doPost(String url, String content) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug(String.format("API，POST过去的数据是: %s", content));
        }
        HttpPost httpPost = new HttpPost(url);
        StringEntity postEntity = new StringEntity(content, CHARSET);
        httpPost.addHeader("Content-type", "application/json");
        httpPost.setEntity(postEntity);

        if (log.isDebugEnabled()) {
            log.debug(String.format("API，POST过去的数据是: %s", content));
        }

        String result;
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, CHARSET);
            if (log.isDebugEnabled()) {
                log.debug(String.format("请求返回状态: %s", httpPost.getRequestLine()));
                log.debug(String.format("请求返回值: %s", result));
            }
        } catch (ConnectionPoolTimeoutException e) {
            log.error("Exception: 连接池超时.");
            throw new Exception(e);
        } catch (ConnectTimeoutException e) {
            log.error("Exception: 连接超时");
            throw new Exception(e);
        } catch (SocketTimeoutException e) {
            log.error("Exception: Socket超时.");
            throw new Exception(e);
        } catch (ConnectException e) {
            log.error("Exception: 连接被拒绝.");
            throw new Exception(e);
        } catch (Exception e) {
            log.error("HTTP Exception", e);
            throw e;
        } finally {
            httpPost.abort();
            if (response != null) {
                response.close();
            }
        }
        return result;
    }

}
