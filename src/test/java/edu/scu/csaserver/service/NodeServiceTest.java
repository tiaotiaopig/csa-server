package edu.scu.csaserver.service;

import edu.scu.csaserver.domain.Node;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class NodeServiceTest {
    @Resource
    private NodeService nodeService;

    @Test
    public void testSelect () {
        List<Node> list = nodeService.list();
        System.out.println(list);
    }
}