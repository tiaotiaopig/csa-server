package edu.scu.csaserver.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 类别VO
 * 我们这里用的是子网划分类别
 * @author Lifeng
 * @date 2021/6/25 19:22
 */
@Data
public class Category implements Serializable {
    /**
     * 类别名称
     */
    private String name;
    /**
     * 类别图形表示
     */
    private String symbol;

    private static final long serialVersionUID = 1L;
}
