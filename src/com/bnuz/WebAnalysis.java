package bnuz;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Keben on 2018-07-22.
 */
public abstract class WebAnalysis {

    /**
     * 解析html表格中的信息
     * @param resultHtml
     * @return
     */
    public abstract void analysisMessage(List tables, String resultHtml);

    /**
     * 获取表格页数
     * @param resultHtml
     * @return
     */
    public abstract int getPageNumber(String resultHtml);
    /**
     * 模拟ajax获取查询结果页面html
     * @param client
     * @return
     */

    public String getHtml(CloseableHttpClient client, String url , Map params, Map header){

        String resultHtml = null;
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(url);
            ArrayList<NameValuePair> queryList = new ArrayList<NameValuePair>();

            //迭代参数容器
            Iterator<Map.Entry<String,String>> entryIterator = params.entrySet().iterator();
            Map.Entry<String,String > entry = null;
            while (entryIterator.hasNext()){
                entry = entryIterator.next();
                if (entry.getValue()!=null){
                    NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                    queryList.add(nameValuePair);
                }

            }
            uriBuilder.addParameters(queryList);
            HttpGet get = new HttpGet(uriBuilder.build());

            //迭代头文件容器
            entryIterator = header.entrySet().iterator();
            while (entryIterator.hasNext()){
                entry = entryIterator.next();
                get.setHeader(entry.getKey(),entry.getValue());
            }


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
}
