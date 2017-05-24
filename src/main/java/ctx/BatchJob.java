package ctx;

import ctx.crawling.CretecDoc;
import ctx.crawling.HttpDoc;
import ctx.domain.Item;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chaester on 2017-05-10.
 */
public class BatchJob {
    public static void main(String[] arg) throws Exception {
        Map mLoginInfo = new HashMap<String, Object>();
        mLoginInfo.put("idName", "id");
        mLoginInfo.put("pwName", "password");
        mLoginInfo.put("id", "admin");
        mLoginInfo.put("pw", "1111");

        CretecDoc cretecDoc = new CretecDoc("con");
        cretecDoc.Login(mLoginInfo);
        BufferedReader bufferedReader = cretecDoc.getCsvData();
        StringBuffer resultSb = new StringBuffer();
        String line = "";

        while ( (line = bufferedReader.readLine()) != null ) {
            Item item = new Item();

            resultSb.append(line);
            System.out.println(line.toString());
        }

    }
}

