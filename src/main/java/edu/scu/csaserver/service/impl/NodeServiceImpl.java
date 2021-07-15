package edu.scu.csaserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.scu.csaserver.domain.Link;
import edu.scu.csaserver.domain.Node;
import edu.scu.csaserver.mapper.LinkMapper;
import edu.scu.csaserver.mapper.SubNetworkNodeMapper;
import edu.scu.csaserver.service.NodeService;
import edu.scu.csaserver.mapper.NodeMapper;
import edu.scu.csaserver.utils.KeyNode;
import edu.scu.csaserver.vo.NodeInfo;
import edu.scu.csaserver.vo.NodeList;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
@Service
public class NodeServiceImpl extends ServiceImpl<NodeMapper, Node>
implements NodeService{

    @Autowired
    private NodeMapper nodeMapper;
    @Autowired
    private SubNetworkNodeMapper subNetworkNodeMapper;
    @Autowired
    private LinkMapper linkMapper;
    @Autowired
    private KeyNode keyNode;

    @Override
    public List<NodeInfo> getNodePage(Integer page, Integer limit) {
        page = (page - 1) * limit;
        List<Node> nodePage = nodeMapper.getNodePage(page, limit);
        // 为了echarts绘图，封装了一下
        // 填充子网号
        List<NodeInfo> result = new ArrayList<>(nodePage.size());
        for (Node node : nodePage) {
            NodeInfo nodeInfo = new NodeInfo();
            nodeInfo.setName("节点" + node.getId());
            Integer subId = subNetworkNodeMapper.getSubByNodeId(node.getId());
            nodeInfo.setCategory("子网" + subId);
            nodeInfo.setNode(node);
            result.add(nodeInfo);
        }
        return result;
    }

    @Override
    public Integer getNodeVulnerability() {
        // 1. 创建QueryWrapper 对象
        QueryWrapper<Node> query = new QueryWrapper<>();
        // 2. 设置查询条件
        query.select("vulnerability_sum");
        // 3. 执行查询
        List<Node> nodes = nodeMapper.selectList(query);
        int sum = 0;
        for (Node node : nodes) {
            sum += node.getVulnerabilitySum();
        }
        return sum;
    }

    @Override
    public List<Integer> getKeyNodeIds(List<Integer> nodeIds) {
        List<Link> related = new ArrayList<>();
        List<Link> links = linkMapper.selectList(null);
        // 获取节点相关边
        for (Link link : links) {
            if (nodeIds.contains(link.getSourceNodeId()) &&
                    nodeIds.contains(link.getTargetNodeId())) {
                related.add(link);
            }
        }
        // 将相关边的连接关系取出
        // 封装成二维数组，供关键节点算法使用
        Link link;
        int len = related.size();
        int[][] linkArray = new int[len][2];
        for (int i = 0; i < len; i++) {
            link = links.get(i);
            linkArray[i][0] = link.getSourceNodeId();
            linkArray[i][1] = link.getTargetNodeId();
        }
        // int[] 转 List<Integer>
        int[] keys = keyNode.getKeyNodeIds(nodeIds.size(), linkArray);
        List<Integer> result = new ArrayList<>();
        for (int key : keys) result.add(key);
        return result;
    }
}




