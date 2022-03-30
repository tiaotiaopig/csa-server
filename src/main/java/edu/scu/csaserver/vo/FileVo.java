package edu.scu.csaserver.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileVo {
    private String filename;
    private long updateTime;
    private long size;
}
