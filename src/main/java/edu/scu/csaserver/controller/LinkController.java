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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 连接相关接口
 * @author Lifeng
 * @date 2021/7/10 16:03
 */
@CrossOrigin
@RestController
@RequestMapping("/link")
@Api(tags = "连接管理")
public class LinkController {

    @Autowired
    private LinkService linkService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/relatedLinks")
    @ApiOperation(value = "获取节点相关的边", notes = "只返回相关边信息")
    public Res<List<LinkInfo>> relatedLinks(@RequestBody @ApiParam(value = "统一请求参数", required = true) Req<LinkedHashMap<String, List<Integer>>> request) {
        // 获取前端请求参数，这里是节点id数组
        // 如果没有对前端请求信息进行java对象的封装
        // 返回的是LinkedHashMap<String, Object>
        LinkedHashMap<String, List<Integer>> nodeIds = request.getParams();
        List<LinkInfo> linkInfos = linkService.getLinksByNodeId(nodeIds.get("nodes"));
        return Res.success(linkInfos);
    }

    @ApiOperation(value = "获取连接（边）总数")
    @GetMapping("/count")
    public Res<Integer> getLinkCount() {
        Res<Integer> res = new Res<>();
        return Res.success(linkService.count());
    }

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

    @ApiOperation(value = "连接分页查询", notes = "分页逻辑不完善")
    @GetMapping("/page")
    public Page<LinkInfo> getLinkList(@RequestParam(defaultValue = "1") String page, @RequestParam(defaultValue = "10") String limit) {
        Page<LinkInfo> linkInfoPage = new Page<>();
        linkInfoPage.setCode(0);
        linkInfoPage.setMsg("请求成功");
        linkInfoPage.setCount(linkService.count());
        int pageInt = Integer.parseInt(page);
        int limitInt = Integer.parseInt(limit);
        linkInfoPage.setData(linkService.getLinkPage(pageInt, limitInt));
        return linkInfoPage;
    }

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

    @ApiOperation(value = "更新连接信息")
    @PostMapping("/update")
    public Res<String> updateLink(@RequestBody @ApiParam(value = "连接对象", required = true) Req<LinkInfo> req) {
        LinkInfo linkInfo = req.getParams();
        linkService.updateById(linkInfo.getLink());
        return new Res<>(200, "更新成功");
    }

    @ApiOperation(value = "关键链路")
    @PostMapping("/keyLink")
    public Res<List<Link>> keyLink() {
        List<Link> keyLinks = new ArrayList<>();
        if (redisTemplate.opsForHash().hasKey("graph", "keyLinks")) {
            keyLinks = (List<Link>) redisTemplate.opsForHash().get("graph", "keyLinks");
        }
        return Res.success(keyLinks);
    }

    @ApiOperation(value = "根据提供的方法和文件名进行链路预测")
    @GetMapping("/predict")
    public Res<List<LinkInfo>> linkPredictBy(@RequestParam("func") String func, @RequestParam("filename") String filename) {
        return Res.success(linkService.linkPredictByFunc(func, filename));
    }

    @ApiOperation(value = "根据文件和比例进行链路掩盖")
    @GetMapping("/masked")
    public Res<List<LinkInfo>> masked(@RequestParam("filename") String filename, @RequestParam("ratio") String ratio) {
        return Res.success(linkService.getMasked(filename, ratio));
    }

    @ApiOperation(value = "在链路掩盖的基础上进行链路预测")
    @GetMapping("/predict2")
    public Res<Map<String, Object>> maskedAndPredict(@RequestParam("filename") String filename, @RequestParam("ratio") String ratio, @RequestParam("func") String funcName) {
        return Res.success(linkService.getPrediction(filename, ratio, funcName));
    }
}
