package edu.scu.csaserver.mapper;

import edu.scu.csaserver.vo.Count;
import edu.scu.csaserver.vo.ServiceCount;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

@SpringBootTest
class NodeServiceMapperTest {

    @Resource
    private NodeServiceMapper mapper;

    @Test
    void serviceCount() {
        List<ServiceCount> serviceCounts = mapper.serviceCount();
        for (ServiceCount serviceCount : serviceCounts) {
            System.out.println(serviceCount);
        }
    }

    @Test
    public void testServiceVulCount() {
        List<Count> counts = mapper.serviceVulCount();
        for (Count count : counts) {
            System.out.println(count);
        }
    }
}