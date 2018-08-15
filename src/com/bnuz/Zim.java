package com.bnuz;

import org.apache.http.Header;
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
import java.util.*;


/**
 * Created by Keben on 2018-07-19.
 */
public class Zim extends WebAnalysis {

    /**
     * 在下拉栏获取并切割出第一个港口信息
     * @param portMessage
     * @return
     */
    @Override
    public String getPortCode(String portMessage){
        String result = null;
        if (portMessage == null){
            System.out.println("log : analysis port code fail.");
        }
        String[] strings = portMessage.split("\"");
        result = strings[9];
        return result;
    }

    /**
     * 解析html表格中的信息
     * @param resultHtml
     * @return
     */
    @Override
    public void analysisMessage(List tables, String resultHtml){
        Document document = Jsoup.parse(resultHtml);
//        System.out.println(document.title());
        Elements tbody = document.select("tbody[class=p2p-tbody]");
//        System.out.println(tbody);
        Elements trs = tbody.select("tr");

//        List<List<List<String>>> tables = new ArrayList<List<List<String>>>();
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
//        System.out.println(tables.get(0).toString());
//        return tables;
    }

    /**
     * 获取表格页数
     * @param resultHtml
     * @return
     */
    @Override
    public int getPageNumber(String resultHtml){
        Document document = Jsoup.parse(resultHtml);
        Elements ul = document.select("ul[class=paging-list]");
        Elements as = ul.get(0).select("a");
        Elements except = ul.get(0).select("a[rel]");
        int number = as.size()- except.size();
        return number;
    }
    public static void main(String[] args) throws IOException {
        String url = "https://www.zim.com/schedules/point-to-point";
        String portUrl = "https://www.zim.com/umbraco/surface/ScheduleByRoute/GetPortsInLands";
        CloseableHttpClient client = HttpClients.createDefault();
        Zim zim = new Zim();
        int pageNumber = 0;

//        获取港口信息
        Map portParamMap = new HashMap();
        portParamMap.put("query","hong");
        Map portHeaderMap = new HashMap();
        portHeaderMap.put("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
        String portCode = zim.getPortCode(zim.getHtmlByGet(client,portUrl,portParamMap,portHeaderMap));
        System.out.println(portCode);
        portParamMap.put("query","los");
        String portDestinationCode = zim.getPortCode(zim.getHtmlByGet(client,portUrl,portParamMap,portHeaderMap));
        System.out.println(portDestinationCode);

//        模拟查询
        String fromDate = "19-07-2018";
        String weeksahead ="6";
        String direction = "True";
        List<List<List<String>>> tables = new ArrayList<List<List<String>>>();
        Map paramMap = new HashMap();
        Map headerMap = new HashMap();
        paramMap.put("portcode",portCode);
        paramMap.put("portdestinationcode",portDestinationCode);
        paramMap.put("fromdate",fromDate);
        paramMap.put("weeksahead",weeksahead);
        paramMap.put("direction",direction);
        headerMap.put("Host","www.zim.com");
        headerMap.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");

        pageNumber = zim.getPageNumber(zim.getHtmlByGet(client,url,paramMap,headerMap));
        for (int i = 1 ; i <= pageNumber ; i++ ){
            paramMap.put("page",""+i);
            String html = zim.getHtmlByGet(client,url,paramMap,headerMap);
            zim.analysisMessage(tables,html);
        }
//        System.out.println(tables.toString());
        for (List<List<String>> table : tables){
            for (List<String> line : table){
                System.out.print("||");
                for (String s : line){
                    System.out.print(s+" || ");
                }
                System.out.println("c2");
            }
        }

    }
}
