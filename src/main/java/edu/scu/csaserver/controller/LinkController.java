package edu.scu.csaserver.controller;

import edu.scu.csaserver.ro.Req;
import edu.scu.csaserver.service.LinkService;
import edu.scu.csaserver.vo.LinkInfo;
import edu.scu.csaserver.vo.Res;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 连接相关接口
 * @author Lifeng
 * @date 2021/7/10 16:03
 */
@RestController
@RequestMapping("/link")
@Api(tags = "连接管理")
public class LinkController {

    private final LinkService linkService;
    @Autowired
    public LinkController (LinkService linkService) {
        this.linkService = linkService;
    }

    @CrossOrigin
    @PostMapping("/relatedLinks")
    @ApiOperation(value = "获取节点相关的边", notes = "只返回相关边信息")
    public Res<List<LinkInfo>> relatedLinks(@RequestBody @ApiParam(value = "统一请求参数", required = true) Req<LinkedHashMap<String, List<Integer>>> request) {
        Res<List<LinkInfo>> res = new Res<>(200, "请求成功");
        // 获取前端请求参数，这里是节点id数组
        // 如果没有对前端请求信息进行java对象的封装
        // 返回的是LinkedHashMap<String, Object>
        LinkedHashMap<String, List<Integer>> nodeIds = request.getParams();
        List<LinkInfo> linkInfos = linkService.getLinksByNodeId(nodeIds.get("nodes"));
        res.setData(linkInfos);
        return res;
    }

    @CrossOrigin
    @ApiOperation(value = "获取连接（边）总数")
    @GetMapping("/count")
    public Res<Integer> getLinkCount() {
        Res<Integer> res = new Res<>();
        res.setCode(200);
        res.setMsg("返回连接（边）总数");
        res.setData(linkService.count());
        return res;
    }

    @CrossOrigin
    @ApiOperation(value = "删除边", notes = "删除没有约束,只是不能重复删除")
    @GetMapping("/deleteLink/{id}")
    public Res<String> deleteLink(@PathVariable(name = "id") Integer id) {
        Res<String> res = new Res<>();
        if (linkService.deleteLinkById(id)) {
            res.setCode(200);
            res.setMsg("删除成功");
        } else {
            res.setCode(100);
            res.setMsg("删除失败");
        }
        return res;
    }
}
