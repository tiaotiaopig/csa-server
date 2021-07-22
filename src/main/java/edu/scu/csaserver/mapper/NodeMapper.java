package edu.scu.csaserver.mapper;

import edu.scu.csaserver.domain.Node;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.scu.csaserver.vo.Count;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * node 表相关 dao 操作
 * 因为我们使用 mybatis 设置了 mapperScan 路径
 * 所以我们不用@Repository 也能注入 spring 容器
 * 但是 为了idea不报错，满足它，加上
 * @Entity edu.scu.csaserver.domain.Node
 */
@Repository
public interface NodeMapper extends BaseMapper<Node> {

    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    List<Node> getNodePage (Integer page, Integer limit);

    /**
     * 获取新增节点的 id
     * 主要是通过获取id最大值得到
     * 在单机单个添加的情况下可以使用
     * @return
     */
    Integer getNodeAutoIncrement();

    /**
     * 获取节点物理类型的统计信息
     * @return
     */
    List<Count> getPhysicalTypeCount();

    /**
     * 获取节点逻辑类型的统计信息
     * @return
     */
    List<Count> getLogicalTypeCount();

    List<Count> getServiceVulCount();
}




