package com.bnuz;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YangMing extends WebAnalysis{

    public void analysisMessage(List tables, String resultHtml) {

    }

    public int getPageNumber(String resultHtml) {
        return 0;
    }

    public String getPortCode(String portMessage) {
        String result = null;
        if (portMessage == null){
            System.out.println("log : analysis port code fail.");
        }
        String[] strings = portMessage.split("\"");
        result = strings[3];
        return result;
    }


    public static void main(String[] args) {
        String url = "";
        String portUrl = "https://o-www.yangming.com/e-service/schedule/PointToPoint_LocList.ashx";
        CloseableHttpClient client = HttpClients.createDefault();
        YangMing ym = new YangMing();

        //获取出发港口
        Map portCodeMap = new HashMap();
        portCodeMap.put("q","hong ");
        portCodeMap.put("limit","99999");
        portCodeMap.put("timestamp",System.currentTimeMillis()+"");
        portCodeMap.put("p_Type","F");
        portCodeMap.put("p_floc","");
        Map portHeaderMap = new HashMap();
        portHeaderMap.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
        String portCode = ym.getPortCode(ym.getHtml(client,portUrl,portCodeMap,portHeaderMap));
        System.out.println(portCode);


        //获取终点港口
        Map portDestinationCodeMap = new HashMap();
        portDestinationCodeMap.put("q","los an");
        portDestinationCodeMap.put("limit","99999");
        portDestinationCodeMap.put("timestamp",System.currentTimeMillis()+"");
        portDestinationCodeMap.put("p_Type","T");
        portDestinationCodeMap.put("p_floc",portCode);
        String portDestinationCode = ym.getPortCode(ym.getHtml(client,portUrl,portDestinationCodeMap,portHeaderMap));
        System.out.println(portDestinationCode);

        //调参


    }
}
