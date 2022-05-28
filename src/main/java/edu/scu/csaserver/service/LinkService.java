package edu.scu.csaserver.service;

import edu.scu.csaserver.domain.Link;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.scu.csaserver.domain.vo.LinkInfo;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据图名称和掩盖比例，返回掩盖的边
     * @param dataName 图名称
     * @param ratio 掩盖比例
     * @return 掩盖的边
     */
    List<LinkInfo> getMasked(String dataName, String ratio);

    /**
     * 根据图名称和掩盖比例，返回指定方法的链路预测结果
     * @param dataName 图名称
     * @param ratio 掩盖比例
     * @param funcName 使用的链路预测方法
     * @return 预测存在的边（）
     */
    Map<String, Object> getPrediction(String dataName, String ratio, String funcName);
}
