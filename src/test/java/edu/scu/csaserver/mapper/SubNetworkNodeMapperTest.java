package edu.scu.csaserver.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.scu.csaserver.domain.SubNetworkNode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SubNetworkNodeMapperTest {
    @Resource
    private SubNetworkNodeMapper subNetworkNodeMapper;

    @Test
    public void testSelect () {
        System.out.println(subNetworkNodeMapper.getNodeId(1));
    }

}