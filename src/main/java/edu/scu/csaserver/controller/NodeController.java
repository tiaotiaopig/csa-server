package edu.scu.csaserver.controller;

import edu.scu.csaserver.domain.Node;
import edu.scu.csaserver.ro.AddedNode;
import edu.scu.csaserver.ro.Req;
import edu.scu.csaserver.service.NodeService;
import edu.scu.csaserver.vo.Count;
import edu.scu.csaserver.vo.NodeList;
import edu.scu.csaserver.vo.Res;
import edu.scu.csaserver.vo.ServiceVul;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;


/**
 * 节点相关操作接口
 * @author Lifeng
 * @date 2021/7/8 14:45
 */
@CrossOrigin
@RestController
@RequestMapping("/node")
@Api(tags = "节点管理")
public class NodeController {
    private final NodeService nodeService;
    @Autowired
    public NodeController (NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @ApiOperation(value = "节点分页查询", notes = "分页逻辑不完善")
    @GetMapping("/list")
    public NodeList getNodeList(@RequestParam(defaultValue = "1") String page, @RequestParam(defaultValue = "10") String limit) {
        NodeList nodeList = new NodeList();
        nodeList.setCode(0);
        nodeList.setMsg("请求成功");
        nodeList.setCount(nodeService.count());
        Integer pageInt = Integer.parseInt(page);
        Integer limitInt = Integer.parseInt(limit);
        nodeList.setData(nodeService.getNodePage(pageInt, limitInt));
        return nodeList;
    }

    @ApiOperation(value = "获取节点总数")
    @GetMapping("/count")
    public Res<Integer> getNodeCount() {
        Res<Integer> res = new Res<>();
        res.setCode(200);
        res.setMsg("返回节点总数");
        res.setData(nodeService.count());
        return res;
    }

    @ApiOperation(value = "获取节点漏洞总数")
    @GetMapping("/vulCount")
    public Res<Integer> getNodeVulnerabilityCount() {
        Res<Integer> res = new Res<>();
        res.setCode(200);
        res.setMsg("返回节点漏洞总数");
        res.setData(nodeService.getNodeVulnerability());
        return res;
    }

    @ApiOperation(value = "获取关键节点id")
    @PostMapping("/keyNode")
    public Res<List<Integer>> getKeyNode(@RequestBody @ApiParam(value = "统一请求参数", required = true) Req<LinkedHashMap<String, List<Integer>>> request) {
        Res<List<Integer>> res = new Res<>();
        res.setCode(200);
        res.setMsg("返回当前拓扑关键节点id");
        List<Integer> nodeIds = request.getParams().get("nodes");
        // 下面的方法报错会导致整个项目崩掉
        // 我们要增强健壮性,下面做相关边的时候,就进行了过滤
        // 不在边里的节点会被排除
        res.setData(nodeService.getKeyNodeIds(nodeIds));
        return res;
    }

    @ApiOperation(value = "更新节点信息")
    @PostMapping("/updateNode")
    public Res<String> updateNode(@RequestBody @ApiParam(value = "节点对象", required = true) Req<Node> req) {
        Node node = req.getParams();
        nodeService.updateById(node);
        return new Res<String>(200, "更新成功");
    }

    @ApiOperation(value = "删除节点", notes = "有关联的边时删除失败")
    @PostMapping("/deleteNode/{id}")
    public Res<String> deleteNode(@PathVariable(name = "id") Integer id) {
        Res<String> res = new Res<>();
        if (nodeService.deleteNodeById(id)) {
            res.setCode(200);
            res.setMsg("删除成功");
        } else {
            res.setCode(100);
            res.setMsg("删除失败,有边关联");
        }
        return res;
    }

    @ApiOperation(value = "添加节点信息")
    @PostMapping("/addNode")
    public Res<String> addNode(@RequestBody @ApiParam(value = "节点对象", required = true) Req<AddedNode> req) {
        AddedNode addedNode = req.getParams();
        nodeService.addNode(addedNode.getSubId(), addedNode.getNode());
        return new Res<>(200, "添加成功");
    }

    @ApiOperation(value = "获取节点逻辑类型统计信息")
    @GetMapping("/logicalCount")
    public Res<List<Count>> logicalCount() {
        Res<List<Count>> res = new Res<>(200, "获取成功");
        res.setData(nodeService.logicalCount());
        return res;
    }

    @ApiOperation(value = "获取节点物理类型统计信息")
    @GetMapping("/physicalCount")
    public Res<List<Count>> physicalCount() {
        Res<List<Count>> res = new Res<>(200, "获取成功");
        res.setData(nodeService.physicalCount());
        return res;
    }

    @ApiOperation(value = "获取节点漏洞统计信息")
    @GetMapping("/serviceVulCount")
    public Res<List<Count>> serviceVulCount() {
        Res<List<Count>> res = new Res<>(200, "获取成功");
        res.setData(nodeService.serviceVulCount());
        return res;
    }

    @ApiOperation(value = "根据安全等级获取节点", notes = "1=可破坏,2=可利用,3=可控制")
    @GetMapping("/safety/{id}")
    public Res<List<Node>> safetyNode(@PathVariable(name = "id") Integer safety) {
        Res<List<Node>> res = new Res<>(200, "请求成功");
        res.setData(nodeService.getNodeBySafety(safety));
        return res;
    }

    @ApiOperation(value = "获取节点服务漏洞数量")
    @GetMapping("/vulNum")
    public Res<ServiceVul> serviceVulNum() {
        Res<ServiceVul> res = new Res<>(200, "获取成功");
        res.setData(nodeService.nodeServiceVulNum());
        return res;
    }

    @ApiOperation(value = "获取节点服务漏洞数量")
    @GetMapping("/keyNodeBy")
    public List<Integer> keyNodeByFunc(@RequestParam("func") String func, @RequestParam("path") String path) {
        return nodeService.keyNode(func, path);
    }
}
