package bnuz;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Keben on 2018-07-19.
 */
public class Zim {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder("https://www.zim.com/umbraco/surface/ScheduleByRoute/GetPortsInLands");
            uriBuilder.addParameter("query","HONG");
            HttpGet get = new HttpGet(uriBuilder.build());
            get.setHeader("accept", "text/plain, */*; q=0.01");
            get.setHeader("accept-encoding","gzip, deflate, br");
            get.setHeader("accept-language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7,zh-TW;q=0.6,en-GB;q=0.5");
            get.setHeader("cache-control","no-cache");
            get.setHeader("pragma","no-cache");
            get.setHeader("referer","https://www.zim.com/schedules/point-to-point");
            get.setHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
            get.setHeader("x-requested-with","XMLHttpRequest");
            CloseableHttpResponse response =httpClient.execute(get);
            int statusCode =response.getStatusLine().getStatusCode();
            System.out.println(statusCode);
            HttpEntity entity =response.getEntity();
            String string = EntityUtils.toString(entity,"utf-8");
            System.out.println(string);
            response.close();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        httpClient.close();
    }
}
