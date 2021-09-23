package edu.scu.csaserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.scu.csaserver.service.NodeServiceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NodeServiceServiceImplTest {

    @Autowired
    private NodeServiceServiceImpl nss;

    @Test
    public void testWrapper () {
//        // 创建QueryWrapper 对象
//        QueryWrapper<Map<String, Integer>> wrapper = new QueryWrapper<>();
//        // 设置查询条件
//        wrapper.select("service_id")
//                .groupBy("service_id");
//        // 执行查询

    }
}