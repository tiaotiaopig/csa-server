package edu.scu.csaserver.service;

import edu.scu.csaserver.domain.Node;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class NodeServiceTest {

    @Autowired
    private NodeService nodeService;

    @Test
    public void testSelectAll () {
        List<Node> list = nodeService.list();
        System.out.println(list);
    }
}