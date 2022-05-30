package edu.scu.csaserver.service.impl;

import edu.scu.csaserver.cachedao.DistributeCacheDAO;
import edu.scu.csaserver.service.DistributeService;
import edu.scu.csaserver.utils.DistributeUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final DistributeCacheDAO distributeCacheDAO;

    public DistributeServiceImpl(DistributeCacheDAO distributeCacheDAO) {
        this.distributeCacheDAO = distributeCacheDAO;
    }
    @Override
    public Map<String, List<Integer>> degree_distribute(String fileName) {
        Map<String, List<Integer>> res;
        if ((res = distributeCacheDAO.getDegreeDis(fileName)) == null) {
            res = DistributeUtil.degree_distribute(fileName);
            distributeCacheDAO.setDegreeDis(fileName, res);
        }
        return res;
    }

    @Override
    public Map<String, List<Integer>> community_distribute(String fileName) {
        Map<String, List<Integer>> res;
        if ((res = distributeCacheDAO.getCommDis(fileName)) == null) {
            res = DistributeUtil.community_distribute(fileName);
            distributeCacheDAO.setCommDis(fileName, res);
        }
        return res;
    }

    @Override
    public Map<String, List<? extends Number>> edge_betweenness_centrality(String fileName) {
        Map<String, List<? extends Number>> res;
        if ((res = distributeCacheDAO.getLinkBC(fileName)) == null) {
            res = DistributeUtil.edge_betweenness_centrality(fileName);
            distributeCacheDAO.setLinkBC(fileName, res);
        }
        return res;
    }
}
