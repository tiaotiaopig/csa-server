package edu.scu.csaserver.controller;

import edu.scu.csaserver.service.FileService;
import edu.scu.csaserver.vo.Res;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * 接收前端上传的文件
 * @author Lifeng
 * @date 2022/3/26 19:16
 */
@CrossOrigin
@RestController
@RequestMapping("/file")
@Api(tags = "文件管理")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    @ApiOperation(value = "上传文件", notes = "上传单个文件，文件内容为空会上传失败")
    public Res<String> upload(@RequestParam("file") MultipartFile file) {
        boolean success = fileService.upload(file);
        if (success) {
            return Res.success("上传成功");
        } else {
            return Res.fail(578, "上传失败");
        }
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "获取所有上传文件名称", notes = "为空表示没有")
    public Res<List<String>> getUploadedFiles() {
        return Res.success(fileService.getUploaded());
    }

    @GetMapping("/func/{choice}")
    @ApiOperation(value = "获取所有关键节点/链路预测方法名称", notes = "选项为node或link")
    public Res<List<String>> getALLFunction(@PathVariable("choice") String choice) {
        if ("node".equals(choice)) {
            return Res.success(Arrays.asList("D", "BC", "CC", "KNI_GCN"));
        } else {
            return Res.success(Arrays.asList("common_neighbor", "page_rank", "sim_rank", "attention_feature_fusion"));
        }
    }

    @GetMapping("/description")
    @ApiOperation(value = "获取关键节点/链路预测方法的描述信息", notes = "参数是方法名")
    public Res<String> getFuncDesc(@RequestParam("funcName") String funcName) {
        String funcDes = fileService.readFuncDes(funcName);
        return funcDes == null ? Res.fail(400, "请求错误，请检查") : Res.success(funcDes);
    }
}
