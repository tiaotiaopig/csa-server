package edu.scu.csaserver.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    String filePath = "graph";

    /**
     * 上传文件服务
     * @param file 被Spring包装的文件类
     * @return
     */
    boolean upload(MultipartFile file);

    /**
     * 获取已经上传的所有文件
     * @return 所有文件的名称列表
     */
    List<String> getUploaded();

    /**
     * 根据拓扑图文件名，解析对应的txt文件
     * @param filename 拓扑图文件名
     * @return 节点对列表
     */
    List<int[]> parseTxt(String filename);
}
