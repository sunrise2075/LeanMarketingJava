package com.yipage.leanmarketing.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
import com.yipage.leanmarketing.constant.LeanMarkentingConstant;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 微信工具类(需要微信提供的jar包支持)
 */
public class WeiXinUtil {

    /**
     * 小程序微信授权登录
     *
     * @param code
     * @return
     */
    public static JSONObject weixinAuthority(String code) {

        //通过code换取网页授权access_token
        String result = HttpUtil.doPostSSL(LeanMarkentingConstant.getOpenIdAndTokenUrl.replace("CODE", code));
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject;
    }

    public static String getAccessToken() {

        String result = HttpUtil.doPostSSL(LeanMarkentingConstant.getAccessTokenUrl);
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject.getString("access_token");
    }


    /**
     * 支付
     *
     * @param spbill_create_ip 访问的ip,可以用request.getRemoteAddr()获取
     * @param total_fee        支付的金额jsonObject.get("openid")
     * @param out_trade_no     商户系统内部订单号，要求32个字符内
     * @param body             商品简单描述(这个可以随便写,但是一定要有)
     * @param openid           用户的openid(trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识)。
     * @return
     */
    public static Map pay(String spbill_create_ip, BigDecimal total_fee, String out_trade_no, String body, String openid, String url, String trade_type) throws Exception {

        //封装请求参数
        String reqParmXml = packagingPayData(spbill_create_ip, total_fee, out_trade_no, body, openid, url, trade_type);
        //调用微信统一下单
        String resResult = HttpUtil.doPostSSL(LeanMarkentingConstant.wxPayUrl, reqParmXml);
        //将返回来的结果封装成map
        Map<String, String> mapResult = WXPayUtil.xmlToMap(resResult);
        //封装返回给前端的map
        Map<String, String> responseMap = packagingRespData(mapResult);
        return responseMap;
    }

    /**
     * 微信支付回调
     */
    public static Map notifyUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {

        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        String resultxml = new String(outSteam.toByteArray(), StandardCharsets.UTF_8);
        Map<String, String> respMap = WXPayUtil.xmlToMap(resultxml);
        return respMap;
    }


    /**
     * 封装返回给前端的map
     *
     * @param mapResult
     * @return
     */
    private static Map<String, String> packagingRespData(Map<String, String> mapResult) throws Exception {

        Map<String, String> responseMap = new HashMap<String, String>();
        responseMap.put("appId", mapResult.get("appid"));
        responseMap.put("nonceStr", mapResult.get("nonce_str"));
        responseMap.put("package", "prepay_id=" + mapResult.get("prepay_id"));
        responseMap.put("signType", "MD5");
        responseMap.put("timeStamp", new Date().getTime() / 1000 + "");
        String paySign = WXPayUtil.generateSignature(responseMap, LeanMarkentingConstant.wxKey);
        responseMap.put("partnerid", mapResult.get("mch_id"));
        responseMap.put("paySign", paySign);
        if ("NATIVE".equals(mapResult.get("trade_type"))) {
            responseMap.put("code_url", mapResult.get("code_url"));
        }
        return responseMap;

    }

    /**
     * 封装支付数据
     *
     * @param spbill_create_ip 访问的ip,可以用request.getRemoteAddr()获取
     * @param total_fee        支付的金额
     * @param out_trade_no     商户系统内部订单号，要求32个字符内
     * @param body             商品简单描述(这个可以随便写,但是一定要有)
     * @param openid           用户的openid(trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识)。
     * @return
     */
    public static String packagingPayData(String spbill_create_ip, BigDecimal total_fee, String out_trade_no, String body, String openid, String url, String trade_type) throws Exception {

        SortedMap<String, String> parm = new TreeMap<String, String>();
        parm.put("appid", LeanMarkentingConstant.wxAppId);//公众号
        parm.put("mch_id", LeanMarkentingConstant.wxMchId);
        parm.put("nonce_str", WXPayUtil.generateNonceStr());
        parm.put("body", body);
        parm.put("out_trade_no", out_trade_no);
        parm.put("total_fee", total_fee.multiply(new BigDecimal(100)).intValue() + "");
        parm.put("spbill_create_ip", spbill_create_ip);
        parm.put("trade_type", trade_type);
        if ("NATIVE".equals(trade_type)) {
            parm.put("product_id", out_trade_no);
        } else {
            parm.put("openid", openid);
        }
        parm.put("notify_url", url);
        String paramXml = WXPayUtil.generateSignedXml(parm, LeanMarkentingConstant.wxKey);
        return paramXml;
    }

