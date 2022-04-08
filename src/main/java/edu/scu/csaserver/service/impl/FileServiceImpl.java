package edu.scu.csaserver.service.impl;

import edu.scu.csaserver.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * txt文件上传的实现类，将上传的文件保存到服务器本地
 * @author Lifeng
 * @date 2022/3/26 18:41
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public boolean upload(MultipartFile file) {
        // 文件为空不给上传，未选择文件上传
        if (file == null || file.isEmpty()) return false;
        File savedDir = new File(filePath);
        if (!savedDir.exists()) savedDir.mkdir();
        String[] fileList = savedDir.list((curr, name) -> name.endsWith(".txt") || name.endsWith(".pcap"));
        // 文件已存在
        if (fileList != null && Arrays.stream(fileList).anyMatch(s -> s.equals(file.getOriginalFilename()))) return true;
        try {
            file.transferTo(new File(savedDir.getAbsolutePath(), file.getOriginalFilename()));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取已经上传的所有文件
     *
     * @return 所有文件的名称列表
     */
    @Override
    public List<String> getUploaded() {
        List<String> res = new ArrayList<>();
        File savedDir = new File(filePath);
        if (!savedDir.exists()) return res;
        // 满足命名规则保留，dir 一直是savedDir这个目录
        // 可真鸡肋
        String[] filenames = savedDir.list((dir, name) -> name.endsWith(".txt"));
        if (filenames != null) {
            Collections.addAll(res, filenames);
        }
        return res;
    }

    /**
     * 根据拓扑图文件名，解析对应的txt文件
     *
     * @param filename 拓扑图文件名
     * @return 节点对列表
     */
    @Override
    public List<int[]> parseTxt(String filename) {
        List<int[]> res = new ArrayList<>();
        HashSet<Integer> nodes = new HashSet<>();
        File file = new File(filePath, filename);
        // try with resource
        // 适配器模式 装饰器模式
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line;
            String[] pairStr;
            int[] pairInt;
            while ((line = br.readLine()) != null) {
                // 文本文件格式不统一,好烦呀
                // 删除左右空格,以空格+或者\t分隔
                pairStr = line.trim().split("[ +\\t]+");
                pairInt = new int[2];
                pairInt[0] = Integer.parseInt(pairStr[0]);
                pairInt[1] = Integer.parseInt(pairStr[1]);
                // 添加边
                res.add(pairInt);
                // 添加节点
                nodes.add(pairInt[0]);
                nodes.add(pairInt[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int index = 0;
        int[] nodeArray = new int[nodes.size()];
        for (Integer node : nodes) nodeArray[index++] = node;
        res.add(nodeArray);
        return res;
    }
}
