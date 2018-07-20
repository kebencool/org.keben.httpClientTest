package com.bnuz;

import org.apache.http.HttpEntity;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Keben on 2018-07-19.
 */
public class Zim {

    /**
     * 在下拉栏获取第一个港口信息
     * @param client
     * @param portCode
     * @return
     */
    public String getPortCode(CloseableHttpClient client, String portCode){
        String result = null;
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder("https://www.zim.com/umbraco/surface/ScheduleByRoute/GetPortsInLands");
            uriBuilder.addParameter("query",portCode);
            HttpGet get = new HttpGet(uriBuilder.build());
            get.setHeader("accept", "text/plain, */*; q=0.01");
            get.setHeader("accept-encoding","gzip, deflate, br");
            get.setHeader("accept-language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7,zh-TW;q=0.6,en-GB;q=0.5");
            get.setHeader("cache-control","no-cache");
            get.setHeader("pragma","no-cache");
            get.setHeader("referer","https://www.zim.com/schedules/point-to-point");
            get.setHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
            get.setHeader("x-requested-with","XMLHttpRequest");
            CloseableHttpResponse response =client.execute(get);
            int statusCode =response.getStatusLine().getStatusCode();
            System.out.println("status: "+statusCode);
            HttpEntity entity =response.getEntity();
            String string = EntityUtils.toString(entity,"utf-8");
            System.out.println("response : "+ string);
            String[] strings = string.split("\"");
            result = strings[9];
            response.close();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            if (result == null){
                System.out.println("log : get port ajax response fail.");
            }
            return result;
        }
    }

    /**
     * 模拟ajax获取查询结果页面html
     * @param client
     * @param portCode
     * @param portDistinationCode
     * @param fromDate
     * @param weeksahead
     * @param direction
     * @return
     */
    public String getTable(CloseableHttpClient client, String portCode , String portDistinationCode , String fromDate , String weeksahead , String direction ){
        String resultHtml = null;
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder("https://www.zim.com/schedules/point-to-point");
            ArrayList<NameValuePair> queryList = new ArrayList<NameValuePair>();
            NameValuePair queryPortCode = new BasicNameValuePair("portcode", portCode);
            NameValuePair queryPortDistinationCode = new BasicNameValuePair("portdestinationcode", portDistinationCode);
            NameValuePair queryFromDate = new BasicNameValuePair("fromdate", fromDate);
            NameValuePair queryWeeksahead = new BasicNameValuePair("weeksahead", weeksahead);
            NameValuePair queryDirection = new BasicNameValuePair("direction", direction);

            queryList.add(queryPortCode);
            queryList.add(queryPortDistinationCode);
            queryList.add(queryFromDate);
            queryList.add(queryWeeksahead);
            queryList.add(queryDirection);

            uriBuilder.addParameters(queryList);

            HttpGet get = new HttpGet(uriBuilder.build());
            get.setHeader("Host","www.zim.com");
            get.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
//            get.setHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//            get.setHeader("accept-encoding","gzip, deflate, br");
//            get.setHeader("accept-language","en-US,en;q=0.5");
//            get.setHeader("referer","https://www.zim.com/schedules/point-to-point?portcode=CALDO%3B0&portdestinationcode=USLAX%3B10&fromdate=19-07-2018&weeksahead=6&direction=True");
//            get.setHeader("connection","keep-alive");
//            get.setHeader("upgrade-insecure-requests","1");
            CloseableHttpResponse response =client.execute(get);
            int statusCode =response.getStatusLine().getStatusCode();
            System.out.println("status: "+statusCode);
            HttpEntity entity =response.getEntity();
            resultHtml = EntityUtils.toString(entity,"utf-8");
//            System.out.println("response : "+ resultHtml);
            response.close();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (resultHtml == null){
                System.out.println("log : get ajax response fail.");
            }
            return resultHtml;
        }
    }


    /**
     * 解析html表格中的信息
     * @param resultHtml
     * @return
     */
    public void analysisTableMessage(String resultHtml){
        Document document = Jsoup.parse(resultHtml);
        System.out.println(document.title());
        Elements tbody = document.select("tbody[class=p2p-tbody]");
//        System.out.println(tbody);
        Elements trs = tbody.select("tr");

        List<List<List<String>>> tables = new ArrayList<List<List<String>>>();
        int lineCount = 0;
        List<List<String>> table = new ArrayList<List<String>>();
        List<String> line = null;

        for (Element tr : trs){
            Elements tds = tr.select("td[rowspan]");
            if(tds.size()!=0){
                if (lineCount!=0){
                    tables.add(table);
                }

                table = new ArrayList<List<String>>();
                lineCount = Integer.parseInt(tds.get(0).attr("rowspan"));
            }
            tds = tr.select("td");
            line = new ArrayList<String>();
            for (Element td : tds){

                Elements as = td.select("a");
                if (as.size()!=0){
                    line.add(as.get(0).ownText());
                }else {
                    Elements strongs = td.select("strong");
                    if (strongs.size() != 0) {
                        line.add(td.select("strong").get(0).ownText());
                    } else {
                        Elements ps = td.select("p");
                        if (ps.size() != 0) {
                            for (Element p : ps) {
                                line.add(p.ownText());
                            }
                        } else {
                            Elements row = td.select("td[rowspan]");
                            if (row.size() != 0) {
                                line.add(row.get(0).ownText());
                            }
                        }
                    }
                }
            }
            table.add(line);
        }
        System.out.println(tables.get(0).toString());
    }

    /**
     * 获取表格页数
     * @param resultHtml
     * @return
     */
    public int getPageNumber(String resultHtml){
        Document document = Jsoup.parse(resultHtml);
        Elements ul = document.select("ul[class=paging-list]");
        Elements lis = ul.get(0).select("ul+li:not(li[class^=PagedList])");
        return lis.size();
    }
    public static void main(String[] args) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        Zim zim = new Zim();
        int pageNumber = 0;
        String portCode = zim.getPortCode(client,"hong");
        String portDistinationCode = zim.getPortCode(client,"los");
        System.out.println(portCode);
        System.out.println(portDistinationCode);
        String fromDate = "19-07-2018";
        String weeksahead ="6";
        String direction = "True";
        System.out.println(zim.getPageNumber(zim.getTable(client,portCode,portDistinationCode,fromDate,weeksahead,direction)));
//        zim.analysisTableMessage(zim.getTable(client,portCode,portDistinationCode,fromDate,weeksahead,direction));


    }
}
