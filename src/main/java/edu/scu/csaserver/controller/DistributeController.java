package edu.scu.csaserver.controller;

import edu.scu.csaserver.service.DistributeService;
import edu.scu.csaserver.vo.Res;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 获取一些分布统计信息
 * @author Lifeng
 * @date 2022/4/21 20:25
 */

@CrossOrigin
@RestController
@RequestMapping("/distribute")
@Api(tags = "图分布统计信息")
public class DistributeController {

    private final DistributeService distributeService;

    @Autowired
    public DistributeController(DistributeService distributeService) {
        this.distributeService = distributeService;
    }

    @GetMapping("/degree")
    @ApiOperation(value = "获取节点度分布")
    public Res<Map<String, List<Integer>>> getDegDis(@RequestParam("fileName") String fileName) {
        return Res.success(distributeService.degree_distribute(fileName));
    }

    @GetMapping("/community")
    @ApiOperation(value = "获取图的社团分布")
    public Res<Map<String, List<Integer>>> getCommDis(@RequestParam("fileName") String fileName) {
        return Res.success(distributeService.community_distribute(fileName));
    }

    @GetMapping("/edgeBetweenCentrality")
    @ApiOperation(value = "获取边的接近中心性")
    public Res<Map<String, List<? extends Number>>> getEdgeBetCen(@RequestParam("fileName") String fileName) {
        return Res.success(distributeService.edge_betweenness_centrality(fileName));
    }
}
