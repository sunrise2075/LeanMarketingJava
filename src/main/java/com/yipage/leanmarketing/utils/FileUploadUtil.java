package com.yipage.leanmarketing.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 文件上传
 */
public class FileUploadUtil {

    /**
     * @param path 文件上传目录
     * @param file 上传的文件
     */
    public static String uploadFile(String path, MultipartFile file) throws Exception {


        File dirFile = new File(path);
        //如果文件不存在就创建
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        //上传文件名
        String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String filePrefix = String.valueOf(System.currentTimeMillis());
        File uploadFileName = new File(path + filePrefix + fileSuffix);
        //文件上传
        file.transferTo(uploadFileName);
        return filePrefix + fileSuffix;
    }


}
