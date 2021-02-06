package com.yipage.leanmarketing.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.yipage.leanmarketing.constant.LeanMarkentingConstant;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Description: 保存图片工具类
 * Company: youjiang
 *
 * @author Kwum
 * @version 1.0
 * @date 2016年10月17日 下午5:09:34
 */

public class QiniuUtil {

    //密钥配置
    private static Auth auth;
    //第一种方式: 指定具体的要上传的zone
    private static Zone z;
    private static Configuration c;
    //创建上传对象
    private static UploadManager uploadManager;

    private static final StringMap policy = new StringMap();

    static {
        try {
            auth = Auth.create(LeanMarkentingConstant.ACCESS_KEY, LeanMarkentingConstant.SECRET_KEY);
            long epoch = System.currentTimeMillis() / 1000 + 3600;
            //3. 构建上传管理对象
            policy.put("scope", LeanMarkentingConstant.bucketname);
            policy.put("deadline", epoch);
            z = Zone.zone2();
            c = new Configuration(z);
            uploadManager = new UploadManager(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public static String getUpToken() {
        long expireSeconds = 36000;
        return auth.uploadToken(LeanMarkentingConstant.bucketname, null, expireSeconds, policy);
    }

    public static String upload(MultipartFile file, String filePath) throws IOException {
        try {
            //上传文件名
            String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String filePrefix = String.valueOf(System.currentTimeMillis());
            Response res = uploadManager.put(filePath, filePrefix + fileSuffix, getUpToken());
            //打印返回的信息
            JSONObject jsonObject = JSON.parseObject(res.bodyString());
            return jsonObject.getString("key");
        } catch (QiniuException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String upload2(File file) throws IOException {
        try {
            Response response = uploadManager.put(file, file.getName(), getUpToken());

            JSONObject jsonObject = JSON.parseObject(response.bodyString());
            return jsonObject.getString("key");
        } catch (QiniuException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String saveImg(HttpServletRequest request,
                                 byte[] file, String fileName) throws IOException {

        try {
            long name = System.nanoTime();//获取系统当前时间（唯一值）
            String ext = fileName.substring(fileName.lastIndexOf("."));
            //上传到七牛后保存的文件名
            String key = name + ext;
            //调用put方法上传
            Response res = uploadManager.put(file, key, getUpToken());
            //打印返回的信息
            JSONObject json = JSON.parseObject(res.bodyString());
            return json.getString("key");

        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            //响应的文本信息
        }
        return "";
    }


}

