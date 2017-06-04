package ctx;

import com.mysema.query.jpa.impl.JPAQuery;
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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.BufferedReader;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chaester on 2017-05-10.
 */
public class BatchJob {
    public static void main(String[] arg) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJob");
        EntityManager em         = emf.createEntityManager();
        EntityManager em2        = emf.createEntityManager();
        EntityTransaction tx     = em.getTransaction();
        try {
            cateInfo(em, em2, tx);
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
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
    private static void ItemInfo(EntityManager em) throws Exception {
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
    private static void cateInfo(EntityManager em, EntityManager em2, EntityTransaction tx) throws Exception {
        Map mLoginInfo = loginInfo();
        CretecDoc cretecDoc = new CretecDoc("con");
        cretecDoc.Login(mLoginInfo);
        BufferedReader bufferedReader = cretecDoc.getCsvCateData();
        String line = "";
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> query = cb.createQuery(Category.class);
        Root<Category> m = query.from(Category.class);

        while ( (line = bufferedReader.readLine()) != null ) {
            tx.begin();
            Category  cate = new Category();
            String[] aCate = line.split(",");
            if ( aCate != null && aCate.length == 5 ) {
                System.out.print(aCate[0]);
                System.out.print(aCate[1]);
                System.out.print(aCate[2]);
                System.out.print(aCate[3]);
                System.out.println(aCate[4]);
                CriteriaQuery<Category> cq = query.select(m)
                        .where(cb.and(cb.equal(m.get("categoryL"), aCate[0]),
                                cb.equal(m.get("categoryM"), aCate[1]),
                                cb.equal(m.get("categoryS"), aCate[2]),
                                cb.equal(m.get("categorySS"), aCate[3]))
                        );
                List<Category> result = em2.createQuery(cq).getResultList();

                cate.convert(aCate);
                if (cate.getCategoryL() != null && result.size() == 0)
                    em.persist(cate);
            }
            tx.commit();
        }
    }

}

