package ctx.crawling;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chaester on 2017-05-24.
 */
public class CretecDoc extends HttpDoc {
    private String domain;
    public CretecDoc(String domain) {
        this.domain = domain;
        setNetWid();
    }
    public void Login(Map<String, Object> mLoginInfo) throws Exception {
        String page = GetPageContent("http://" + domain + ".toolpark.kr/mng/login.do");
        List<NameValuePair> postParams = getFormParams(page, mLoginInfo);
        BufferedReader resultBr = sendPost("http://" + domain + ".toolpark.kr/uat/uia/actionSecurityLogin.do", postParams);
        StringBuffer resultSb = new StringBuffer();
        String line = "";
        while ( (line = resultBr.readLine()) != null ) {
            resultSb.append(line);
        }
        String[] token = resultSb.toString().split("/");
        GetPageContent("http://" + domain + ".toolpark.kr/uat/uia/jsecurity_check.do?id=" + token[1] + "&pass=" + token[2]);
    }
    public BufferedReader getCsvItemData() throws Exception {
        String GoodData = GetPageContent("http://" + domain + ".toolpark.kr/Admin/Goods.do");
        Document doc = Jsoup.parse(GoodData);
        Element loginform = doc.getElementsByTag("form").get(2);
        Elements inputElements = loginform.getElementsByTag("input");
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");
            if (key.equals("bass_goods_nm"))
                value = "";
            else if (key.equals("brand"))
                value = "";
            else if (key.equals("goods_code"))
                value = "";
            else if (key.equals("goods_gnrl_info"))
                value = "";
            else if (key.equals("model_nm"))
                value = "";
            else if ( key.equals("c_trtmnt_goods_yn") )
                break;
            else if ( key.equals("radio1") )
                break;

            paramList.add(new BasicNameValuePair(key, value));
        }
        paramList.add(new BasicNameValuePair("c_trtmnt_goods_yn", "y"));
        paramList.add(new BasicNameValuePair("radio1", "0"));

        return sendPost("http://" + domain + ".toolpark.kr/Admin/GoodsExcel.do", paramList);

    }
}
