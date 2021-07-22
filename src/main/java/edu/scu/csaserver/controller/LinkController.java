package edu.scu.csaserver.controller;

import edu.scu.csaserver.domain.Link;
import edu.scu.csaserver.domain.SubNetworkLink;
import edu.scu.csaserver.ro.AddLink;
import edu.scu.csaserver.ro.AddedNode;
import edu.scu.csaserver.ro.Req;
import edu.scu.csaserver.service.LinkService;
import edu.scu.csaserver.service.SubNetworkLinkService;
import edu.scu.csaserver.vo.*;
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
    @PostMapping("/deleteLink/{id}")
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

    @CrossOrigin
    @ApiOperation(value = "连接分页查询", notes = "分页逻辑不完善")
    @GetMapping("/page")
    public Page<LinkInfo> getLinkList(@RequestParam(defaultValue = "1") String page, @RequestParam(defaultValue = "10") String limit) {
//        NodeList nodeList = new NodeList();
//        nodeList.setCode(0);
//        nodeList.setMsg("请求成功");
//        nodeList.setCount(nodeService.count());
//        Integer pageInt = Integer.parseInt(page);
//        Integer limitInt = Integer.parseInt(limit);
//        nodeList.setData(nodeService.getNodePage(pageInt, limitInt));
//        return nodeList;
        Page<LinkInfo> linkInfoPage = new Page<>();
        linkInfoPage.setCode(0);
        linkInfoPage.setMsg("请求成功");
        linkInfoPage.setCount(linkService.count());
        int pageInt = Integer.parseInt(page);
        int limitInt = Integer.parseInt(limit);
        linkInfoPage.setData(linkService.getLinkPage(pageInt, limitInt));
        return linkInfoPage;
    }

    @CrossOrigin
    @ApiOperation(value = "添加连接信息")
    @PostMapping("/add")
    public Res<String> addLink(@RequestBody @ApiParam(value = "连接对象", required = true) Req<AddLink> req) {
        AddLink addLink = req.getParams();
        if (linkService.addLink(addLink.getLink(), addLink.getSubId())) {
            return new Res<>(200, "添加成功");
        } else {
            return new Res<>(100, "添加失败");
        }

    }

    @CrossOrigin
    @ApiOperation(value = "更新连接信息")
    @PostMapping("/update")
    public Res<String> updateLink(@RequestBody @ApiParam(value = "连接对象", required = true) Req<LinkInfo> req) {
        LinkInfo linkInfo = req.getParams();
        linkService.updateById(linkInfo.getLink());
        return new Res<>(200, "更新成功");
    }
}
