package edu.scu.csaserver.vo;

import edu.scu.csaserver.domain.Node;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 节点详情分页结果
 * {
 *   "status":0,
 *     "hint":"",
 *     "total":1000,
 *     "rows": [
 *
 *     {
 *       "id": 1,
 *       "node_ip": "192.192.192.75",
 *       "node_mac": "00-01-6C-06-A6-29",
 *       "node_vlan": 4002,
 *       "node_name": "节点1",
 *       "physical_type": 2,
 *       "logical_type": 3,
 *       "spectrum_floor": 2568.0,
 *       "spectrum_top": 2754.0
 *
 *     }
 * }
 * @author Lifeng
 * @date 2021/7/8 14:52
 */

// TODO:将nodelist整合到page中
@Data
public class NodeList {

    /**
     * 响应状态码
     */
    @ApiModelProperty("响应状态码")
    private Integer code;
    /**
     * 响应信息
     */
    @ApiModelProperty("响应信息")
    private String msg;
    /**
     * 节点总数
     */
    @ApiModelProperty("节点总数")
    private Integer count;
    /**
     * 分页结果
     */
    @ApiModelProperty("响应数据")
    private List<NodeInfo> data;
}
