package edu.scu.csaserver.controller;

import edu.scu.csaserver.domain.Link;
import edu.scu.csaserver.ro.Req;
import edu.scu.csaserver.service.LinkService;
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
    @Autowired
    private LinkService linkService;

    @CrossOrigin
    @PostMapping("/relatedLinks")
    @ApiOperation(value = "获取节点相关的边", notes = "只返回相关边信息")
    public Res relatedLinks(@RequestBody @ApiParam(value = "统一请求参数", required = true) Req request) {
        Res response = new Res(200, "请求成功");
        Object obj = request.getParams();
        LinkedHashMap<String, List<Integer>> nodeIds = (LinkedHashMap<String, List<Integer>>)request.getParams();
        List<Link> links = linkService.getLinksByNodeId(nodeIds.get("nodes"));
        response.setData(links);
        return response;
    }
}
