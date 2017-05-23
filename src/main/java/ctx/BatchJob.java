package ctx;

import ctx.crawling.HttpDoc;
import org.apache.http.NameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chaester on 2017-05-10.
 */
public class BatchJob {
    public static void main(String[] arg) throws Exception {
        HttpDoc httpDoc = new HttpDoc();
        Map mLoginInfo = new HashMap<String, Object>();
        mLoginInfo.put("idName", "id");
        mLoginInfo.put("pwName", "password");
        mLoginInfo.put("id", "admin");
        mLoginInfo.put("pw", "1111");
        httpDoc.setNetWid("http://con.toolpark.kr/mng/login.do", mLoginInfo);
String a = httpDoc.GetPageContent("http://con.toolpark.kr/Admin/Goods.do");
        Document doc = Jsoup.parse(a);

        Element loginform = doc.getElementsByTag("form").get(3);
        System.out.println(loginform.toString());

    }
}
