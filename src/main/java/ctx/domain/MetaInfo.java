package ctx.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by chaester on 2017-05-12.
 */
@Entity
@Table(name="metainfo")
public class MetaInfo {
    @Id
    @Column(name="metainfo_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name="item_master_id")
    private ItemMaster master;
    private String name;
//    private List<MetaInfo> metaDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
