package edu.scu.csaserver.service.impl;

import edu.scu.csaserver.domain.Node;
import edu.scu.csaserver.service.NodeService;
import edu.scu.csaserver.vo.NodeInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

@SpringBootTest
class NodeServiceImplTest {

    @Resource
    private NodeService nodeService;

    @Test
    void getNodePage() {
        List<NodeInfo> list = nodeService.getNodePage(1, 5);
        for (NodeInfo node : list) {
            System.out.println(node);
        }
    }

    @Test
    void testDelete() {
//        nodeService.deleteNodeById(26);
    }
}