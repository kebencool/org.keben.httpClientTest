package com.bnuz;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Ne {

	public static void main(String[] args) throws IOException {
		System.out.println("开始");

		//发送一个get请求去建立session, 获得相应的sessionId
		String urlForSession = "https://www.yangming.com/e-service/schedule/PointToPoint.aspx";
		Connection con1 = Jsoup.connect(urlForSession);
		Response resp = con1.method(Method.GET).execute();
		String sessionId = resp.cookie("ASP.NET_SessionId");
		System.out.println("新建立一个session, id是 " + sessionId);

		String url = "https://o-www.yangming.com/e-service/schedule/PointToPointResult.aspx";
		Connection con = Jsoup.connect(url);

		// 添加post请求参数
		con.data("__EVENTTARGET", "ctl00$ContentPlaceHolder1$btnSearch0");
		con.data("__EVENTARGUMENT", "");
		con.data("__VIEWSTATE",
				"/wEPDwUJMzM4OTEwMjY5D2QWAmYPZBYCAgMPZBYEAgcPZBYGAgEPFgIeBXN0eWxlBQ1kaXNwbGF5Om5vbmU7ZAIFD2QWBAIBD2QWBgI4DxYCHgdWaXNpYmxlaGQCVQ8WAh8BaGQCXQ8WAh8BZxYCZg9kFgICAw9kFgICAw8PFgIeBFRleHQFmgk8ZGw+PGR0Pjxmb250IHNpemU9Mj5Mb2NhbCBJbmZvPC9mb250PjwvZHQ+PGRkPjxhIGhyZWYgPScjJyBvbmNsaWNrPXNldExvY2FsU2l0ZSgnQkUnLCdlbi11cycpOyA+IEJlbGdpdW08L2E+PC9kZD48ZGQ+PGEgaHJlZiA9JyMnIG9uY2xpY2s9c2V0TG9jYWxTaXRlKCdDQScsJ2VuLXVzJyk7ID4gQ2FuYWRhPC9hPjwvZGQ+PGRkPjxhIGhyZWYgPScjJyBvbmNsaWNrPXNldExvY2FsU2l0ZSgnQ04nLCd6aC1jbicpOyA+IENoaW5hKOeugOS9k+S4reaWhyk8L2E+PC9kZD48ZGQ+PGEgaHJlZiA9JyMnIG9uY2xpY2s9c2V0TG9jYWxTaXRlKCdISycsJ3poLXR3Jyk7ID4gQ2hpbmEtSG9uZyBLb25nKOe5gemrlOS4reaWhyk8L2E+PC9kZD48L2RsPjxkbD48ZHQ+Jm5ic3A7PC9kdD48ZGQ+PGEgaHJlZiA9JyMnIG9uY2xpY2s9c2V0TG9jYWxTaXRlKCdFRycsJ2VuLXVzJyk7ID4gRWd5cHQ8L2E+PC9kZD48ZGQ+PGEgaHJlZiA9JyMnIG9uY2xpY2s9c2V0TG9jYWxTaXRlKCdJVCcsJ2VuLXVzJyk7ID4gSXRhbHk8L2E+PC9kZD48ZGQ+PGEgaHJlZiA9JyMnIG9uY2xpY2s9c2V0TG9jYWxTaXRlKCdKUCcsJ2phLWpwJyk7ID4gSmFwYW4o5pel5pys6KqeKTwvYT48L2RkPjxkZD48YSBocmVmID0nIycgb25jbGljaz1zZXRMb2NhbFNpdGUoJ01ZJywnZW4tdXMnKTsgPiBNYWxheXNpYTwvYT48L2RkPjwvZGw+PGRsPjxkdD4mbmJzcDs8L2R0PjxkZD48YSBocmVmID0nIycgb25jbGljaz1zZXRMb2NhbFNpdGUoJ05MJywnZW4tdXMnKTsgPiBOZXRoZXJsYW5kczwvYT48L2RkPjxkZD48YSBocmVmID0nIycgb25jbGljaz1zZXRMb2NhbFNpdGUoJ1BIJywnZW4tdXMnKTsgPiBQaGlsaXBwaW5lczwvYT48L2RkPjxkZD48YSBocmVmID0nIycgb25jbGljaz1zZXRMb2NhbFNpdGUoJ1NHJywnZW4tdXMnKTsgPiBTaW5nYXBvcmU8L2E+PC9kZD48ZGQ+PGEgaHJlZiA9JyMnIG9uY2xpY2s9c2V0TG9jYWxTaXRlKCdUVycsJ3poLXR3Jyk7ID4gVGFpd2FuKOe5gemrlOS4reaWhyk8L2E+PC9kZD48L2RsPjxkbD48ZHQ+Jm5ic3A7PC9kdD48ZGQ+PGEgaHJlZiA9JyMnIG9uY2xpY2s9c2V0TG9jYWxTaXRlKCdHQicsJ2VuLXVzJyk7ID4gVW5pdGVkIEtpbmdkb208L2E+PC9kZD48ZGQ+PGEgaHJlZiA9JyMnIG9uY2xpY2s9c2V0TG9jYWxTaXRlKCdWTicsJ2VuLXVzJyk7ID4gVmlldG5hbTwvYT48L2RkPmRkAgMPFgIfAWgWAgINDxYCHwFoZAIGD2QWBGYPD2QWAh4FU3R5bGUFLHBhZGRpbmctdG9wOjVweDtwYWRkaW5nLWJvdHRvbTo1cHg7ZGlzcGxheTo7ZAIBDw9kFgIfAwUwcGFkZGluZy10b3A6NXB4O3BhZGRpbmctYm90dG9tOjVweDtkaXNwbGF5Om5vbmU7ZAILD2QWAgILD2QWAmYPZBYEAgUPD2QWAh4Hb25mb2N1cwU6aWYodGhpcy52YWx1ZT09JyhJbnB1dCBsb2NhdGlvbiBuYW1lLi4uKScpe3RoaXMudmFsdWU9Jyc7fWQCFQ8PZBYCHwQFOmlmKHRoaXMudmFsdWU9PScoSW5wdXQgbG9jYXRpb24gbmFtZS4uLiknKXt0aGlzLnZhbHVlPScnO31kZGpbxgw6kjly3CmxpqbVJYaKGK1IqD4RhxkU7aLEV9Rt");
		con.data("__VIEWSTATEGENERATOR", "D33A6342");
		con.data("__PREVIOUSPAGE",
				"47M2I5jX07T05rnRzTJ_fzmBpYhbxLt6_3L_ZdvL89IlBon4UhuN38MlR0a24SQLG8sWSI_YOxlO1lCf42HNptM2ler2yvvCx9AfbXr9CWh7b7W764Qiel7j2pNFmuR80");
		con.data("__EVENTVALIDATION",
				"/wEdAB5f/EgTgsnGa2OfBb3luOmA0kzCBn0GUXaybVvEgpGhJBan8Hipa+RH80BtXbRl05cVKm2QObfOHv4blEBfrAgEQSDCMfw7FN3se4IlFUFNZwcT55ELczTiJdZdBbjd+jjgfUbRDP1Za3tqCVuKq3JEilszF1fEdRbrNJpbE3QLQGeUoYRkJtnNa5MIrup723GrO92XpGZzAjiJf/VTxtfRD8S/fVI8QOjmC5BCjLpbvOvR4V3EgbS0+ZZwcCcLplGh9amUJ2dDm3gSYCRKJGNHPIBMIlmoQr48AB2TxQ2k4iHphYGS2Lf3mSRP5PZTxXd6HsoKAHqxj0BaaK8j40jC7tXrQkaCZsF4Vg8pfpiEl5MIRavcM5wp9qdF8poLUO9MFHioACK2YKaC7+2L/SzFsoIURPWEGLaERW4ekWpy+P3gIYsvzHRVFW491SRlYwHgg7TtCeg6rRhLTIXR9Qki9t1R2+o6ukNjDoWZCb/kEu/Bb9PMbK2A0Ag1gOnv/x6wClVjTow4Dwk4eTrDtriUP2hEDXmAcFqlTozVJVa8MVplsC0GCkw1CPkPiRij+afxTTULuqaFeXiMeAfnAFTFne3EdM02HHYSdp0nfVbq6RjyRBlqndtqTdICG7mbrKYxZoVgBMKJMOYSnBozhIx3lyJ91geybIqqvJHdgexM0Q==");

		con.data("ctl00$hidButtonType", "0");
		con.data("ctl00$hfIsInIframe", "");
		con.data("ctl00$ctl142$hidlocalver", "EN");
		con.data("ctl00$ctl142$hidLang_ver", "en-us");

		// Hong Kong to Los ANGELES

					con.data("ctl00$ContentPlaceHolder1$txtFrom","HONG+KONG,+Hong+Kong");
					con.data("ctl00$ContentPlaceHolder1$hidFrom","HKHKG"); //在这段语句中,这条语句是必须的
					con.data("ctl00$ContentPlaceHolder1$hidFrom_txt","HONG+");
					con.data("ctl00$ContentPlaceHolder1$radServiceTerm1","0");
					con.data("ctl00$ContentPlaceHolder1$txtTo","LOS+ANGELES,+CA,+USA");
					con.data("ctl00$ContentPlaceHolder1$hidTo","USLAX"); //在这段语句中,这条语句是必须的
					con.data("ctl00$ContentPlaceHolder1$hidTo_txt","lOS");
					con.data("ctl00$ContentPlaceHolder1$radServiceTerm2","0");

		// singapore to sydney
		//			con.data("ctl00$ContentPlaceHolder1$txtFrom","SINGAPORE,+Singapore");
		//			con.data("ctl00$ContentPlaceHolder1$hidFrom","SGSIN");
		//			con.data("ctl00$ContentPlaceHolder1$hidFrom_txt","Si");
		//			con.data("ctl00$ContentPlaceHolder1$radServiceTerm1","0");
		//			con.data("ctl00$ContentPlaceHolder1$txtTo","SYDNEY,+Australia");
		//			con.data("ctl00$ContentPlaceHolder1$hidTo","AUSYD");
		//			con.data("ctl00$ContentPlaceHolder1$hidTo_txt","SYD");
		//			con.data("ctl00$ContentPlaceHolder1$radServiceTerm2","0");

		// 连云港 to 西雅图
		//			con.data("ctl00$ContentPlaceHolder1$hidFrom","CNLYG");
		//			con.data("ctl00$ContentPlaceHolder1$hidTo","USSEA");

		// 宁波 to 长滩(美国)
		//			con.data("ctl00$ContentPlaceHolder1$hidFrom","CNNGB");
		//			con.data("ctl00$ContentPlaceHolder1$hidTo","USLGB");

		// 名古屋(日本) to 勒阿弗尔(法国)
//		con.data("ctl00$ContentPlaceHolder1$hidFrom", "JPNGO");
//		con.data("ctl00$ContentPlaceHolder1$hidTo", "FRLEH");

		//开始和结束的日期
		con.data("ctl00$ContentPlaceHolder1$date_Start", "2018/07/11");
		con.data("ctl00$ContentPlaceHolder1$date_End", "2018/08/10");
		con.data("ctl00$ContentPlaceHolder1$hiddate_Start", "2018/07/11");
		con.data("ctl00$ContentPlaceHolder1$hiddate_End", "2018/08/10");

		// 插入cookie(头文件形式)
		con.header("Host", "o-www.yangming.com");
		con.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
		con.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		con.header("Accept-Language", "en-US,en;q=0.5");
		con.header("Accept-Encoding", "gzip, deflate, br");
		con.header("Referer", "https://o-www.yangming.com/e-service/schedule/PointToPoint.aspx");
		//			con.header("Cookie","ver=EN; ymweb=3454797322.20480.0000; local_site=N");
		//			con.header("Cookie",
		//					"ASP.NET_SessionId=fm2mlbhdfnnncj3qsjczu4tb; ver=EN; ymweb=3454797322.20480.0000; local_site=N"); // 必须要有个sessionid,需要从浏览器控制台获取,
		//																														// 否则爬不到结果
		con.header("Cookie", "ASP.NET_SessionId=" + sessionId + "; ver=EN; ymweb=3454797322.20480.0000; local_site=N"); // sessionId已经可以通过前面的代码获取了
		con.header("Connection", "keep-alive");
		con.header("Upgrade-Insecure-Requests", "1");

		Document doc = con.post();
		System.out.println(doc);
		Elements trs = doc.select("table#ContentPlaceHolder1_gvRouting thead tr");
		int cols = 0;
		final int colWidth = 18;
		for (Element tr : trs) { // 打印表头
			Elements ths = tr.select("th");
			if (cols == 0) {
				cols = ths.size(); // 只获取第一行的列数
			}
			for (int i = 0; i < ths.size() - 1; i++) {
				String text = ths.get(i).text();
				if (i == 7) {
					text += "|Ocean Days";
				}
				System.out.printf("%-" + colWidth + "s\t", text);
			}
		}
		System.out.println();

		for (int i = 0; i < cols * (colWidth + 4); i++) { // 打印分割线
			System.out.print("-");
		}
		System.out.println();

		trs = doc.select("table#ContentPlaceHolder1_gvRouting tbody tr");
		for (Element tr : trs) { // 打印表格数据
			Elements tds = tr.select("td");
			for (int i = 0; i < tds.size() - 1; i++) {
				System.out.printf("%-" + colWidth + "s\t", tds.get(i).text());
			}
			System.out.println();
		}
		System.out.println("结束");
	}
}
