package edu.scu.csaserver.mapper;

import edu.scu.csaserver.domain.Link;
import edu.scu.csaserver.domain.SubNetworkNode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.nodes.NodeId;

import java.util.List;

/**
 * @Entity edu.scu.csaserver.domain.SubNetworkNode
 */
@Repository
public interface SubNetworkNodeMapper extends BaseMapper<SubNetworkNode> {

    /**
     * 通过子网号获取该子网下，所有节点的id
     * @param subId 子网id
     * @return 子网 subId 下的所有节点id
     */
    List<Integer> getNodeId (Integer subId);

    /**
     * 通过节点id 获取子网 id
     * @param nodeId 节点 id
     * @return
     */
    Integer getSubByNodeId (Integer nodeId);
}




