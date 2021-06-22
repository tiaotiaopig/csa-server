package edu.scu.csaserver.mapper;

import edu.scu.csaserver.domain.ServiceNet;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class ServiceNetMapperTest {

    @Resource
    private ServiceNetMapper serviceNetMapper;

    @Test
    public void testSelectAll () {
        List<ServiceNet> serviceNets = serviceNetMapper.selectList(null);
        for (ServiceNet serviceNet : serviceNets) {
            System.out.println(serviceNet);
        }
    }
}