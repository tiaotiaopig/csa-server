package edu.scu.csaserver.mapper;

import edu.scu.csaserver.domain.Node;
import edu.scu.csaserver.vo.Count;
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

    @Test
    public void testPhysical() {
        List<Count> counts = nodeMapper.getPhysicalTypeCount();
        for (Count count : counts) {
            System.out.println(count);
        }
    }

    @Test
    public void testLogical() {
        List<Count> counts = nodeMapper.getLogicalTypeCount();
        for (Count count : counts) {
            System.out.println(count);
        }
    }

    @Test
    public void testServiceVul() {
        List<Count> counts = nodeMapper.getServiceVulCount();
        for (Count count : counts) {
            System.out.println(count);
        }
    }
}