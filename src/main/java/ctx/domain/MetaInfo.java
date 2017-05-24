package ctx.domain;

import java.util.List;

/**
 * Created by chaester on 2017-05-12.
 */
public class MetaInfo {
    private String id;
    private String name;
    private List<MetaInfo> metaDetails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
