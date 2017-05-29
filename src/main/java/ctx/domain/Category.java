package ctx.domain;

/**
 * Created by chaester on 2017-05-12.
 */
public class Category {
    private String category1;
    private String category2;
    private String category3;
    private String category4;
    private String  categoryNm;
    public void convert(String line) {
        String[] aCate = line.split(",");
        if ( aCate.length > 4 ) {
            category1  = aCate[0];
            category2  = aCate[1];
            category3  = aCate[2];
            category4  = aCate[3];
            categoryNm = aCate[4];
        }
    }
}
