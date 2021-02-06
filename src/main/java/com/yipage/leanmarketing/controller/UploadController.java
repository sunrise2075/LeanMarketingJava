package com.yipage.leanmarketing.controller;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.yipage.leanmarketing.constant.LeanMarkentingConstant;
import com.yipage.leanmarketing.utils.FileUploadUtil;
import com.yipage.leanmarketing.utils.QiniuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/upload")
public class UploadController {

    private final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @RequestMapping(value = "/uploadFile", method = {RequestMethod.POST})
    @ResponseBody
    public Map uploadFile(MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        String fileName;
        String path = LeanMarkentingConstant.localTemporaryPath;
        try {
            fileName = FileUploadUtil.uploadFile(path, file);
            String fileName2 = QiniuUtil.upload(file, path + fileName);
            map.put("data", LeanMarkentingConstant.uploadAddress + fileName2);
            map.put("msg", "ok");
            map.put("fileSize", file.getSize());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "fail");
            map.put("fileSize", file.getSize());
        }
        return map;
    }

    /**
     * 富文本的文件上传
     */
    @ResponseBody
    @RequestMapping(value = "/ueditorUpload", method = {RequestMethod.POST})
    public Map editorUpload(HttpServletRequest requests) {

        Map<String, Object> map = new HashMap<>();
        try {
            //线上
            String path = LeanMarkentingConstant.localTemporaryPath;
            String fileName;
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) requests;
            Map<String, MultipartFile> files = multipartRequest.getFileMap();
            String[] fileNames = new String[files.size()];
            if (files.size() > 0) {
                for (MultipartFile multipartFile : files.values()) {
                    fileName = FileUploadUtil.uploadFile(path, multipartFile);
                    String filename = QiniuUtil.upload(multipartFile, path + fileName);
                    fileNames[0] = LeanMarkentingConstant.uploadAddress + filename;
                }
            }
            map.put("errno", 0);
            map.put("data", fileNames);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @GetMapping(value = "/getToken")
    @ResponseBody
    public Map getToken() {
        Map<String, Object> map = new HashMap<>();
        String accessKey = LeanMarkentingConstant.ACCESS_KEY;
        String secretKey = LeanMarkentingConstant.SECRET_KEY;
        String bucket = LeanMarkentingConstant.bucketname;
        long expireSeconds = 600;   //过期时间
        StringMap putPolicy = new StringMap();
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        map.put("key", UUID.randomUUID().toString().replaceAll("\\-", ""));
        map.put("token", upToken);
        return map;
    }

}
