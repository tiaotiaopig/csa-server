package edu.scu.csaserver.controller;

import edu.scu.csaserver.service.NodeService;
import edu.scu.csaserver.vo.NodeList;
import edu.scu.csaserver.vo.Res;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 节点相关操作接口
 * @author Lifeng
 * @date 2021/7/8 14:45
 */
@RestController
@RequestMapping("/node")
@Api(tags = "节点管理")
public class NodeController {
    private final NodeService nodeService;
    @Autowired
    public NodeController (NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @CrossOrigin
    @ApiOperation(value = "节点分页查询", notes = "分页逻辑不完善")
    @GetMapping("/list")
    public NodeList getNodeList(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10") String limit) {
        NodeList nodeList = new NodeList();
        nodeList.setCode(0);
        nodeList.setMsg("请求成功");
        nodeList.setCount(nodeService.count());
        Integer pageInt = Integer.parseInt(page);
        Integer limitInt = Integer.parseInt(limit);
        nodeList.setData(nodeService.getNodePage(pageInt, limitInt));
        return nodeList;
    }

    @CrossOrigin
    @ApiOperation(value = "获取节点总数")
    @GetMapping("/count")
    public Res<Integer> getNodeCount() {
        Res<Integer> res = new Res<>();
        res.setCode(200);
        res.setMsg("返回节点总数");
        res.setData(nodeService.count());
        return res;
    }

    @CrossOrigin
    @ApiOperation(value = "获取节点漏洞总数")
    @GetMapping("/vulCount")
    public Res<Integer> getNodeVulnerabilityCount() {
        Res<Integer> res = new Res<>();
        res.setCode(200);
        res.setMsg("返回节点漏洞总数");
        res.setData(nodeService.getNodeVulnerability());
        return res;
    }
}