    /**
     * 生成带参数的小程序二维码
     *
     * @param sceneStr
     * @param accessToken
     */

    public static void getminiqrQr(String sceneStr, String accessToken, String page) {
        try {
            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", sceneStr);
            paramJson.put("page", "pages/index/index");
            paramJson.put("width", 430);
            paramJson.put("auto_color", true);
            /**
             * line_color生效
             * paramJson.put("auto_color", false);
             * JSONObject lineColor = new JSONObject();
             * lineColor.put("r", 0);
             * lineColor.put("g", 0);
             * lineColor.put("b", 0);
             * paramJson.put("line_color", lineColor);
             * */

            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            OutputStream os = new FileOutputStream(new File("C:/Users/ghs/Desktop/1.png"));
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String doImgPost(HttpServletRequest request, String url, Map<String, Object> map) {

        //使用HTTPPost方法访问获取二维码链接url
        HttpPost httpPost = new HttpPost(url);
        //创建http连接客户端
        CloseableHttpClient client = HttpClients.createDefault();
        //设置头响应类型

        httpPost.addHeader("Content-Type", "application/json");
        String fileName = "";
        try {
            // 设置请求的参数
            JSONObject postData = new JSONObject();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                postData.put(entry.getKey(), entry.getValue());
            }
            httpPost.setEntity(new StringEntity(postData.toString(), "UTF-8"));

            //返回的post请求结果
            HttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();

            byte[] bytes = EntityUtils.toByteArray(entity);

            fileName = QiniuUtil.saveImg(request, bytes, "qrcode.png");

        } catch (Exception e) {

        } finally {
            httpPost.releaseConnection();
        }
        return LeanMarkentingConstant.uploadAddress + fileName;
    }

    /**
     * 微信退款
     */
    public static String doRefund(String outTradeNo, String totalFee, String refundFee) throws Exception {

        String reqParmXml = packagingRefundData(outTradeNo, totalFee, refundFee);

        String result = HttpUtil.doPostHaveResourceSSL(LeanMarkentingConstant.wxPayRefundUrl, reqParmXml, LeanMarkentingConstant.apiclient_certLocation, LeanMarkentingConstant.wxMchId);

        return result;
    }


    /**
     * 封装退款参数
     *
     * @return
     */
    private static String packagingRefundData(String outTradeNo, String totalFee, String refundFee) throws Exception {

        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", LeanMarkentingConstant.wxAppId);
        packageParams.put("mch_id", LeanMarkentingConstant.wxMchId);
        packageParams.put("nonce_str", System.currentTimeMillis() + "");
        packageParams.put("out_trade_no", outTradeNo);
        packageParams.put("out_refund_no", UUID.randomUUID().toString().substring(0, 32));
        packageParams.put("total_fee", totalFee);
        packageParams.put("refund_fee", refundFee);
        packageParams.put("op_user_id", LeanMarkentingConstant.wxMchId);

        String paramXml = WXPayUtil.generateSignedXml(packageParams, LeanMarkentingConstant.wxKey);

        return paramXml;
    }

    public static void main(String[] args) throws Exception {

        doRefund("20190514064757214783", "1", "1");
        doRefund("20190514064949213578", "1", "1");
    }


}
