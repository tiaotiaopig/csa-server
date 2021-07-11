package edu.scu.csaserver.controller;

import edu.scu.csaserver.service.GraphService;
import edu.scu.csaserver.vo.Graph;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 拓扑图相关接口
 * @author Lifeng
 * @date 2021/6/25 20:15
 */

@RestController
@RequestMapping("/graph")
@Api(tags = "拓扑图管理")
public class GraphController {

    @Autowired
    private GraphService graphService;


    @CrossOrigin
    @GetMapping("/getGraph")
    @ApiOperation(value = "获取拓扑图", notes = "获取整个网络的拓扑数据")
    public Graph getGraph () {
        return graphService.generateGraph();
    }
}
