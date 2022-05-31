package edu.scu.csaserver.cachedao;

import edu.scu.csaserver.constant.GraphKey;
import edu.scu.csaserver.domain.vo.Graph;
import edu.scu.csaserver.domain.vo.LinkInfo;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 对图操作相关缓存逻辑进行整理封装
 * @author Lifeng
 * @date 2022/5/31 14:24
 */
@Repository
public class GraphCacheDAO {
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, Object, Object> opsForHash;

    public GraphCacheDAO(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        opsForHash = redisTemplate.opsForHash();
    }
    // TODO:抽取缓存的通用逻辑

    public Graph getGraph(String filename) {
        Graph graph = null;
        String key = GraphKey.GRAPH.getKEY();
        if (opsForHash.hasKey(key, filename)) {
            graph = (Graph) opsForHash.get(key, filename);
        }
        return graph;
    }

    public void setGraph(String filename, Graph graph) {
        String key = GraphKey.GRAPH.getKEY();
        opsForHash.put(key, filename, graph);
        redisTemplate.expire(key, 1, TimeUnit.DAYS);
    }

    public HashMap<String, String> getGraphCount(String filename) {
        HashMap<String, String> graphCount = null;
        String key = GraphKey.COUNT.getKEY();
        if (opsForHash.hasKey(key, filename)) {
            graphCount = (HashMap<String, String>) opsForHash.get(key, filename);
        }
        return graphCount;
    }

    public void setGraphCount(String filename, HashMap<String, String> graphCount) {
        String key = GraphKey.COUNT.getKEY();
        opsForHash.put(key, filename, graphCount);
        redisTemplate.expire(key, 1, TimeUnit.DAYS);
    }

    public List<LinkInfo> getMaskedLinks(String ratio, String filename) {
        List<LinkInfo> maskedLinks = null;
        String key = String.format(GraphKey.MASK.getKEY(), ratio);
        if (opsForHash.hasKey(key, filename)) {
            maskedLinks = (List<LinkInfo>) opsForHash.get(key, filename);
        }
        return maskedLinks;
    }

    public void setMaskedLinks(String ratio, String filename, List<LinkInfo> maskedLinks) {
        String key = String.format(GraphKey.MASK.getKEY(), ratio);
        opsForHash.put(key, filename, maskedLinks);
        redisTemplate.expire(key, 1, TimeUnit.DAYS);
    }

    public Map<String, Object> getPredictCache(String ratio, String filename, String func) {
        Map<String, Object> predictRes = null;
        String key = String.format(GraphKey.PREDICT.getKEY(), filename, ratio);
        if (opsForHash.hasKey(key, filename)) {
            predictRes = (Map<String, Object>) opsForHash.get(key, func);
        }
        return predictRes;
    }

    public void setPredictCache(String ratio, String filename, String func, Map<String, Object> predictRes) {
        String key = String.format(GraphKey.PREDICT.getKEY(), filename, ratio);
        opsForHash.put(key, func, predictRes);
        redisTemplate.expire(key, 1, TimeUnit.DAYS);
    }
}
