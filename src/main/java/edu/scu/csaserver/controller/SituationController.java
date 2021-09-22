package edu.scu.csaserver.controller;

import edu.scu.csaserver.vo.Res;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    /**
     * 网络物理传输效能
     * @return
     */
    @GetMapping("/phyTrans")
    @ApiOperation("网络物理传输效能")
    public Res<Float> physicalTransmission() {
        return Res.success(35.6f);
    }

    /**
     * 网络逻辑传输效能
     * @return
     */
    @GetMapping("/logTrans")
    @ApiOperation("网络逻辑传输效能")
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
        return Res.success(78.5f);
    }

    /**
     * 拓扑结构效能
     * @return
     */
    @GetMapping("/topology")
    @ApiOperation("拓扑结构效能")
    public Res<Float> topologyStructure() {
        return Res.success(72.3f);
    }

    /**
     * 网络效能态势
     * @return
     */
    @GetMapping("/overall")
    @ApiOperation("网络效能态势")
    public Res<Float> overallSituation() {
        return Res.success(78.5f);
    }
}
