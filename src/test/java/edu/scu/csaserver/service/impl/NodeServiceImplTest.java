package edu.scu.csaserver.service.impl;

import edu.scu.csaserver.domain.Node;
import edu.scu.csaserver.service.NodeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NodeServiceImplTest {

    @Resource
    private NodeService nodeService;

    @Test
    void getNodePage() {
        List<Node> list = nodeService.getNodePage(1, 5);
        for (Node node : list) {
            System.out.println(node);
        }
    }
}