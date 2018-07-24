package bnuz;

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
        String searchUrl = "https://www.yangming.com/e-service/schedule/PointToPointResult.aspx";
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
        yangMingSearchDataMap.put("__EVENTTARGET","ctl00$ContentPlaceHolder1$btnSearch0");
        yangMingSearchDataMap.put("__EVENTARGUMENT","");
        yangMingSearchDataMap.put("__VIEWSTATE","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ZGlzcGxheTpub25lO2QCJw9kFgJmD2QWAgIEDw8WAh8CBeAaPGxpIGNsYXNzPSdkcm9wZG93biBkcm9wZG93bi1zdWJtZW51IG1lbnVfbGknPiA8YSB0YWJpbmRleCA9Jy0xJyBjbGFzcz0nZHJvcGRvd24tdG9nZ2xlJyAgZGF0YS10b2dnbGU9J2Ryb3Bkb3duJyBocmVmPScjJyBvbmNsaWNrPXNldExvY2FsU2l0ZSgnQkUnLCdlbi11cycpOyA+IEJlbGdpdW08L2E+PC9saT48bGkgY2xhc3M9J2Ryb3Bkb3duIGRyb3Bkb3duLXN1Ym1lbnUgbWVudV9saSc+IDxhIHRhYmluZGV4ID0nLTEnIGNsYXNzPSdkcm9wZG93bi10b2dnbGUnICBkYXRhLXRvZ2dsZT0nZHJvcGRvd24nIGhyZWY9JyMnIG9uY2xpY2s9c2V0TG9jYWxTaXRlKCdDQScsJ2VuLXVzJyk7ID4gQ2FuYWRhPC9hPjwvbGk+PGxpIGNsYXNzPSdkcm9wZG93biBkcm9wZG93bi1zdWJtZW51IG1lbnVfbGknPiA8YSB0YWJpbmRleCA9Jy0xJyBjbGFzcz0nZHJvcGRvd24tdG9nZ2xlJyAgZGF0YS10b2dnbGU9J2Ryb3Bkb3duJyBocmVmPScjJyBvbmNsaWNrPXNldExvY2FsU2l0ZSgnQ04nLCd6aC1jbicpOyA+IENoaW5hKOeugOS9k+S4reaWhyk8L2E+PC9saT48bGkgY2xhc3M9J2Ryb3Bkb3duIGRyb3Bkb3duLXN1Ym1lbnUgbWVudV9saSc+IDxhIHRhYmluZGV4ID0nLTEnIGNsYXNzPSdkcm9wZG93bi10b2dnbGUnICBkYXRhLXRvZ2dsZT0nZHJvcGRvd24nIGhyZWY9JyMnIG9uY2xpY2s9c2V0TG9jYWxTaXRlKCdISycsJ3poLXR3Jyk7ID4gQ2hpbmEtSG9uZyBLb25nKOe5gemrlOS4reaWhyk8L2E+PC9saT48bGkgY2xhc3M9J2Ryb3Bkb3duIGRyb3Bkb3duLXN1Ym1lbnUgbWVudV9saSc+IDxhIHRhYmluZGV4ID0nLTEnIGNsYXNzPSdkcm9wZG93bi10b2dnbGUnICBkYXRhLXRvZ2dsZT0nZHJvcGRvd24nIGhyZWY9JyMnIG9uY2xpY2s9c2V0TG9jYWxTaXRlKCdFRycsJ2VuLXVzJyk7ID4gRWd5cHQ8L2E+PC9saT48bGkgY2xhc3M9J2Ryb3Bkb3duIGRyb3Bkb3duLXN1Ym1lbnUgbWVudV9saSc+IDxhIHRhYmluZGV4ID0nLTEnIGNsYXNzPSdkcm9wZG93bi10b2dnbGUnICBkYXRhLXRvZ2dsZT0nZHJvcGRvd24nIGhyZWY9JyMnIG9uY2xpY2s9c2V0TG9jYWxTaXRlKCdERScsJ2VuLXVzJyk7ID4gR2VybWFueTwvYT48L2xpPjxsaSBjbGFzcz0nZHJvcGRvd24gZHJvcGRvd24tc3VibWVudSBtZW51X2xpJz4gPGEgdGFiaW5kZXggPSctMScgY2xhc3M9J2Ryb3Bkb3duLXRvZ2dsZScgIGRhdGEtdG9nZ2xlPSdkcm9wZG93bicgaHJlZj0nIycgb25jbGljaz1zZXRMb2NhbFNpdGUoJ0lOJywnZW4tdXMnKTsgPiBJbmRpYTwvYT48L2xpPjxsaSBjbGFzcz0nZHJvcGRvd24gZHJvcGRvd24tc3VibWVudSBtZW51X2xpJz4gPGEgdGFiaW5kZXggPSctMScgY2xhc3M9J2Ryb3Bkb3duLXRvZ2dsZScgIGRhdGEtdG9nZ2xlPSdkcm9wZG93bicgaHJlZj0nIycgb25jbGljaz1zZXRMb2NhbFNpdGUoJ0lUJywnZW4tdXMnKTsgPiBJdGFseTwvYT48L2xpPjxsaSBjbGFzcz0nZHJvcGRvd24gZHJvcGRvd24tc3VibWVudSBtZW51X2xpJz4gPGEgdGFiaW5kZXggPSctMScgY2xhc3M9J2Ryb3Bkb3duLXRvZ2dsZScgIGRhdGEtdG9nZ2xlPSdkcm9wZG93bicgaHJlZj0nIycgb25jbGljaz1zZXRMb2NhbFNpdGUoJ0pQJywnamEtanAnKTsgPiBKYXBhbijml6XmnKzoqp4pPC9hPjwvbGk+PGxpIGNsYXNzPSdkcm9wZG93biBkcm9wZG93bi1zdWJtZW51IG1lbnVfbGknPiA8YSB0YWJpbmRleCA9Jy0xJyBjbGFzcz0nZHJvcGRvd24tdG9nZ2xlJyAgZGF0YS10b2dnbGU9J2Ryb3Bkb3duJyBocmVmPScjJyBvbmNsaWNrPXNldExvY2FsU2l0ZSgnS1InLCdrby1LUicpOyA+IEtvcmVhKO2VnOq1reyWtCk8L2E+PC9saT48bGkgY2xhc3M9J2Ryb3Bkb3duIGRyb3Bkb3duLXN1Ym1lbnUgbWVudV9saSc+IDxhIHRhYmluZGV4ID0nLTEnIGNsYXNzPSdkcm9wZG93bi10b2dnbGUnICBkYXRhLXRvZ2dsZT0nZHJvcGRvd24nIGhyZWY9JyMnIG9uY2xpY2s9c2V0TG9jYWxTaXRlKCdNWScsJ2VuLXVzJyk7ID4gTWFsYXlzaWE8L2E+PC9saT48bGkgY2xhc3M9J2Ryb3Bkb3duIGRyb3Bkb3duLXN1Ym1lbnUgbWVudV9saSc+IDxhIHRhYmluZGV4ID0nLTEnIGNsYXNzPSdkcm9wZG93bi10b2dnbGUnICBkYXRhLXRvZ2dsZT0nZHJvcGRvd24nIGhyZWY9JyMnIG9uY2xpY2s9c2V0TG9jYWxTaXRlKCdOTCcsJ2VuLXVzJyk7ID4gTmV0aGVybGFuZHM8L2E+PC9saT48bGkgY2xhc3M9J2Ryb3Bkb3duIGRyb3Bkb3duLXN1Ym1lbnUgbWVudV9saSc+IDxhIHRhYmluZGV4ID0nLTEnIGNsYXNzPSdkcm9wZG93bi10b2dnbGUnICBkYXRhLXRvZ2dsZT0nZHJvcGRvd24nIGhyZWY9JyMnIG9uY2xpY2s9c2V0TG9jYWxTaXRlKCdQSCcsJ2VuLXVzJyk7ID4gUGhpbGlwcGluZXM8L2E+PC9saT48bGkgY2xhc3M9J2Ryb3Bkb3duIGRyb3Bkb3duLXN1Ym1lbnUgbWVudV9saSc+IDxhIHRhYmluZGV4ID0nLTEnIGNsYXNzPSdkcm9wZG93bi10b2dnbGUnICBkYXRhLXRvZ2dsZT0nZHJvcGRvd24nIGhyZWY9JyMnIG9uY2xpY2s9c2V0TG9jYWxTaXRlKCdTRycsJ2VuLXVzJyk7ID4gU2luZ2Fwb3JlPC9hPjwvbGk+PGxpIGNsYXNzPSdkcm9wZG93biBkcm9wZG93bi1zdWJtZW51IG1lbnVfbGknPiA8YSB0YWJpbmRleCA9Jy0xJyBjbGFzcz0nZHJvcGRvd24tdG9nZ2xlJyAgZGF0YS10b2dnbGU9J2Ryb3Bkb3duJyBocmVmPScjJyBvbmNsaWNrPXNldExvY2FsU2l0ZSgnVFcnLCd6aC10dycpOyA+IFRhaXdhbijnuYHpq5TkuK3mlocpPC9hPjwvbGk+PGxpIGNsYXNzPSdkcm9wZG93biBkcm9wZG93bi1zdWJtZW51IG1lbnVfbGknPiA8YSB0YWJpbmRleCA9Jy0xJyBjbGFzcz0nZHJvcGRvd24tdG9nZ2xlJyAgZGF0YS10b2dnbGU9J2Ryb3Bkb3duJyBocmVmPScjJyBvbmNsaWNrPXNldExvY2FsU2l0ZSgnVFInLCdlbi11cycpOyA+IFR1cmtleShUdXJraXNoKTwvYT48L2xpPjxsaSBjbGFzcz0nZHJvcGRvd24gZHJvcGRvd24tc3VibWVudSBtZW51X2xpJz4gPGEgdGFiaW5kZXggPSctMScgY2xhc3M9J2Ryb3Bkb3duLXRvZ2dsZScgIGRhdGEtdG9nZ2xlPSdkcm9wZG93bicgaHJlZj0nIycgb25jbGljaz1zZXRMb2NhbFNpdGUoJ0FFJywnZW4tdXMnKTsgPiBVQUU8L2E+PC9saT48bGkgY2xhc3M9J2Ryb3Bkb3duIGRyb3Bkb3duLXN1Ym1lbnUgbWVudV9saSc+IDxhIHRhYmluZGV4ID0nLTEnIGNsYXNzPSdkcm9wZG93bi10b2dnbGUnICBkYXRhLXRvZ2dsZT0nZHJvcGRvd24nIGhyZWY9JyMnIG9uY2xpY2s9c2V0TG9jYWxTaXRlKCdHQicsJ2VuLXVzJyk7ID4gVW5pdGVkIEtpbmdkb208L2E+PC9saT48bGkgY2xhc3M9J2Ryb3Bkb3duIGRyb3Bkb3duLXN1Ym1lbnUgbWVudV9saSc+IDxhIHRhYmluZGV4ID0nLTEnIGNsYXNzPSdkcm9wZG93bi10b2dnbGUnICBkYXRhLXRvZ2dsZT0nZHJvcGRvd24nIGhyZWY9JyMnIG9uY2xpY2s9c2V0TG9jYWxTaXRlKCdWTicsJ2VuLXVzJyk7ID4gVmlldG5hbTwvYT48L2xpPmRkAgsPZBYCAgsPZBYCZg9kFgQCBQ8PZBYCHgdvbmZvY3VzBTppZih0aGlzLnZhbHVlPT0nKElucHV0IGxvY2F0aW9uIG5hbWUuLi4pJyl7dGhpcy52YWx1ZT0nJzt9ZAIVDw9kFgIfBAU6aWYodGhpcy52YWx1ZT09JyhJbnB1dCBsb2NhdGlvbiBuYW1lLi4uKScpe3RoaXMudmFsdWU9Jyc7fWRkbkBkY+unn3ATnt4aCiWXQFc3C5aPwCbf/KCz8a/kxzg=");
        yangMingSearchDataMap.put("__VIEWSTATEGENERATOR","D33A6342");
        yangMingSearchDataMap.put("__PREVIOUSPAGE","z1UQqKr4NIfsci_1WpKvVznekqWGPFvUncZIeWo9eJcDPe5x_xB-a5-ZQs2Bjc--T0ayjpDtKNhSYfXTBuA7QbSyab6mIvq2kgYVkr32vStLqy_gzOCJ_15j1Juq0kZ70");
        yangMingSearchDataMap.put("__EVENTVALIDATION","/wEdACH3clAByf3FBzA/uPTnOx9e0kzCBn0GUXaybVvEgpGhJBan8Hipa+RH80BtXbRl05cVKm2QObfOHv4blEBfrAgEQSDCMfw7FN3se4IlFUFNZwcT55ELczTiJdZdBbjd+jjgfUbRDP1Za3tqCVuKq3JEilszF1fEdRbrNJpbE3QLQGeUoYRkJtnNa5MIrup723GrO92XpGZzAjiJf/VTxtfRD8S/fVI8QOjmC5BCjLpbvOvR4V3EgbS0+ZZwcCcLplGrtYCiLBSnHdjIHVISU8+uTyVDPw+3qib0zZbchOgQMlEpdxvAzt5ef+KRyITlk22h9amUJ2dDm3gSYCRKJGNHPIBMIlmoQr48AB2TxQ2k4iHphYGS2Lf3mSRP5PZTxXd6HsoKAHqxj0BaaK8j40jC7tXrQkaCZsF4Vg8pfpiEl5MIRavcM5wp9qdF8poLUO9MFHioACK2YKaC7+2L/SzFsoIURPWEGLaERW4ekWpy+P3gIYsvzHRVFW491SRlYwHgg7TtCeg6rRhLTIXR9Qki9t1R2+o6ukNjDoWZCb/kEu/Bb9PMbK2A0Ag1gOnv/x6wClVjTow4Dwk4eTrDtriUP2hEDXmAcFqlTozVJVa8MVplsC0GCkw1CPkPiRij+afxTTULuqaFeXiMeAfnAFTFne3EdM02HHYSdp0nfVbq6RjyRBlqndtqTdICG7mbrKbi5iGcSa6VWf9iFKVO/diAQAb4wglh/cyU7pyMDennSg==");
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
        yangMingSearchDataMap.put("ctl00$ContentPlaceHolder1$date_Start","2018/07/23");
        yangMingSearchDataMap.put("ctl00$ContentPlaceHolder1$date_End","2018/08/22");
        yangMingSearchDataMap.put("ctl00$ContentPlaceHolder1$hiddate_Start","2018/07/23");
        yangMingSearchDataMap.put("ctl00$ContentPlaceHolder1$hiddate_End","2018/08/22");


        Map yangMingSearchHeaderMap = new HashMap();
        yangMingSearchHeaderMap.put("User-Agent","User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
        yangMingSearchHeaderMap.put("Content-Type","application/x-www-form-urlencoded");

        ym.getHtmlByPost(client,searchUrl,yangMingSearchDataMap,yangMingSearchHeaderMap);
    }
}
