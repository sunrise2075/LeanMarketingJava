package com.yipage.leanmarketing.utils;


import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件下载工具类
 */
public class FileDownloadUtil {


    public static void download(String address, String fileName, HttpServletResponse response) {

        try {
            Map<String, Object> resMap = new HashMap<>();
            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");

            //2.设置文件头：最后一个参数是设置下载文件名
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String(fileName.getBytes("gb2312"), StandardCharsets.ISO_8859_1)); //支持中文文件名

            URL url = new URL(address);

            //通过文件路径获得File对象
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            OutputStream out = new BufferedOutputStream(response.getOutputStream());

            int b = 0;
            byte[] buffer = new byte[1024];
            while ((b = in.read(buffer)) != -1) {
                out.write(buffer, 0, b); //4.写到输出流(out)中
            }
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
