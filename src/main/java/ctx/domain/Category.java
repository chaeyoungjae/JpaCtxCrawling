package ctx.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaester on 2017-05-12.
 */
@Entity
@Table(name="category", uniqueConstraints = {@UniqueConstraint(
        name="category_unique",
        columnNames = {"cate_l", "cate_m", "cate_s", "cate_ss"}
)})
public class Category implements Serializable {
    @Id
    @Column(name="cate_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="cate_l", nullable = false, length = 2)
    private String categoryL;
    @Column(name="cate_m", nullable = false, length = 4)
    private String categoryM;
    @Column(name="cate_s", nullable = false, length = 4)
    private String categoryS;
    @Column(name="cate_ss", nullable = false, length = 4)
    private String categorySS;
    @Column(name="cate_nm")
    private String  categoryNm;
    @Column(name="ord_by")
    private Integer orderBy;
    @OneToMany(mappedBy = "category")
    private List<Item> items = new ArrayList<>();
    public String getCategoryL() {
        return categoryL;
    }

    public void setCategoryL(String categoryL) {
        this.categoryL = categoryL;
    }

    public String getCategoryM() {
        return categoryM;
    }

    public void setCategoryM(String categoryM) {
        this.categoryM = categoryM;
    }

    public String getCategoryS() {
        return categoryS;
    }

    public void setCategoryS(String categoryS) {
        this.categoryS = categoryS;
    }

    public String getCategorySS() {
        return categorySS;
    }

    public void setCategorySS(String categorySS) {
        this.categorySS = categorySS;
    }

    public String getCategoryNm() {
        return categoryNm;
    }

    public void setCategoryNm(String categoryNm) {
        this.categoryNm = categoryNm;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public void convert(String[] aCate) {
        if ( aCate.length > 4 ) {
            categoryL  = aCate[0];
            categoryM  = aCate[1];
            categoryS  = aCate[2];
            categorySS = aCate[3];
            categoryNm = aCate[4];
        }
    }

    public List<Item> getItems() {
        return items;
    }
}
