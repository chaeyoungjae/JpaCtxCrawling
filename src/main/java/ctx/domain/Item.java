package ctx.domain;

import java.util.List;

/**
 * Created by chaester on 2017-05-10.
 */
public class Item {
    private String  id;
    private String  prod_cd;                    // 상품코드
    private String  pname;                      // 실제 제품명
    private String  nickName;                   // 변경 제품명
    private Integer sle_pc;                     // 공급 가격
    private Integer price_pc;                   // 판매 가격
    private Integer price_mobile;               // 모바일 판매 가격
    private Integer normal_price;               // 할인전 가격
    private String  image_link;                 // 대표 이미지
    private String  add_image_link;             // 구분자 |
    private String  category_id1;               // 대분류 (제휴사 카테고리명)
    private String  category_id2;               // 중분류 (제휴사 카테고리명)
    private String  category_id3;               // 소분류 (제휴사 카테고리명)
    private String  category_id4;               // 세분류 (제휴사 카테고리명)
    private String  category1;                  // 대분류 (제휴사 카테고리명)
    private String  category2;                  // 중분류 (제휴사 카테고리명)
    private String  category3;                  // 소분류 (제휴사 카테고리명)
    private String  category4;                  // 세분류 (제휴사 카테고리명)
    private String  naver_category;             // 네이버카테고리
    private String  naver_product_id;           // 가격비고 페이지 ID
    private String  condition;                  // 상품상태 (신상품/중고/리퍼/전시/반품/스크래치)
    private String  import_flag;                // 해외구매대행 여부
    private String  paralel_import;             // 병행수입 여부
    private String  order_made;                 // 주문제작 상품여부
    private String  product_flag;               // 판매방식 구분 (도매/렌탈/대여/할부/예약판매/구매대행)
    private String  adult;                      // 미성년자 구매불가 상품여부
    private String  goods_type;                 // 상품구분
    private String  barcode;                    // 바코드
    private String  manufacture_define_number;  // 제품코드
    private String  model_number;               // 모델명
    private String  brand;                      // 브랜드
    private String  maker;                      // 제조사
    private String  orgin;                      // 원산지
    private String  card_event;                 // 카드명/카드할인가격
    private String  event_words;                // 이벤트
    private String  coupon;                     // 일반/제휴쿠폰
    private String  partner_coupon_download;    // 쿠폰다운로드필요여부
    private String  interest_free_event;        // 카드 무이자 할부 정보
    private String  point;                      // 포인트
    private String  installation_costs;         // 별도 설치비 유무
    private String  search_tag;                 // 검색태그
    private String  group_id;                   // GroupID
    private String  vendor_id;                  // 제휴사 상품ID
    private String  coordi_id;                  // 코디상품 ID
    private Integer minimun_purchase_quanitity; // 최소구매수량
    private Integer review_count;               // 상품평 수
    private Integer shipping;                   // 배송료
    private String  delivery_grade;             // 차등배송비 여부
    private String  delivery_detail;            // 차등배송비 내용
    private String  attribute;                  // 상품속성
    private String  option_detail;              // 구매옵션
    private String  age_group;                  // 주이용고객층
    private String  gender;                     // 성별
    private String  use_yn;                     // 노출여부
    private String  soldout_yn;                 // 품절여부
    private List<Item> relationItem;            // 관련상품
    private List<MetaInfo> metaInfos;           // 메타정보

    public void convertItem(String line) {
        String[] aItem = line.split(",");
        if ( aItem.length > 0 ) {
            this.id = aItem[0];
            this.prod_cd      = aItem[1].replace("-", "");
            this.use_yn       = aItem[2];
            this.soldout_yn   = aItem[3];
            this.brand        = aItem[5];
            this.maker        = aItem[5];
            this.pname        = aItem[6];
            this.model_number = aItem[7];
            this.image_link   = aItem[8];
            this.price_pc     = Integer.parseInt(aItem[12]);
            this.price_mobile = Integer.parseInt(aItem[12]);
            this.normal_price = Integer.parseInt(aItem[13]);
            this.sle_pc       = Integer.parseInt(aItem[14]);
            this.minimun_purchase_quanitity = Integer.parseInt(aItem[18]);
            this.manufacture_define_number  = aItem[1].replace("-", "");
            if ( aItem[23] != null && aItem[23].equals("Y") ) {
                this.delivery_grade  = "Y";
                this.delivery_detail = "화물착불";
            }
            if ( aItem[26] != null && !aItem[26].equals("") ) {
                String[] Meta = aItem[26].split("|");
                for ( String data : Meta ) {
                    MetaInfo metaInfo = new MetaInfo();
                    String[] datas = data.split("_");
                    metaInfo.setId(datas[0]);
                    metaInfo.setName(datas[1]);
                    this.metaInfos.add(metaInfo);
                }
            }
            add_image_link = aItem[27];
            if ( aItem[29] != null && !aItem[29].equals("") ) {
                String[] cates = aItem[29].split("_");
                this.category_id1 = cates[0];
                this.category_id2 = cates[1];
                this.category_id3 = cates[2];
                this.category_id4 = cates[3];
            }
            this.orgin = aItem[30];
        }
    }
}
