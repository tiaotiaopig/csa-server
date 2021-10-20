package edu.scu.csaserver.service;

import edu.scu.csaserver.domain.Link;
import edu.scu.csaserver.vo.NodeNormal;
import edu.scu.csaserver.vo.TopoElem;

/**
 * 计算网络效能态势的业务逻辑
 */
public interface SituationService {

    /**
     * 计算网络物理传输效能
     * @return
     */
    float phyTrans();

    /**
     * 返回所有连接的加权后的物理指标
     * @return
     */
    Link phyLinkElem();

    /**
     * 网络的节点处理效能
     * @return
     */
    float nodeProcess();

    /**
     * 网络的节点效能要素
     * @return
     */
    NodeNormal npElem();

    /**
     * 计算网络拓扑效能
     * @return
     */
    float topoSituation();
    /**
     * 网络拓扑效能要素
     * @return
     */
    TopoElem topologyElem();

    /**
     * 根据ahp,计算出传输，节点处理，拓扑的权重：0.21，0.10，0.69
     * @return
     */
    float overallSituation();
}
