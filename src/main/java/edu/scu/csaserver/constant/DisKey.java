package edu.scu.csaserver.constant;

/**
 * 定义图分布信息缓存的key常量
 */
public enum DisKey {
    DEGREE("node_degree"),
    BETWEEN("between_centrality"),
    COMM("edge_between_centrality")
    ;

    private final String KEY;
    DisKey(String KEY) {
        this.KEY = KEY;
    }

    public String getKEY() {
        return KEY;
    }
}
