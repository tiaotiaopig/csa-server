package edu.scu.csaserver.controller;

import edu.scu.csaserver.domain.ServiceNet;
import edu.scu.csaserver.mapper.ServiceNetMapper;
import edu.scu.csaserver.service.NodeServiceService;
import edu.scu.csaserver.domain.vo.Count;
import edu.scu.csaserver.domain.vo.Res;
import edu.scu.csaserver.domain.vo.ServiceCount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Lifeng
 * @date 2021/7/14 13:09
 */
@CrossOrigin
@RestController
@RequestMapping("/service")
@Api(tags = "服务管理")
public class ServiceNetController {

    public final NodeServiceService nss;

    public final ServiceNetMapper sm;
    @Autowired
    public ServiceNetController(NodeServiceService nss, ServiceNetMapper sm) {

        this.nss = nss;
        this.sm = sm;
    }

    @GetMapping("/serviceCount")
    @ApiOperation("服务统计信息")
    public Res<List<ServiceCount>> getServiceCount() {
        Res<List<ServiceCount>> response = new Res<>();
        response.setCode(200);
        response.setMsg("请求成功");
        response.setData(nss.serviceCount());
        return response;
    }

    @GetMapping("/vulCount")
    @ApiOperation("服务漏洞统计信息")
    public Res<List<Count>> getServiceVulCount() {
        Res<List<Count>> response = new Res<>();
        response.setCode(200);
        response.setMsg("请求成功");
        response.setData(nss.serviceVulCount());
        return response;
    }

    @ApiOperation(value = "获取节点运行服务总数")
    @GetMapping("/count")
    public Res<Integer> getNodeCount() {
        Res<Integer> res = new Res<>();
        res.setCode(200);
        res.setMsg("返回运行服务总数");
        res.setData(nss.count());
        return res;
    }

    @GetMapping("/portCount")
    @ApiOperation("服务端口统计信息")
    public Res<List<Count>> getServicePortCount() {
        Res<List<Count>> response = new Res<>();
        response.setCode(200);
        response.setMsg("请求成功");
        response.setData(sm.getByServicePortCount());
        return response;
    }

    @GetMapping("/safetyCount")
    @ApiOperation("服务安全级别统计信息")
    public Res<List<Count>> getServiceSafetyCount() {
        Res<List<Count>> response = new Res<>();
        response.setCode(200);
        response.setMsg("请求成功");
        response.setData(sm.getNodeSafetyCount());
        return response;
    }

    @GetMapping("/all")
    @ApiOperation("获取所有服务信息")
    public Res<List<ServiceNet>> getAllService() {
        Res<List<ServiceNet>> response = new Res<>();
        response.setCode(200);
        response.setMsg("请求成功");
        response.setData(sm.selectList(null));
        return response;
    }
}
