package ctx.crawling;

import ctx.domain.Item;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chaester on 2016-01-06.
 */
public class HttpDoc {
    public final String UserAgent    = "Mozilla/5.0";
    private static  String[] dCookie = new String[24];
    private String cookies;
    public static CloseableHttpClient client = HttpClients.createDefault();

    public void setNetWid(String url, Map<String, Object> mLoginInfo)
            throws Exception {
        try {
            client.close();
            client      = null;
            client      = HttpClients.createDefault();
            String page = GetPageContent(url);
            List<NameValuePair> postParams = getFormParams(page, mLoginInfo);
            sendPost("http://con.toolpark.kr/uat/uia/actionSecurityLogin.do", postParams);
        }catch(Exception e){
            System.out.println( " setNet valuerrr : " +e.getMessage());

        }
    }
    private void sendPost(String url, List<NameValuePair> postParams)
            throws Exception {

        HttpPost post = new HttpPost(url);
        post.setHeader("Referer", "http://con.toolpark.kr/mng/login.do");
        post.setEntity(new UrlEncodedFormEntity(postParams));

        CloseableHttpResponse response = client.execute(post);

        int responseCode = response.getStatusLine().getStatusCode();

        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            System.out.println("Key : " + header.getName() + " ,Value : " + header.getValue());
            String name = header.getName().trim();
            if ( name.equals("Set-Cookie") ) {
                String[] s = header.getValue().split(";");
                System.out.println("Set-Cookie:"+s[0]);
                cookies=s[0];
            }
        }
        System.out.println("\nSending2 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + postParams);
        System.out.println("Response Code : " + responseCode);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ( (line = rd.readLine()) != null ) {
            result.append(line);
        }
        System.out.println(result.toString());
        GetPageContent("http://con.toolpark.kr");
    }
    public List<NameValuePair> getFormParams(String html, Map<String, Object> mLoginInfo)
            throws UnsupportedEncodingException {
        Document doc = Jsoup.parse(html);

        // Google form id
        Element loginform = doc.getElementsByTag("form").first();

        Elements inputElements = loginform.getElementsByTag("input");

        List<NameValuePair> paramList = new ArrayList<NameValuePair>();

        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");
            if (key.equals(mLoginInfo.get("idName").toString()))
                value = mLoginInfo.get("id").toString();
            else if (key.equals(mLoginInfo.get("pwName").toString()))
                value = mLoginInfo.get("pw").toString();

            paramList.add(new BasicNameValuePair(key, value));
        }
        return paramList;
    }
    public String GetPageContent(String url) throws Exception {
        try {
            HttpGet request = new HttpGet(url);
            request.setHeader("Host", "con.toolpark.kr/mng/login.do");
            request.setHeader("User-Agent", UserAgent);
            request.setHeader("Accept", "text/html,application/xhtml+xml");
            request.setHeader("Accept-Language", "ko-KR");
            request.setHeader("Connection", "keep-alive");
            request.setHeader("Referer", "http://tnet.kb-one.co.kr/member/login");
            request.setHeader("Content-Type", "application/x-www-form-urlencoded");
            HttpResponse response = client.execute(request);
            int responseCode = response.getStatusLine().getStatusCode();
            Header[] headers = response.getAllHeaders();
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent(),"UTF-8"));

            StringBuffer result = new StringBuffer();
            String line = "";

            while ( (line = rd.readLine()) != null ) {
                result.append(line);
            }
            return result.toString();
        } catch ( Exception e ) {
            System.out.println( " valuerrr : " +e.getMessage());
            return null;
        }
    }
    private String GetPageContentLogin(String url) throws Exception {
        try {
            HttpGet request = new HttpGet(url);
            request.setHeader("Host", "toolmon.toolpark.kr");
            request.setHeader("User-Agent", UserAgent);
            request.setHeader("Accept", "*/*");
            request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            request.setHeader("Accept-Language", "ko-KR");
            try{
                for(int k=0;k<dCookie.length;k++){
                    if(! dCookie[k].equals(null)){
                        System.out.println("Cookie:"+ dCookie[k]);
                        request.setHeader("Cookie", dCookie[k]);
                    }else{
                        break;
                    }
                }
            }catch(Exception e){
                System.out.println( " sss : " +e.getMessage());
            }
            request.setHeader("Connection", "keep-alive");
            request.setHeader("Referer", "http://tnet.kb-one.co.kr/member/login");
            request.setHeader("Content-Type", "application/x-www-form-urlencoded");
            HttpResponse response = client.execute(request);
            Header[] headers = response.getAllHeaders();

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent(),"UTF-8"));

            StringBuffer result = new StringBuffer();
            String line = "";

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        }catch(Exception e){
            System.out.println( " valuerrr : " +e.getMessage());
            return null;
        }
    }
    public void reClient() throws Exception {
        client.close();
        client      = null;
        client      = HttpClients.createDefault();
    }
    public void crtImageAtti(String url, Item item) {
        InputStream is       = null;
        FileOutputStream fos = null;
        try {
            reClient();
            String mkFolder = "";
            HttpGet httpget = new HttpGet("http:"+ url);
            HttpResponse response = client.execute(httpget);
            HttpEntity entity     = response.getEntity();
            String[] sP=url.split("/");
            File desti = new File(mkFolder);
            if (!desti.exists()) {
                desti.mkdirs();
            }
            is = entity.getContent();
            String filePath = mkFolder;

            filePath    = filePath + sP[sP.length - 1];
            File destis = new File(filePath);
            if (!destis.exists()) {
                fos = new FileOutputStream(new File(filePath));
                int inByte;
                while ((inByte = is.read()) != -1) {
                    fos.write(inByte);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            if (fos != null) {
                try {

                    fos.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    public void cate() throws Exception{}
    public void item() throws Exception{}
    public void insertImg() throws Exception{}
    public Map<String, Object> getItem(File f, String brandNm) throws Exception{return null;}
}