package com.yipage.leanmarketing.utils;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.yipage.leanmarketing.constant.LeanMarkentingConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付宝支付工具类
 */
public class AliPayUtil {

    private static final Logger logger = LoggerFactory.getLogger(AliPayUtil.class);

    /**
     * 返回页面
     *
     * @param body
     * @param subject
     * @param outTradeNo
     * @param totalAmount
     * @param returnUrl
     * @param notifyUrl
     * @param httpResponse
     */
    public static void pay(String body, String subject, String outTradeNo, String totalAmount, String returnUrl, String notifyUrl,
                           HttpServletResponse httpResponse) {
        AlipayClient alipayClient = new DefaultAlipayClient(LeanMarkentingConstant.aliPayUrl, LeanMarkentingConstant.aliAppId,
                LeanMarkentingConstant.aliPrivateKey, LeanMarkentingConstant.format, LeanMarkentingConstant.charset,
                null, LeanMarkentingConstant.signType);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\","
                + "\"total_amount\":\"" + totalAmount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        try {
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            httpResponse.setContentType("text/html;charset=" + LeanMarkentingConstant.charset);
            httpResponse.getWriter().write(result);//直接将完整的表单html输出到页面
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static Map<String, String> notifyUrl(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {
        //获取支付宝POST过来反馈信息
        response.setContentType("text/html;charset=" + LeanMarkentingConstant.charset);
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        logger.info("支付宝返回的参数" + JSON.toJSONString(requestParams));
        //参数处理
        dealWithParms(requestParams, params);
        logger.info("封装的参数" + JSON.toJSONString(params));
        return params;
    }

    private static void dealWithParms(Map requestParams, Map<String, String> params) {
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
    }


}
