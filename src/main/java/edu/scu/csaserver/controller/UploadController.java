//package edu.scu.csaserver.controller;
//
//
//import edu.scu.csaserver.vo.Res;
//import edu.scu.csaserver.vo.ResCode;
//import org.springframework.util.ResourceUtils;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//public class UploadController {
//
//    @PostMapping("/upload")
//    public Object upload(@RequestParam("file") MultipartFile file) throws FileNotFoundException {
//        return Res.genRes(saveFile(file));
//    }
//    @PostMapping("/multiUpload")
//    public Object multiUpload(@RequestParam("file")MultipartFile[] files) throws FileNotFoundException {
////        System.out.println("文件的个数:"+files.length);
//        Map<String,String> map = new HashMap<>();
//        boolean allSuccess = true;
//        for (MultipartFile f : files){
//            ResCode code = saveFile(f);
//            if (code.equals(ResCode.SUCCESS)){
//                map.put(f.getOriginalFilename(),"上传成功");
//            }else{
//                allSuccess = false;
//                map.put(f.getOriginalFilename(),code.getMsg());
//            }
//        }
//        if (allSuccess)
//            return Res.genRes(ResCode.SUCCESS);
//        else
//            return Res.genResWithData(ResCode.UPLOAD_FAIL,map);
//    }
//
////    private ResCode saveFile(MultipartFile file) throws FileNotFoundException {
////        if (file.isEmpty()){
////            return ResCode.UPLOAD_FAIL;
////        }
////        String filename = file.getOriginalFilename(); //获取上传文件原来的名称
////        return getUploadDir(file, filename);
////    }
//}