package edu.scu.csaserver.service.impl;

import edu.scu.csaserver.service.DistributeService;
import edu.scu.csaserver.utils.DistributeUtil;
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
    @Override
    public Map<String, List<Integer>> degree_distribute(String fileName) {
        return DistributeUtil.degree_distribute(fileName);
    }

    @Override
    public Map<String, List<Integer>> community_distribute(String fileName) {
        return DistributeUtil.community_distribute(fileName);
    }
}
