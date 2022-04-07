package edu.scu.csaserver.service;

import edu.scu.csaserver.domain.Link;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.scu.csaserver.vo.LinkInfo;

import java.util.List;

/**
 *
 */
public interface LinkService extends IService<Link> {
    /**
     * 根据节点 ids 获取相关的边
     * 我们采用的是 并 运算
     * @param nodes
     * @return
     */
    List<LinkInfo> getLinksByNodeId(List<Integer> nodes);

    /**
     * 将根据节点ids 获取相关边的操作，提取操作
     * 在进行关键节点和关键链路识别时，都要根据它，重新构造邻接矩阵
     * @param nodes
     * @return
     */
    List<Link> getRelatedLinks(List<Integer> nodes);

    Boolean deleteLinkById(Integer id);

    List<LinkInfo> getLinkPage(Integer page, Integer limit);

    Boolean addLink(Link link, Integer subId);

    /**
     * 根据方法名和文件名,调用对应的链路预测算法,返回10%预测为存在的边
     * @param func 要调用的方法名
     * @param filename 拓扑图的名称
     * @return 10%预测为存在的边
     */
    List<LinkInfo> linkPredictByFunc(String func, String filename);
}
