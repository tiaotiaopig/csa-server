package edu.scu.csaserver.controller;

import edu.scu.csaserver.domain.Link;
import edu.scu.csaserver.service.SituationService;
import edu.scu.csaserver.domain.vo.NodeNormal;
import edu.scu.csaserver.domain.vo.Res;
import edu.scu.csaserver.domain.vo.TopoElem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 向前端返回计算得到的网络态势
 * @author Lifeng
 * @date 2021/9/22 14:15
 */
@CrossOrigin
@RestController
@RequestMapping("/situation")
@Api(tags = "网络态势")
public class SituationController {

    @Autowired
    private SituationService situationService;

    /**
     * 网络物理传输效能
     * @return
     */
    @GetMapping("/phyTrans")
    @ApiOperation("网络物理传输效能")
    public Res<Float> physicalTransmission() {

        return Res.success(situationService.phyTrans());
    }

    @GetMapping("/phyTransElem")
    @ApiOperation("网络物理传输要素平均值")
    public Res<Link> phyTransElem() {
        return Res.success(situationService.phyLinkElem());
    }

    /**
     * 网络逻辑传输效能
     * @return
     */
    @GetMapping("/logTrans")
    @ApiOperation("网络逻辑传输效能(未完善)")
    public Res<Float> logicalTransmission() {
        return Res.success(58.4f);
    }

    /**
     * 节点处理效能
     * @return
     */
    @GetMapping("/procession")
    @ApiOperation("节点处理效能")
    public Res<Float> nodeProcession() {

        return Res.success(situationService.nodeProcess());
    }

    @GetMapping("/nodeProElem")
    @ApiOperation("节点性能要素平均值")
    public Res<NodeNormal> nodeProElem() {
        return Res.success(situationService.npElem());
    }

    /**
     * 拓扑结构效能
     * @return
     */
    @GetMapping("/topology")
    @ApiOperation("拓扑结构效能(未完善)")
    public Res<Float> topologyStructure() {
        return Res.success(situationService.topoSituation());
    }

    /**
     * 拓扑结构效能要素
     * @return
     */
    @GetMapping("/topologyElem")
    @ApiOperation("拓扑结构效能要素(未完善)")
    public Res<TopoElem> topologyElement() {
        return Res.success(situationService.topologyElem());
    }

    /**
     * 网络效能态势
     * @return
     */
    @GetMapping("/overall")
    @ApiOperation("网络效能态势(未完善)")
    public Res<Float> overallSituation() {
        return Res.success(situationService.overallSituation());
    }
}
