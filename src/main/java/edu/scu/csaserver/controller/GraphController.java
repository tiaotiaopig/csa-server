package edu.scu.csaserver.controller;

import edu.scu.csaserver.service.GraphService;
import edu.scu.csaserver.vo.Graph;
import edu.scu.csaserver.vo.Res;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 拓扑图相关接口
 * @author Lifeng
 * @date 2021/6/25 20:15
 */
@CrossOrigin
@RestController
@RequestMapping("/graph")
@Api(tags = "拓扑图管理")
public class GraphController {

    @Autowired
    private GraphService graphService;

    @GetMapping("/getGraph")
    @ApiOperation(value = "获取拓扑图", notes = "获取整个网络的拓扑数据")
    public Res<Graph> getGraph () {
        return Res.success(graphService.generateGraph());
    }

    @GetMapping("/get/{filename}")
    @ApiOperation(value = "由文件名获取拓扑图", notes = "获取拓扑文件对应的拓扑图")
    public Res<Graph> getByFilename(@PathVariable String filename) {

        return Res.success(graphService.generateGraph(filename));
    }


}
