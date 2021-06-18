package edu.scu.csaserver.mapper;

import edu.scu.csaserver.domain.Node;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class NodeMapperTest {
    @Resource
    private NodeMapper nodeMapper;

    @Test
    public void testSelectAll () {
        List<Node> nodes = nodeMapper.selectList(null);
        System.out.println(nodes);
    }

}