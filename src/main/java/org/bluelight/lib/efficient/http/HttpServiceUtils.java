package org.bluelight.lib.efficient.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * http service utils.
 * Created by mikes on 14-10-27.
 */
public class HttpServiceUtils {
    private static HttpService defaultHttpService=null;
    private static final int MAX_PER_ROUTE=200;
    private static final int MAX_TOTAL=2014;
    private static final int CONN_TIMEOUT=3*1000;
    private static final int SOCK_TIMEOUT=4*1000;
    private static final String USER_AGENT="bluelight_efficient";

    public static HttpService getService(){
        if (defaultHttpService==null){
            synchronized (HttpServiceUtils.class) {
                if (defaultHttpService==null) {
                    HttpClient httpClient = HttpClients.custom()
                            .setUserAgent(USER_AGENT)
                            .setConnectionManager(getPoolingHttpClientConnectionManager(MAX_PER_ROUTE, MAX_TOTAL))
                            .setDefaultRequestConfig(getRequestConfig(CONN_TIMEOUT, SOCK_TIMEOUT))
                            .build();
                    defaultHttpService = new HttpService(httpClient);
                }
            }
        }
        return defaultHttpService;
    }
    private static PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager(int connMaxPerRoute,int connMaxTotal){
        PoolingHttpClientConnectionManager cm=new PoolingHttpClientConnectionManager();
        cm.setDefaultMaxPerRoute(connMaxPerRoute);
        cm.setMaxTotal(connMaxTotal);
        return cm;
    }
    private static RequestConfig getRequestConfig(int connTimeout,int sockTimeout){
        return RequestConfig.custom()
                .setConnectTimeout(connTimeout)
                .setSocketTimeout(sockTimeout)
                .build();
    }
    public static HttpService getService(int sockTimeout){
        HttpClient httpClient = HttpClients.custom()
                .setUserAgent(USER_AGENT)
                .setConnectionManager(getPoolingHttpClientConnectionManager(MAX_PER_ROUTE, MAX_TOTAL))
                .setDefaultRequestConfig(getRequestConfig(CONN_TIMEOUT, sockTimeout))
                .build();
        return new HttpService(httpClient);
    }
    public static HttpService getService(int sockTimeout, int connTimeout){
        HttpClient httpClient = HttpClients.custom()
                .setUserAgent(USER_AGENT)
                .setConnectionManager(getPoolingHttpClientConnectionManager(MAX_PER_ROUTE, MAX_TOTAL))
                .setDefaultRequestConfig(getRequestConfig(connTimeout, sockTimeout))
                .build();
        return new HttpService(httpClient);
    }
    public static HttpService getService(int sockTimeout, int connTimeout, int connMaxTotal){
        HttpClient httpClient = HttpClients.custom()
                .setUserAgent(USER_AGENT)
                .setConnectionManager(getPoolingHttpClientConnectionManager(MAX_PER_ROUTE, connMaxTotal))
                .setDefaultRequestConfig(getRequestConfig(connTimeout, sockTimeout))
                .build();
        return new HttpService(httpClient);
    }
    public static HttpService getService(int sockTimeout, int connTimeout, int connMaxTotal, int connMaxPerRoute){
        HttpClient httpClient = HttpClients.custom()
                .setUserAgent(USER_AGENT)
                .setConnectionManager(getPoolingHttpClientConnectionManager(connMaxPerRoute, connMaxTotal))
                .setDefaultRequestConfig(getRequestConfig(connTimeout, sockTimeout))
                .build();
        return new HttpService(httpClient);
    }
}
