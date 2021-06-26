package edu.scu.csaserver.mapper;

import edu.scu.csaserver.domain.Link;
import edu.scu.csaserver.domain.SubNetworkNode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Entity edu.scu.csaserver.domain.SubNetworkNode
 */
public interface SubNetworkNodeMapper extends BaseMapper<SubNetworkNode> {

    public List<Integer> getNodeId (Integer id);
}




