package ctx.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by chaester on 2017-05-25.
 */
@Entity
@Table(name="item_master")
public class ItemMaster {
    @Id
    @Column(name="item_master_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name="item_id")
    private Item main_item;

    @OneToMany(mappedBy = "master")
    private List<Item> sub_item;

    @OneToMany(mappedBy = "master")
    private List<MetaInfo> metaInfos;
}
