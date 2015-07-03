package org.bluelight.lib.efficient.http;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http service efficient than http client.
 * Created by mikes on 15-3-23.
 */
public class HttpService {
    private HttpClient httpClient;
    HttpService(HttpClient httpClient){
        this.httpClient=httpClient;
    }
    public HttpClient getHttpClient(){
        return httpClient;
    }
    public String get(String urlStr) throws IOException {
        HttpGet get=new HttpGet(urlStr);
        return httpClient.execute(get, new BasicResponseHandler());
    }
    public String get(String urlPatternStr, Object... queryParams) throws IOException {
        String urlStr= MessageFormat.format(urlPatternStr,queryParams);
        return this.get(urlStr);
    }
    public String get(String urlStr, Map<String,Object> queryParamMap) throws IOException {
        String urlStrTrim=urlStr.trim();
        StringBuilder urlBuilder=new StringBuilder(urlStrTrim);
        if (urlStrTrim.contains("?") && !urlStrTrim.endsWith("&")){
            urlBuilder.append("&");
        }
        if (!urlStrTrim.contains("?")){
            if(urlStrTrim.endsWith("/")) {
                urlBuilder.deleteCharAt(urlBuilder.length() - 1);
            }
            urlBuilder.append("?");
        }
        for (Map.Entry<String,Object> entry: queryParamMap.entrySet()){
            urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        urlBuilder.deleteCharAt(urlBuilder.length()-1);
        String realUrl=urlBuilder.toString();
        return this.get(realUrl);
    }
    public String post(String urlStr, String content) throws IOException {
        StringEntity entity=new StringEntity(content, ContentType.create("text/plain", "UTF-8"));
        HttpPost post=new HttpPost(urlStr);
        post.setEntity(entity);
        return httpClient.execute(post, new BasicResponseHandler());
    }
    public String post(String urlStr, Map<String,String> form) throws IOException {
        List<NameValuePair> list=new ArrayList<NameValuePair>();
        for (Map.Entry<String,String> entry: form.entrySet()){
            list.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        UrlEncodedFormEntity entity=new UrlEncodedFormEntity(list,"UTF-8");
        HttpPost post=new HttpPost(urlStr);
        post.setEntity(entity);
        return httpClient.execute(post, new BasicResponseHandler());
    }
    public String put(String urlStr, String content) throws IOException {
        StringEntity entity=new StringEntity(content, ContentType.create("text/plain","UTF-8"));
        HttpPut put=new HttpPut(urlStr);
        put.setEntity(entity);
        return httpClient.execute(put, new BasicResponseHandler());
    }
    public String put(String urlStr, Map<String,String> form) throws IOException {
        List<NameValuePair> list=new ArrayList<NameValuePair>();
        for (Map.Entry<String,String> entry: form.entrySet()){
            list.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        UrlEncodedFormEntity entity=new UrlEncodedFormEntity(list,"UTF-8");
        HttpPut put=new HttpPut(urlStr);
        put.setEntity(entity);
        return httpClient.execute(put, new BasicResponseHandler());
    }
    public String delete(String urlStr) throws IOException {
        HttpDelete delete=new HttpDelete(urlStr);
        return httpClient.execute(delete, new BasicResponseHandler());
    }
}
