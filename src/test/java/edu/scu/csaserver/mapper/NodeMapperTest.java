package edu.scu.csaserver.mapper;

import edu.scu.csaserver.domain.Node;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NodeMapperTest {

    @Resource
    private NodeMapper nodeMapper;

    @Test
    public void testSelectAll() {
        List<Node> nodes = nodeMapper.selectList(null);
        for (Node node : nodes) {
            System.out.println(node);
        }
    }

    @Test
    public void testGetAuto() {
        System.out.println(nodeMapper.getNodeAutoIncrement());
    }

    @Test
    public void testDelete() {
//        nodeMapper.deleteById(25);
    }
}