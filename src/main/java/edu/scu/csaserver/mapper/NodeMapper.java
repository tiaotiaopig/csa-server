package edu.scu.csaserver.mapper;

import edu.scu.csaserver.domain.Node;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

    public List<Node> getNodePage (Integer page, Integer limit);
}




