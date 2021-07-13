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
}
