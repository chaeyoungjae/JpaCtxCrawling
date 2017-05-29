package ctx;

import ctx.crawling.CretecDoc;
import ctx.crawling.HttpDoc;
import ctx.domain.Category;
import ctx.domain.Item;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
        //ItemInfo();
        //cateInfo();
    }
    private static Map loginInfo() {
        Map mLoginInfo = new HashMap<String, Object>();
        mLoginInfo.put("idName", "id");
        mLoginInfo.put("pwName", "password");
        mLoginInfo.put("id", "admin");
        mLoginInfo.put("pw", "1111");
        return mLoginInfo;
    }

    private static void ItemInfo() throws Exception {
        Map mLoginInfo = loginInfo();

        CretecDoc cretecDoc = new CretecDoc("con");
        cretecDoc.Login(mLoginInfo);
        BufferedReader bufferedReader = cretecDoc.getCsvItemData();
        StringBuffer resultSb = new StringBuffer();
        String line = "";

        while ( (line = bufferedReader.readLine()) != null ) {
            Item item = new Item();
            item.convert(line);
        }
    }
    private static void cateInfo() throws Exception {
        Map mLoginInfo = loginInfo();
        CretecDoc cretecDoc = new CretecDoc("con");
        cretecDoc.Login(mLoginInfo);
        BufferedReader bufferedReader = cretecDoc.getCsvCateData();
        String line = "";

        while ( (line = bufferedReader.readLine()) != null ) {
            Category  cate = new Category();
            cate.convert(line);
        }
    }

}

