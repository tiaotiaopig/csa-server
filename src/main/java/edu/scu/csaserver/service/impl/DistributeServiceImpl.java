package edu.scu.csaserver.service.impl;

import edu.scu.csaserver.service.DistributeService;
import edu.scu.csaserver.utils.DistributeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 获取分布统计信息
 * @author Lifeng
 * @date 2022/4/21 20:31
 */
@Service
public class DistributeServiceImpl implements DistributeService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public Map<String, List<Integer>> degree_distribute(String fileName) {
        Map<String, List<Integer>> res;
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        String key = "degree_distribute";
        if (opsForHash.hasKey(key, fileName)) {
            res = (Map<String, List<Integer>>) opsForHash.get(key, fileName);
        } else {
            res = DistributeUtil.degree_distribute(fileName);
        }
        return res;
    }

    @Override
    public Map<String, List<Integer>> community_distribute(String fileName) {
        Map<String, List<Integer>> res;
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        String key = "community_distribute";
        if (opsForHash.hasKey(key, fileName)) {
            res = (Map<String, List<Integer>>) opsForHash.get(key, fileName);
        } else {
            res = DistributeUtil.community_distribute(fileName);
        }
        return res;
    }
}
