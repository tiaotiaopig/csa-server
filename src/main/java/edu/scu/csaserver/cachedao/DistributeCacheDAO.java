package edu.scu.csaserver.cachedao;

import edu.scu.csaserver.constant.DisKey;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 主要封装对图分布信息的缓存操作
 * 目前主要有节点度分布,边的接近中心性,社团分布
 * @author Lifeng
 * @date 2022/5/30 16:18
 */
@Repository
public class DistributeCacheDAO {

    private static final String KEY_PATTERN = "dis:%s";
    private static final String HASH_PATTERN = "graph:%s";
    
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, Object, Object> opsForHash;

    public DistributeCacheDAO(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.opsForHash = redisTemplate.opsForHash();
    }

    private String generateKey(String dis) {
        return String.format(KEY_PATTERN, dis);
    }

    private String generateHash(String graph) {
        return String.format(HASH_PATTERN, graph);
    }

    public Map<String, List<Integer>> getDegreeDis(String graph) {
        String key = generateKey(DisKey.DEGREE.getKEY());
        String hash = generateHash(graph);
        Map<String, List<Integer>> res = null;
        if (opsForHash.hasKey(key, hash)) {
            res = (Map<String, List<Integer>>)opsForHash.get(key, hash);
        }
        return res;
    }

    public void setDegreeDis(String graph, Map<String, List<Integer>> res) {
        String key = generateKey(DisKey.DEGREE.getKEY());
        String hash = generateHash(graph);
        opsForHash.put(key, hash, res);
        redisTemplate.expire(key, 1, TimeUnit.DAYS);
    }

    public Map<String, List<Integer>> getCommDis(String graph) {
        String key = generateKey(DisKey.COMM.getKEY());
        String hash = generateHash(graph);
        Map<String, List<Integer>> res = null;
        if (opsForHash.hasKey(key, hash)) {
            res = (Map<String, List<Integer>>)opsForHash.get(key, hash);
        }
        return res;
    }

    public void setCommDis(String graph, Map<String, List<Integer>> res) {
        String key = generateKey(DisKey.COMM.getKEY());
        String hash = generateHash(graph);
        opsForHash.put(key, hash, res);
        redisTemplate.expire(key, 1, TimeUnit.DAYS);
    }

    public Map<String, List<? extends Number>> getLinkBC(String graph) {
        String key = generateKey(DisKey.BETWEEN.getKEY());
        String hash = generateHash(graph);
        Map<String, List<? extends Number>> res = null;
        if (opsForHash.hasKey(key, hash)) {
            res=  (Map<String, List<? extends Number>>) opsForHash.get(key, hash);
        }
        return res;
    }

    public void setLinkBC(String graph, Map<String, List<? extends Number>> res) {
        String key = generateKey(DisKey.BETWEEN.getKEY());
        String hash = generateHash(graph);
        opsForHash.put(key, hash, res);
        redisTemplate.expire(key, 1, TimeUnit.DAYS);
    }
}
