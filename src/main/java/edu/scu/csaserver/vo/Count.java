package edu.scu.csaserver.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 统一封装 pie 图的响应信息
 * 主要就是 名称 和 数量
 * @author lifeng
 * @date 2021/7/21 上午10:41
 */
@Data
public class Count {
    /**
     * 统计量的名称
     */
    @ApiModelProperty("统计量的名称")
    private String countName;
    /**
     * 统计量的数量
     */
    @ApiModelProperty("统计量的数量")
    private Integer countValue;
}
