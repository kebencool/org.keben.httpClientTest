package com.bnuz;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YangMing extends WebAnalysis{


    /**
     * 获取cookie
     * @return
     * @throws IOException
     */
    private String getCookie() throws IOException {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            client = HttpClients.createDefault();
            HttpGet get = new HttpGet("https://o-www.yangming.com/e-service/schedule/PointToPoint.aspx");
            String rawHeaders = "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\nAccept-Encoding: gzip, deflate, br\nAccept-Language: zh-CN,zh;q=0.9\nCache-Control: max-age=0\nConnection: keep-alive\nHost: o-www.yangming.com\nUpgrade-Insecure-Requests: 1\nUser-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.162 Safari/537.36";
            String[] splitHeaders = rawHeaders.split("\n");
            for (String s : splitHeaders) {
                String[] split = s.split(": ");
                get.addHeader(split[0], split[1]);
            }

            response = client.execute(get);

            String cookie =response.getFirstHeader("Set-Cookie").getValue();
            return cookie;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void analysisMessage(List tables, String resultHtml) {
        Document document = Jsoup.parse(resultHtml);
        Elements tbodys = document.select("tbody");
        Elements theadTDs = document.select("thead th");

        //获取表头
        List<String> data = new ArrayList<String>();
        for (Element td : theadTDs){
            data.add(td.ownText());
        }
        tables.add(data);
//        System.out.println(tbodys);

        //获取表数据

        Element tbody = tbodys.get(1);
        Elements trs = tbody.select("tr");
        for (Element tr : trs){
            data = new ArrayList<String>();
            Elements tds = tr.select("td");
            for (Element td : tds){
                if (td.select("td a").size()==0){
                    data.add(td.ownText());
                }else {
                    data.add(td.select("td a").get(0).attr("href"));
                }
            }
            tables.add(data);
        }
    }

    public int getPageNumber(String resultHtml) {
        return 0;
    }

    /**
     * 获取下拉栏港口
     * @param portMessage
     * @return
     */
    public String getPortCode(String portMessage) {
        String result = null;
        if (portMessage == null){
            System.out.println("log : analysis port code fail.");
        }
        String[] strings = portMessage.split("\"");
        result = strings[3];
        return result;
    }


    public static void main(String[] args) throws IOException {
        String homeUrl = "https://www.yangming.com/e-service/schedule/PointToPoint.aspx";
        String searchUrl = "https://www.yangming.com/e-service/schedule/PointToPointResult.aspx";
        String portUrl = "https://o-www.yangming.com/e-service/schedule/PointToPoint_LocList.ashx";
        String __EVENTTARGET = null;
        String __EVENTARGUMENT = "";
        String __VIEWSTATE = null;
        String __VIEWSTATEGENERATOR = null;
        String __PREVIOUSPAGE = null;
        String __EVENTVALIDATION = null;
        String cookie = null;


        CloseableHttpClient client = HttpClients.createDefault();
        YangMing ym = new YangMing();
        String pattern = null;
        Pattern r = null;
        Matcher m = null;
        String[] strings=null;

        //获取隐藏参数
        try {
            Document document = Jsoup.connect(homeUrl).get();
            Elements __EVENTTARGET_INPUT = document.select("input[id=ContentPlaceHolder1_btnSearch0]");
            System.out.println(__EVENTTARGET_INPUT.get(0).attr("onclick"));
            pattern ="(new WebForm_PostBackOptions(\\(\".+\",))";

            r = Pattern.compile(pattern);
            m = r.matcher(__EVENTTARGET_INPUT.get(0).attr("onclick"));
            System.out.println(m.groupCount());
            if (m.find()){
                strings = m.group(0).split("\"");
                __EVENTTARGET = strings[1];
            }
//            System.out.println(document);
            __VIEWSTATE = document.select("input[id=__VIEWSTATE]").get(0).attr("value");
            __VIEWSTATEGENERATOR = document.select("input[id=__VIEWSTATEGENERATOR]").get(0).attr("value");
            __PREVIOUSPAGE = document.select("input[id=__PREVIOUSPAGE]").get(0).attr("value");
            __EVENTVALIDATION = document.select("input[id=__EVENTVALIDATION]").get(0).attr("value");

        } catch (IOException e) {
            e.printStackTrace();
        }



        //获取出发港口
        Map portCodeMap = new HashMap();
        portCodeMap.put("q","hong ");
        portCodeMap.put("limit","99999");
        portCodeMap.put("timestamp",System.currentTimeMillis()+"");
        portCodeMap.put("p_Type","F");
        portCodeMap.put("p_floc","");
        Map portHeaderMap = new HashMap();
        portHeaderMap.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
        String portCode = ym.getPortCode(ym.getHtmlByGet(client,portUrl,portCodeMap,portHeaderMap));
        System.out.println(portCode);


        //获取终点港口
        Map portDestinationCodeMap = new HashMap();
        portDestinationCodeMap.put("q","los an");
        portDestinationCodeMap.put("limit","99999");
        portDestinationCodeMap.put("timestamp",System.currentTimeMillis()+"");
        portDestinationCodeMap.put("p_Type","T");
        portDestinationCodeMap.put("p_floc",portCode);
        String portDestinationCode = ym.getPortCode(ym.getHtmlByGet(client,portUrl,portDestinationCodeMap,portHeaderMap));
        System.out.println(portDestinationCode);


        //调参
        Map yangMingSearchDataMap = new HashMap();

        cookie = ym.getCookie();

        yangMingSearchDataMap.put("__EVENTTARGET",__EVENTTARGET);
        yangMingSearchDataMap.put("__EVENTARGUMENT",__EVENTARGUMENT);
        yangMingSearchDataMap.put("__VIEWSTATE",__VIEWSTATE);
        yangMingSearchDataMap.put("__VIEWSTATEGENERATOR",__VIEWSTATEGENERATOR);
        yangMingSearchDataMap.put("__PREVIOUSPAGE",__PREVIOUSPAGE);
        yangMingSearchDataMap.put("__EVENTVALIDATION",__EVENTVALIDATION);
        yangMingSearchDataMap.put("ctl00$hidButtonType","0");
        yangMingSearchDataMap.put("ctl00$hfIsInIframe","");
        yangMingSearchDataMap.put("ctl00$ctl142$hidlocalver","EN");
        yangMingSearchDataMap.put("ctl00$ctl142$hidLang_ver","en-us");
        yangMingSearchDataMap.put("ctl00$ctl143$hidlocalver","EN");
        yangMingSearchDataMap.put("ctl00$ctl143$hidLang_ver","en-us");
        yangMingSearchDataMap.put("ctl00$ContentPlaceHolder1$txtFrom","HONG KONG, Hong Kong");
        yangMingSearchDataMap.put("ctl00$ContentPlaceHolder1$hidFrom","HKHKG");
        yangMingSearchDataMap.put("ctl00$ContentPlaceHolder1$hidFrom_txt","hong k");
        yangMingSearchDataMap.put("ctl00$ContentPlaceHolder1$radServiceTerm1","0");
        yangMingSearchDataMap.put("ctl00$ContentPlaceHolder1$txtTo","LOS ANGELES, CA, USA");
        yangMingSearchDataMap.put("ctl00$ContentPlaceHolder1$hidTo","USLAX");
        yangMingSearchDataMap.put("ctl00$ContentPlaceHolder1$hidTo_txt","los");
        yangMingSearchDataMap.put("ctl00$ContentPlaceHolder1$radServiceTerm2","0");
        yangMingSearchDataMap.put("ctl00$ContentPlaceHolder1$date_Start","2018/07/25");
        yangMingSearchDataMap.put("ctl00$ContentPlaceHolder1$date_End","2018/08/31");
        yangMingSearchDataMap.put("ctl00$ContentPlaceHolder1$hiddate_Start","2018/07/25");
        yangMingSearchDataMap.put("ctl00$ContentPlaceHolder1$hiddate_End","2018/08/31");


        Map yangMingSearchHeaderMap = new HashMap();
        yangMingSearchHeaderMap.put("Cookie",cookie);
        yangMingSearchHeaderMap.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
        //重点 不是 o-www.yangming.com
        yangMingSearchHeaderMap.put("Host","www.yangming.com");
        yangMingSearchHeaderMap.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        yangMingSearchHeaderMap.put("Accept-Language","en-US,en;q=0.5");
        yangMingSearchHeaderMap.put("Accept-Encoding","gzip, deflate, br");
        yangMingSearchHeaderMap.put("Referer","https://www.yangming.com/e-service/schedule/PointToPoint.aspx");
        yangMingSearchHeaderMap.put("Connection","keep-alive");
        yangMingSearchHeaderMap.put("Upgrade-Insecure-Requests","1");
        yangMingSearchHeaderMap.put("Content-Type","application/x-www-form-urlencoded");
        yangMingSearchHeaderMap.put("Origin","https://www.yangming.com");

        String result = ym.getHtmlByPost(client,searchUrl,yangMingSearchDataMap,yangMingSearchHeaderMap);
        List<List<String>> tables = new ArrayList<List<String>>();
        ym.analysisMessage(tables,result);
        System.out.println(tables);
    }
}
